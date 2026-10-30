package net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.util.StrUtils;

public class ShaderOptionVariableConst extends ShaderOptionVariable {
   private String type = null;
   private static final Pattern l = Pattern.compile("^\\s*const\\s*(float|int)\\s*([A-Za-z0-9_]+)\\s*=\\s*(-?[0-9.]+f?F?)\\s*;\\s*(//.*)?$");

   public ShaderOptionVariableConst(String string1, String string2, String string3, String string4, String[] arr, String string6) {
      super(string1, string3, string4, arr, string6);
      this.type = string2;
   }

   @Override
   public String getSourceLine() {
      String var10000 = this.type;
      String var3;
      String var4 = var3 = this.getValue();
      String var5 = this.getName();
      String var6 = var10000;
      return "const " + var6 + " " + var5 + " = " + var4 + "; // Shader option " + var3;
   }

   @Override
   public boolean matchesLine(String string) {
      Matcher var5 = l.matcher(string);
      return !var5.matches() ? false : var5.group(2).matches(this.getName());
   }

   public static ShaderOption parseOption(String string1, String string2) {
      Matcher var5 = l.matcher(string1);
      if (!var5.matches()) {
         return null;
      }

      String var6 = var5.group(1);
      String var7 = var5.group(2);
      String var8 = var5.group(3);
      String var9 = var5.group(4);
      String var10 = StrUtils.getSegment(var9, "[", "]");
      if (var10 != null && var10.length() > 0) {
         var9 = var9.replace(var10, "").trim();
      }

      String[] var11 = parseValues(var8, var10);
      if (var7 != null && var7.length() > 0) {
         string2 = StrUtils.removePrefix(string2, "/shaders/");
         return new ShaderOptionVariableConst(var7, var6, var9, var8, var11, string2);
      } else {
         return null;
      }
   }
}
