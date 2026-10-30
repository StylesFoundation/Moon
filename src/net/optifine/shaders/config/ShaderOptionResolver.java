package net.optifine.shaders.config;

import java.util.HashMap;
import java.util.Map;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionResolver;

public class ShaderOptionResolver implements IExpressionResolver {
   private final Map<String, ExpressionShaderOptionSwitch> mapOptions = new HashMap<>();

   public ShaderOptionResolver(ShaderOption[] arr) {
      for (ShaderOption var8 : arr) {
         if (var8 instanceof ShaderOptionSwitch var9) {
            ExpressionShaderOptionSwitch var10 = new ExpressionShaderOptionSwitch(var9);
            this.mapOptions.put(var8.getName(), var10);
         }
      }
   }

   @Override
   public IExpression getExpression(String string) {
      return this.mapOptions.get(string);
   }
}
