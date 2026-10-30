package com.moon.render.attributes.parser.impl;

import com.moon.render.attributes.parser.AttributeParser;

public class StringAttributeParser implements AttributeParser<String> {
   public String parse(String value) {
      return value;
   }

   public static boolean verify() {
      return true;
   }

   static {
      if (!verify()) {
      }
   }
}
