package net.optifine.shaders.config;

import java.util.Map;
import net.minecraft.src.Config;
import net.optifine.expr.ConstantFloat;
import net.optifine.expr.FunctionBool;
import net.optifine.expr.FunctionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionResolver;

public class MacroExpressionResolver implements IExpressionResolver {
   private Map<String, String> mapMacroValues = null;

   public MacroExpressionResolver(Map<String, String> map) {
      this.mapMacroValues = map;
   }

   @Override
   public IExpression getExpression(String string) {
      String var5 = "defined_";
      if (string.startsWith(var5)) {
         String var8 = string.substring(8);
         return this.mapMacroValues.containsKey(var8) ? new FunctionBool(FunctionType.TRUE, null) : new FunctionBool(FunctionType.FALSE, null);
      }

      while (this.mapMacroValues.containsKey(string)) {
         String var6 = this.mapMacroValues.get(string);
         if (var6 == null || var6.equals(string)) {
            break;
         }

         string = var6;
      }

      int var7 = Config.parseInt(string, Integer.MIN_VALUE);
      if (var7 == Integer.MIN_VALUE) {
         Config.Method252("Unknown macro value: " + string);
         return new ConstantFloat(0.0F);
      } else {
         return new ConstantFloat(var7);
      }
   }
}
