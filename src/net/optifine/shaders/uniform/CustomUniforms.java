package net.optifine.shaders.uniform;

import java.util.ArrayList;
import java.util.Map;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionCached;

public class CustomUniforms {
   private final CustomUniform[] uniforms;
   private final IExpressionCached[] expressionsCached;

   public CustomUniforms(CustomUniform[] arr, Map<String, IExpression> map) {
      this.uniforms = arr;
      ArrayList<IExpressionCached> var6 = new ArrayList<>();

      for (String var8 : map.keySet()) {
         IExpression var9 = map.get(var8);
         if (var9 instanceof IExpressionCached var10) {
            var6.add(var10);
         }
      }

      this.expressionsCached = var6.toArray(new IExpressionCached[0]);
   }

   public void setProgram(int integer) {
      CustomUniform[] var5 = this.uniforms;
      int var6 = var5.length;

      for (int var7 = 0; var7 < var6; var7++) {
         var5[var7].setProgram(integer);
      }
   }

   public void update() {
      this.resetCache();
      CustomUniform[] var4 = this.uniforms;
      int var5 = var4.length;

      for (int var6 = 0; var6 < var5; var6++) {
         var4[var6].update();
      }
   }

   private void resetCache() {
      IExpressionCached[] var4 = this.expressionsCached;
      int var5 = var4.length;

      for (int var6 = 0; var6 < var5; var6++) {
         var4[var6].reset();
      }
   }

   public void reset() {
      CustomUniform[] var4 = this.uniforms;
      int var5 = var4.length;

      for (int var6 = 0; var6 < var5; var6++) {
         var4[var6].reset();
      }
   }
}
