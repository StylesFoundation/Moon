package net.minecraft.util;

import java.util.regex.Pattern;

public class StringUtils {
   private static final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

   public static String Method604(int integer) {
      int var4 = integer / 20;
      int var5 = var4 / 60;
      var4 %= 60;
      return var4 < 10 ? var5 + ":0" + var4 : var5 + ":" + var4;
   }

   public static String stripControlCodes(String string) {
      return patternControlCode.matcher(string).replaceAll("");
   }

   public static boolean isNullOrEmpty(String string) {
      return org.apache.commons.lang3.StringUtils.isEmpty(string);
   }
}
