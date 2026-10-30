package com.moon.render.attributes.writer.impl;

import com.moon.render.attributes.model.AttributeList;
import com.moon.render.attributes.model.AttributeNode;
import com.moon.render.attributes.parser.AttributeParser;
import com.moon.render.attributes.parser.impl.PrimitiveArrayConverter;
import com.moon.render.attributes.writer.AttributeWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

public class ListAttributeWriter extends AttributeWriter {
   @Override
   public <T> void write(
      T object,
      AttributeNode node,
      Field field,
      Class<?> fieldType,
      Class<?> valueType,
      Map<Class<? extends AttributeNode>, AttributeWriter> writers,
      Map<Class<?>, Class<? extends AttributeParser<?>>> parsers
   ) throws Exception {
      AttributeList attributeList = (AttributeList)node;
      ArrayList elements = new ArrayList();

      for (AttributeNode elementNode : attributeList.flow()) {
         Class elementClass = elementNode.getClass();
         AttributeWriter writer = (AttributeWriter)writers.get(elementClass);
         if (writer != null) {
            writer.writeElement(object, elementNode, field, fieldType, valueType, writers, parsers, elements);
         }
      }

      if (valueType.isPrimitive()) {
         switch (valueType.getTypeName()) {
            case "int":
               field.set(object, PrimitiveArrayConverter.toIntArray(elements));
               break;
            case "float":
               field.set(object, PrimitiveArrayConverter.toFloatArray(elements));
               break;
            case "double":
               field.set(object, PrimitiveArrayConverter.toDoubleArray(elements));
               break;
            case "byte":
               field.set(object, PrimitiveArrayConverter.toByteArray(elements));
               break;
            case "short":
               field.set(object, PrimitiveArrayConverter.toShortArray(elements));
               break;
            case "long":
               field.set(object, PrimitiveArrayConverter.toLongArray(elements));
         }
      } else {
         field.set(object, fieldType.isArray() ? elements.toArray((T[])((Object[])Array.newInstance(fieldType.getComponentType(), elements.size()))) : elements);
      }
   }

   @Override
   public <T> void writeElement(
      T object,
      AttributeNode node,
      Field field,
      Class<?> fieldType,
      Class<?> valueType,
      Map<Class<? extends AttributeNode>, AttributeWriter> writers,
      Map<Class<?>, Class<? extends AttributeParser<?>>> parsers,
      ArrayList elements
   ) {
   }
}
