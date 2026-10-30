package com.moon.render.attributes;

import com.moon.render.attributes.model.AttributeGroup;
import com.moon.render.attributes.model.AttributeNode;
import com.moon.render.attributes.parser.AttributeParser;
import com.moon.render.attributes.parser.AttributeParserRegistry;
import com.moon.render.attributes.writer.AttributeWriter;
import com.moon.render.attributes.writer.AttributeWriterRegistry;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import moom._d;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class AttributeCompiler<T> {
   private static final TaggedLogger logger = Logger.tag("attributes/compiler");
   private static final Map<Class<? extends AttributeNode>, AttributeWriter> WRITERS = new AttributeWriterRegistry();
   private final Map<Class<?>, Class<? extends AttributeParser<?>>> parsers = new AttributeParserRegistry(this);
   private final List<AttributeGroup> attributeGroups;
   private final Class<T> targetClass;

   public T compile() throws Exception {
      try {
         Object instance = this.targetClass.getDeclaredConstructor().newInstance();


         for (AttributeGroup group : this.attributeGroups) {
            Field field = this.targetClass.getDeclaredField(group.name());
            Class fieldType = field.getType();
            Class valueType = fieldType.isArray() ? fieldType.getComponentType() : fieldType;
            if (!field.canAccess(instance)) {
               field.setAccessible(true);
            }

            for (AttributeNode node : group.flow()) {
               Class nodeClass = node.getClass();
               AttributeWriter writer = WRITERS.get(nodeClass);
               if (writer != null) {
                  writer.write(instance, node, field, fieldType, valueType, WRITERS, this.parsers);
               }
            }
         }

         return (T)instance;
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
         logger.error("Failed to compile attributes file {}", new Object[]{e});
         e.printStackTrace();
         return null;
      }
   }

   public AttributeCompiler(List<AttributeGroup> attributeGroups, Class<T> targetClass) {
      this.attributeGroups = attributeGroups;
      this.targetClass = targetClass;
   }
}
