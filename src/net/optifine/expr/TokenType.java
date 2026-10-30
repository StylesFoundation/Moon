package net.optifine.expr;

public enum TokenType {
   IDENTIFIER("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_:."),
   NUMBER("0123456789", "0123456789."),
   OPERATOR("+-*/%!&|<>=", "&|="),
   COMMA(","),
   BRACKET_OPEN("("),
   BRACKET_CLOSE(")");

   private final String charsFirst;
   private final String charsNext;
   public static final TokenType[] VALUES = values();

   TokenType(String string3) {
      this(string3, "");
   }

   TokenType(String string3, String string4) {
      this.charsFirst = string3;
      this.charsNext = string4;
   }

   public String getCharsFirst() {
      return this.charsFirst;
   }

   public String getCharsNext() {
      return this.charsNext;
   }

   public static TokenType getTypeByFirstChar(char character) {
      for (TokenType var7 : VALUES) {
         if (var7.getCharsFirst().indexOf(character) >= 0) {
            return var7;
         }
      }

      return null;
   }

   public boolean hasCharNext(char character) {
      return this.charsNext.indexOf(character) >= 0;
   }

   private static TokenType[] Method574() {
      return new TokenType[]{IDENTIFIER, NUMBER, OPERATOR, COMMA, BRACKET_OPEN, BRACKET_CLOSE};
   }

   static class Const {
      private Const() {
      }
   }
}
