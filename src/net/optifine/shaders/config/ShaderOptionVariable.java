package net.optifine.shaders.config;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.src.Config;
import net.optifine.shaders.Shaders;
import net.optifine.util.StrUtils;

public class ShaderOptionVariable extends ShaderOption {
   private static final Pattern j = Pattern.compile("^\\s*#define\\s+(\\w+)\\s+(-?[0-9.Ff]+|\\w+)\\s*(//.*)?$");

   public ShaderOptionVariable(String string1, String string2, String string3, String[] arr, String string5) {
      super(string1, string2, string3, arr, string3, string5);
      this.setVisible(this.getValues().length > 1);
   }

   @Override
   public String getSourceLine() {
      String var3;
      String var4 = var3 = this.getValue();
      String var5 = this.getName();
      return "#define " + var5 + " " + var4 + " // Shader option " + var3;
   }

   @Override
   public String getValueText(String string) {
      String var4 = Shaders.translate("prefix." + this.getName(), "");
      String var5 = super.getValueText(string);
      String var6 = Shaders.translate("suffix." + this.getName(), "");
      return var4 + var5 + var6;
   }

   @Override
   public String getValueColor(String string) {
      String var5 = string.toLowerCase();
      return !var5.equals("false") && !var5.equals("off") ? "§a" : "§c";
   }

   @Override
   public boolean matchesLine(String string) {
      Matcher var5 = j.matcher(string);
      return !var5.matches() ? false : var5.group(1).matches(this.getName());
   }

   public static ShaderOption parseOption(String string1, String string2) {
      Matcher var5 = j.matcher(string1);
      if (!var5.matches()) {
         return null;
      }

      String var6 = var5.group(1);
      String var7 = var5.group(2);
      String var8 = var5.group(3);
      String var9 = StrUtils.getSegment(var8, "[", "]");
      if (var9 != null && var9.length() > 0) {
         var8 = var8.replace(var9, "").trim();
      }

      String[] var10 = parseValues(var7, var9);
      if (var6 != null && var6.length() > 0) {
         string2 = StrUtils.removePrefix(string2, "/shaders/");
         return new ShaderOptionVariable(var6, var8, var7, var10, string2);
      } else {
         return null;
      }
   }

   public static String[] parseValues(String string1, String string2) {
      String[] var5 = new String[]{string1};
      if (string2 == null) {
         return var5;
      }

      string2 = StrUtils.removeSuffix(StrUtils.removePrefix(string2.trim(), "["), "]").trim();
      if (string2.length() == 0) {
         return var5;
      }

      String[] var6 = Config.tokenize(string2, " ");
      if (var6.length == 0) {
         return var5;
      }

      if (!Arrays.asList(var6).contains(string1)) {
         var6 = (String[])Config.addObjectToArray(var6, string1, 0);
      }

      return var6;
   }
}
