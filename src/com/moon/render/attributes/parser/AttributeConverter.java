package com.moon.render.attributes.parser;

import com.moon.render.attributes.AttributeCompiler;
import com.moon.render.attributes.model.AttributeGroup;
import com.moon.render.attributes.model.AttributeSource;
import java.util.List;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class AttributeConverter<T> {
   private static final TaggedLogger logger = Logger.tag("attributes/converter");
   private final AttributeSource<T> source;

   public T convert() {
      try {
         List<AttributeGroup> groups = new AttributeTextParser<T>(this.source).parse();
         return new AttributeCompiler<T>(groups, this.source.resultType()).compile();
      } catch (Exception e) {
         logger.error("Failed to convert attributes file {}", new Object[]{e});
         e.printStackTrace();
         return null;
      }
   }

   public AttributeConverter(AttributeSource<T> source) {
      this.source = source;
   }
}
