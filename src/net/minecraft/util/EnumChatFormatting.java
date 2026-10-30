package net.minecraft.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public enum EnumChatFormatting {
   BLACK("BLACK", '0', 0),
   DARK_BLUE("DARK_BLUE", '1', 1),
   DARK_GREEN("DARK_GREEN", '2', 2),
   DARK_AQUA("DARK_AQUA", '3', 3),
   DARK_RED("DARK_RED", '4', 4),
   DARK_PURPLE("DARK_PURPLE", '5', 5),
   GOLD("GOLD", '6', 6),
   GRAY("GRAY", '7', 7),
   DARK_GRAY("DARK_GRAY", '8', 8),
   BLUE("BLUE", '9', 9),
   GREEN("GREEN", 'a', 10),
   AQUA("AQUA", 'b', 11),
   RED("RED", 'c', 12),
   LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13),
   YELLOW("YELLOW", 'e', 14),
   WHITE("WHITE", 'f', 15),
   OBFUSCATED("OBFUSCATED", 'k', true),
   BOLD("BOLD", 'l', true),
   STRIKETHROUGH("STRIKETHROUGH", 'm', true),
   UNDERLINE("UNDERLINE", 'n', true),
   ITALIC("ITALIC", 'o', true),
   RESET("RESET", 'r', -1);

   private static final Map<String, EnumChatFormatting> nameMapping = Maps.newHashMap();
   private static final Pattern b = Pattern.compile("(?i)§[0-9A-FK-OR]");
   private final String name;
   private final char formattingCode;
   private final boolean fancyStyling;
   private final String f;
   private final int colorIndex;

   private static String func_175745_c(String string) {
      return string.toLowerCase().replaceAll("[^a-z]", "");
   }

   EnumChatFormatting(String string3, char character, int integer5) {
      this(string3, character, false, integer5);
   }

   EnumChatFormatting(String string3, char character, boolean boolean5) {
      this(string3, character, boolean5, -1);
   }

   EnumChatFormatting(String string3, char character, boolean boolean5, int integer6) {
      this.name = string3;
      this.formattingCode = character;
      this.fancyStyling = boolean5;
      this.colorIndex = integer6;
      this.f = "§" + character;
   }

   public int getColorIndex() {
      return this.colorIndex;
   }

   public boolean isFancyStyling() {
      return this.fancyStyling;
   }

   public boolean isColor() {
      return !this.fancyStyling && this != RESET;
   }

   public String getFriendlyName() {
      return this.name().toLowerCase();
   }

   @Override
   public String toString() {
      return this.f;
   }

   public static String getTextWithoutFormattingCodes(String string) {
      return string == null ? null : b.matcher(string).replaceAll("");
   }

   public static EnumChatFormatting getValueByName(String string) {
      return string == null ? null : nameMapping.get(func_175745_c(string));
   }

   public static EnumChatFormatting func_175744_a(int integer) {
      if (integer < 0) {
         return RESET;
      }

      for (EnumChatFormatting var7 : values()) {
         if (var7.getColorIndex() == integer) {
            return var7;
         }
      }

      return null;
   }

   public static Collection<String> getValidValues(boolean boolean1, boolean boolean2) {
      ArrayList var5 = Lists.newArrayList();

      for (EnumChatFormatting var9 : values()) {
         if ((!var9.isColor() || boolean1) && (!var9.isFancyStyling() || boolean2)) {
            var5.add(var9.getFriendlyName());
         }
      }

      return var5;
   }

   private static EnumChatFormatting[] Method319() {
      return new EnumChatFormatting[]{
         BLACK,
         DARK_BLUE,
         DARK_GREEN,
         DARK_AQUA,
         DARK_RED,
         DARK_PURPLE,
         GOLD,
         GRAY,
         DARK_GRAY,
         BLUE,
         GREEN,
         AQUA,
         RED,
         LIGHT_PURPLE,
         YELLOW,
         WHITE,
         OBFUSCATED,
         BOLD,
         STRIKETHROUGH,
         UNDERLINE,
         ITALIC,
         RESET
      };
   }

   static {
      for (EnumChatFormatting var16 : values()) {
         nameMapping.put(func_175745_c(var16.name), var16);
      }
   }
}
