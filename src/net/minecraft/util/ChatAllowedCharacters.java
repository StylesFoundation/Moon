package net.minecraft.util;

public class ChatAllowedCharacters {
   public static final char[] allowedCharactersArray = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':'};

   public static boolean isAllowedCharacter(char character) {
      return character != 167 && character >= ' ' && character != 127;
   }

   public static String filterAllowedCharacters(String string) {
      StringBuilder var4 = new StringBuilder();

      for (char var8 : string.toCharArray()) {
         if (isAllowedCharacter(var8)) {
            var4.append(var8);
         }
      }

      return var4.toString();
   }
}
