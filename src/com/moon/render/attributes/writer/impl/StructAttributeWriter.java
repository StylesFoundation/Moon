package com.moon.render.attributes.writer.impl;

import com.moon.render.attributes.model.AttributeField;
import com.moon.render.attributes.model.AttributeNode;
import com.moon.render.attributes.model.AttributeStruct;
import com.moon.render.attributes.parser.AttributeParser;
import com.moon.render.attributes.parser.impl.EnumAttributeParser;
import com.moon.render.attributes.writer.AttributeWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import moom._d;

public class StructAttributeWriter extends AttributeWriter {
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
      AttributeStruct attributeStruct = (AttributeStruct)node;
      Object instance = valueType.getDeclaredConstructor().newInstance();

      try {
         String nameFieldName = _d.c(valueType, "_name");
         String defaultFieldName = "_name";
         Field nameField = valueType.getDeclaredField(nameFieldName);
         if (!nameField.canAccess(instance)) {
            nameField.setAccessible(true);
         }
         nameField.set(instance, attributeStruct.name());
      } catch (Exception e) {
         e.printStackTrace();
      }

      for (AttributeField attributeField : attributeStruct.flow()) {
         String attrName = attributeField.name();
         Field structField = valueType.getDeclaredField(_d.c(valueType, attrName));
         Class<?> fieldClass = structField.getType();
         if (!structField.canAccess(instance)) {
            structField.setAccessible(true);
         }

         if (fieldClass.isEnum()) {
            structField.set(instance, new EnumAttributeParser((Class<? extends Enum<?>>)fieldClass).parse(attributeField.value()));
         } else {
            Class<? extends AttributeParser<?>> parserClass = parsers.get(fieldClass);
            if (parserClass != null) {
               AttributeParser<?> parser = parserClass.getDeclaredConstructor().newInstance();
               structField.set(instance, parser.parse(attributeField.value()));
            }
         }
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
   ) throws Exception {
      AttributeStruct attributeStruct = (AttributeStruct)node;
      Object instance = valueType.getDeclaredConstructor().newInstance();

      try {
         Field nameField = valueType.getDeclaredField("_name");
         if (!nameField.canAccess(instance)) {
            nameField.setAccessible(true);
         }

         nameField.set(instance, attributeStruct.name());
      } catch (Exception e) {
         e.printStackTrace();
      }

      for (AttributeField attributeField : attributeStruct.flow()) {
         String attrName = attributeField.name();
         Field structField = valueType.getDeclaredField(attrName);
         Class<?> fieldClass = structField.getType();
         if (!structField.canAccess(instance)) {
            structField.setAccessible(true);
         }

         if (fieldClass.isEnum()) {
            structField.set(instance, new EnumAttributeParser((Class<? extends Enum<?>>)fieldClass).parse(attributeField.value()));
         } else {
            Class<? extends AttributeParser<?>> parserClass = parsers.get(fieldClass);
            if (parserClass != null) {
               AttributeParser<?> parser = parserClass.getDeclaredConstructor().newInstance();
               structField.set(instance, parser.parse(attributeField.value()));
            }
         }
      }

      elements.add(instance);
   }
}
