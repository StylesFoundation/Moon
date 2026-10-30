package com.moon.render.attributes;

import java.util.Arrays;

public enum AttributeTokenType {
   PARENTHESES('('),
   CLOSING_PARENTHESES(')'),
   QUOTATION('"'),
   BRACKET('['),
   CLOSING_BRACKET(']'),
   BRACES('{'),
   CLOSING_BRACES('}'),
   EQUALS('='),
   COMMA(',');

   private final char symbol;

   public static AttributeTokenType fromChar(char character) {
      return Arrays.stream(values()).filter(tokenType -> tokenType.getSymbol() == character).findFirst().orElse(null);
   }

   public char getSymbol() {
      return this.symbol;
   }

   AttributeTokenType(char symbol) {
      this.symbol = symbol;
   }

   private static AttributeTokenType[] createValues() {
      return new AttributeTokenType[]{PARENTHESES, CLOSING_PARENTHESES, QUOTATION, BRACKET, CLOSING_BRACKET, BRACES, CLOSING_BRACES, EQUALS, COMMA};
   }
}
