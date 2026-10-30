package com.moon.render.attributes.parser;

public interface AttributeParser<T> {
   T parse(String value);
}
