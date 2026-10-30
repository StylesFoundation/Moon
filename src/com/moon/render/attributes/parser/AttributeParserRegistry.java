package com.moon.render.attributes.parser;

import com.moon.render.attributes.AttributeCompiler;
import com.moon.render.attributes.parser.impl.IntegerAttributeParser;
import com.moon.render.attributes.parser.impl.StringAttributeParser;
import java.util.HashMap;

public class AttributeParserRegistry extends HashMap<Class<?>, Class<? extends AttributeParser<?>>> {
   final AttributeCompiler compiler;

   public AttributeParserRegistry(AttributeCompiler compiler) {
      this.compiler = compiler;
      this.put(String.class, StringAttributeParser.class);
      this.put(Integer.class, IntegerAttributeParser.class);
      this.put(int.class, IntegerAttributeParser.class);
   }
}
