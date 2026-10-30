package com.moon.render.attributes.writer;

import com.moon.render.attributes.model.AttributeNode;
import com.moon.render.attributes.parser.AttributeParser;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

public abstract class AttributeWriter {
   public abstract <T> void write(
      T object,
      AttributeNode node,
      Field field,
      Class<?> fieldType,
      Class<?> valueType,
      Map<Class<? extends AttributeNode>, AttributeWriter> writers,
      Map<Class<?>, Class<? extends AttributeParser<?>>> parsers
   ) throws Exception;

   public abstract <T> void writeElement(
      T object,
      AttributeNode node,
      Field field,
      Class<?> fieldType,
      Class<?> valueType,
      Map<Class<? extends AttributeNode>, AttributeWriter> writers,
      Map<Class<?>, Class<? extends AttributeParser<?>>> parsers,
      ArrayList elements
   ) throws Exception;
}
