package net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.src.Config;
import net.optifine.Lang;
import net.optifine.util.StrUtils;

public class ShaderOptionSwitch extends ShaderOption {
   private static final Pattern PATTERN_DEFINE = Pattern.compile("^\\s*(//)?\\s*#define\\s+([A-Za-z0-9_]+)\\s*(//.*)?$");
   private static final Pattern PATTERN_IFDEF = Pattern.compile("^\\s*#if(n)?def\\s+([A-Za-z0-9_]+)(\\s*)?$");

   public ShaderOptionSwitch(String string1, String string2, String string3, String string4) {
      super(string1, string2, string3, new String[]{"false", "true"}, string3, string4);
   }

   @Override
   public String getSourceLine() {
      return isTrue(this.getValue()) ? "#define " + this.getName() + " // Shader option ON" : "//#define " + this.getName() + " // Shader option OFF";
   }

   @Override
   public String getValueText(String string) {
      String var5 = super.getValueText(string);
      return var5 != string ? var5 : (isTrue(string) ? Lang.getOn() : Lang.getOff());
   }

   @Override
   public String getValueColor(String string) {
      return isTrue(string) ? "§a" : "§c";
   }

   public static ShaderOption parseOption(String string1, String string2) {
      Matcher var5 = PATTERN_DEFINE.matcher(string1);
      if (!var5.matches()) {
         return null;
      } else {
         String var6 = var5.group(1);
         String var7 = var5.group(2);
         String var8 = var5.group(3);
         if (var7 != null && var7.length() > 0) {
            boolean var9 = Config.equals(var6, "//");
            boolean var10 = !var9;
            string2 = StrUtils.removePrefix(string2, "/shaders/");
            return new ShaderOptionSwitch(var7, var8, String.valueOf(var10), string2);
         } else {
            return null;
         }
      }
   }

   @Override
   public boolean matchesLine(String string) {
      Matcher var5 = PATTERN_DEFINE.matcher(string);
      return !var5.matches() ? false : var5.group(2).matches(this.getName());
   }

   @Override
   public boolean checkUsed() {
      return true;
   }

   @Override
   public boolean isUsedInLine(String string) {
      Matcher var5 = PATTERN_IFDEF.matcher(string);
      return var5.matches() ? var5.group(2).equals(this.getName()) : false;
   }

   public static boolean isTrue(String string) {
      return Boolean.parseBoolean(string);
   }
}
