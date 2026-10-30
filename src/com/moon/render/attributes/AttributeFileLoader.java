package com.moon.render.attributes;

import com.moon.render.attributes.model.AttributeSource;
import com.moon.render.attributes.parser.AttributeConverter;
import java.io.IOException;
import java.io.InputStream;

public class AttributeFileLoader {
   public static <T> T loadFromStream(InputStream inputStream, Class<T> targetClass) {
      try {
         return loadFromString(new String(inputStream.readAllBytes()), targetClass);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public static <T> T loadFromString(String content, Class<T> targetClass) {
      return new AttributeConverter<>(new AttributeSource<>(content, targetClass)).convert();
   }
}
