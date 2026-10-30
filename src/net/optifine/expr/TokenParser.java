package net.optifine.expr;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;

public class TokenParser {
   public static Token[] parse(String string) throws IOException, ParseException {
      StringReader var4 = new StringReader(string);
      PushbackReader var5 = new PushbackReader(var4);
      ArrayList<Token> var6 = new ArrayList<>();

      while (true) {
         int var7 = var5.read();
         if (var7 < 0) {
            return var6.toArray(new Token[0]);
         }

         char var8 = (char)var7;
         if (!Character.isWhitespace(var8)) {
            TokenType var9 = TokenType.getTypeByFirstChar(var8);
            if (var9 == null) {
               throw new ParseException("Invalid character: '" + var8 + "', in: " + string);
            }

            Token var10 = readToken(var8, var9, var5);
            var6.add(var10);
         }
      }
   }

   private static Token readToken(char character, TokenType tokenType, PushbackReader pushbackReader) throws IOException {
      StringBuilder var6 = new StringBuilder();
      var6.append(character);

      while (true) {
         int var7 = pushbackReader.read();
         if (var7 < 0) {
            break;
         }

         char var8 = (char)var7;
         if (!tokenType.hasCharNext(var8)) {
            pushbackReader.unread(var8);
            break;
         }

         var6.append(var8);
      }

      return new Token(tokenType, var6.toString());
   }
}
