package com.moon.render.attributes.parser.impl;

import com.moon.render.attributes.parser.AttributeParser;

public class EnumAttributeParser implements AttributeParser<Enum<?>> {
   private final Class<? extends Enum<?>> enumType;

   public Enum<?> parse(String value) {
      String normalizedValue = value.trim().replace(" ", "");

      for (Enum enumConstant : this.enumType.getEnumConstants()) {
         if (enumConstant.name().equals(normalizedValue)) {
            return enumConstant;
         }
      }

      return null;
   }

   public EnumAttributeParser(Class<? extends Enum<?>> enumType) {
      this.enumType = enumType;
   }
}
