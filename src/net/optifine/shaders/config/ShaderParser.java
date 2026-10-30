package net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShaderParser {
   public static Pattern PATTERN_UNIFORM = Pattern.compile("\\s*uniform\\s+\\w+\\s+(\\w+).*");
   public static Pattern PATTERN_ATTRIBUTE = Pattern.compile("\\s*attribute\\s+\\w+\\s+(\\w+).*");
   public static Pattern PATTERN_CONST_INT = Pattern.compile("\\s*const\\s+int\\s+(\\w+)\\s*=\\s*([-+.\\w]+)\\s*;.*");
   public static Pattern PATTERN_CONST_FLOAT = Pattern.compile("\\s*const\\s+float\\s+(\\w+)\\s*=\\s*([-+.\\w]+)\\s*;.*");
   public static Pattern PATTERN_CONST_VEC4 = Pattern.compile("\\s*const\\s+vec4\\s+(\\w+)\\s*=\\s*(.+)\\s*;.*");
   public static Pattern PATTERN_CONST_BOOL = Pattern.compile("\\s*const\\s+bool\\s+(\\w+)\\s*=\\s*(\\w+)\\s*;.*");
   public static Pattern PATTERN_PROPERTY = Pattern.compile("\\s*(/\\*|//)?\\s*([A-Z]+):\\s*(\\w+)\\s*(\\*/.*|\\s*)");
   public static Pattern PATTERN_EXTENSION = Pattern.compile("\\s*#\\s*extension\\s+(\\w+)\\s*:\\s*(\\w+).*");
   public static Pattern PATTERN_DEFERRED_FSH = Pattern.compile(".*deferred[0-9]*\\.fsh");
   public static Pattern PATTERN_COMPOSITE_FSH = Pattern.compile(".*composite[0-9]*\\.fsh");
   public static Pattern PATTERN_FINAL_FSH = Pattern.compile(".*final\\.fsh");
   public static Pattern PATTERN_DRAW_BUFFERS = Pattern.compile("[0-7N]*");

   public static ShaderLine parseLine(String string) {
      Matcher var4 = PATTERN_UNIFORM.matcher(string);
      if (var4.matches()) {
         return new ShaderLine(1, var4.group(1), "", string);
      }

      Matcher var5 = PATTERN_ATTRIBUTE.matcher(string);
      if (var5.matches()) {
         return new ShaderLine(2, var5.group(1), "", string);
      }

      Matcher var6 = PATTERN_PROPERTY.matcher(string);
      if (var6.matches()) {
         return new ShaderLine(6, var6.group(2), var6.group(3), string);
      }

      Matcher var7 = PATTERN_CONST_INT.matcher(string);
      if (var7.matches()) {
         return new ShaderLine(3, var7.group(1), var7.group(2), string);
      }

      Matcher var8 = PATTERN_CONST_FLOAT.matcher(string);
      if (var8.matches()) {
         return new ShaderLine(4, var8.group(1), var8.group(2), string);
      }

      Matcher var9 = PATTERN_CONST_BOOL.matcher(string);
      if (var9.matches()) {
         return new ShaderLine(5, var9.group(1), var9.group(2), string);
      }

      Matcher var10 = PATTERN_EXTENSION.matcher(string);
      if (var10.matches()) {
         return new ShaderLine(7, var10.group(1), var10.group(2), string);
      }

      Matcher var11 = PATTERN_CONST_VEC4.matcher(string);
      return var11.matches() ? new ShaderLine(8, var11.group(1), var11.group(2), string) : null;
   }

   public static int getIndex(String string1, String string2, int integer3, int integer4) {
      if (string1.length() != string2.length() + 1) {
         return -1;
      }

      if (!string1.startsWith(string2)) {
         return -1;
      }

      int var7 = string1.charAt(string2.length()) - '0';
      return var7 >= integer3 && var7 <= integer4 ? var7 : -1;
   }

   public static int getShadowDepthIndex(String string) {
      return string.equals("shadow") ? 0 : (string.equals("watershadow") ? 1 : getIndex(string, "shadowtex", 0, 1));
   }

   public static int getShadowColorIndex(String string) {
      return string.equals("shadowcolor") ? 0 : getIndex(string, "shadowcolor", 0, 1);
   }

   public static int getDepthIndex(String string) {
      return getIndex(string, "depthtex", 0, 2);
   }

   public static int getColorIndex(String string) {
      int var4 = getIndex(string, "gaux", 1, 4);
      return var4 > 0 ? var4 + 3 : getIndex(string, "colortex", 4, 7);
   }

   public static boolean isDeferred(String string) {
      return PATTERN_DEFERRED_FSH.matcher(string).matches();
   }

   public static boolean isComposite(String string) {
      return PATTERN_COMPOSITE_FSH.matcher(string).matches();
   }

   public static boolean isFinal(String string) {
      return PATTERN_FINAL_FSH.matcher(string).matches();
   }

   public static boolean isValidDrawBuffers(String string) {
      return PATTERN_DRAW_BUFFERS.matcher(string).matches();
   }
}
