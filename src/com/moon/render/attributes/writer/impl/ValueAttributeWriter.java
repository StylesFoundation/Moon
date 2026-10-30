package com.moon.render.attributes.writer.impl;

import com.moon.render.attributes.model.AttributeNode;
import com.moon.render.attributes.model.AttributeValue;
import com.moon.render.attributes.parser.AttributeParser;
import com.moon.render.attributes.parser.impl.EnumAttributeParser;
import com.moon.render.attributes.writer.AttributeWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

public class ValueAttributeWriter extends AttributeWriter {
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
      AttributeValue attributeValue = (AttributeValue)node;
      AttributeParser parser = this.getParser(fieldType, parsers);
      if (parser != null) {
         field.set(object, parser.parse(attributeValue.value()));
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
      AttributeValue attributeValue = (AttributeValue)node;
      AttributeParser parser = this.getParser(valueType, parsers);
      if (parser != null) {
         elements.add(parser.parse(attributeValue.value()));
      }
   }

   private AttributeParser<?> getParser(Class<?> type, Map<Class<?>, Class<? extends AttributeParser<?>>> parsers) throws Exception {
      if (type.isEnum()) {
         return new EnumAttributeParser((Class<? extends Enum<?>>)type);
      }
      Class parserClass = (Class)parsers.get(type);
      return parserClass == null ? null : (AttributeParser)parserClass.getDeclaredConstructor().newInstance();
   }
}
