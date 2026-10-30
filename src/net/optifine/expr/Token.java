package net.optifine.expr;

public class Token {
   private final TokenType type;
   private final String text;

   public Token(TokenType tokenType, String string) {
      this.type = tokenType;
      this.text = string;
   }

   public TokenType getType() {
      return this.type;
   }

   public String getText() {
      return this.text;
   }

   @Override
   public String toString() {
      return this.text;
   }
}
