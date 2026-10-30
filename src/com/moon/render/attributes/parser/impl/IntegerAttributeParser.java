package com.moon.render.attributes.parser.impl;

import com.moon.render.attributes.parser.AttributeParser;

public class IntegerAttributeParser implements AttributeParser<Integer> {
   public Integer parse(String value) {
      return Integer.parseInt(value.trim().replace(" ", ""));
   }
}
