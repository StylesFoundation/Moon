package net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.util.StrUtils;

public class ShaderOptionSwitchConst extends ShaderOptionSwitch {
   private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*bool\\s*([A-Za-z0-9_]+)\\s*=\\s*(true|false)\\s*;\\s*(//.*)?$");

   public ShaderOptionSwitchConst(String string1, String string2, String string3, String string4) {
      super(string1, string2, string3, string4);
   }

   @Override
   public String getSourceLine() {
      String var3;
      String var4 = var3 = this.getValue();
      String var5 = this.getName();
      return "const bool " + var5 + " = " + var4 + "; // Shader option " + var3;
   }

   public static ShaderOption parseOption(String string1, String string2) {
      Matcher var5 = PATTERN_CONST.matcher(string1);
      if (!var5.matches()) {
         return null;
      } else {
         String var6 = var5.group(1);
         String var7 = var5.group(2);
         String var8 = var5.group(3);
         if (var6 != null && var6.length() > 0) {
            string2 = StrUtils.removePrefix(string2, "/shaders/");
            ShaderOptionSwitchConst var9 = new ShaderOptionSwitchConst(var6, var8, var7, string2);
            var9.setVisible(false);
            return var9;
         } else {
            return null;
         }
      }
   }

   @Override
   public boolean matchesLine(String string) {
      Matcher var5 = PATTERN_CONST.matcher(string);
      return !var5.matches() ? false : var5.group(1).matches(this.getName());
   }

   @Override
   public boolean checkUsed() {
      return false;
   }
}
