package net.optifine.shaders.uniform;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.expr.ConstantFloat;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionResolver;
import net.optifine.shaders.SMCLog;

public class ShaderExpressionResolver implements IExpressionResolver {
   private final Map<String, IExpression> mapExpressions = new HashMap<>();

   public ShaderExpressionResolver(Map<String, IExpression> map) {
      this.registerExpressions();

      for (String var6 : map.keySet()) {
         IExpression var7 = (IExpression)map.get(var6);
         this.registerExpression(var6, var7);
      }
   }

   private void registerExpressions() {
      for (ShaderParameterFloat var8 : ShaderParameterFloat.values()) {
         this.addParameterFloat(this.mapExpressions, var8);
      }

      for (ShaderParameterBool var9 : ShaderParameterBool.values()) {
         this.mapExpressions.put(var9.getName(), var9);
      }

      for (BiomeGenBase var14 : BiomeGenBase.BIOME_ID_MAP.values()) {
         String var16 = "BIOME_" + var14.biomeName.trim().toUpperCase().replace(' ', '_');
         int var17 = var14.biomeID;
         ConstantFloat var10 = new ConstantFloat(var17);
         this.registerExpression(var16, var10);
      }
   }

   private void addParameterFloat(Map<String, IExpression> map, ShaderParameterFloat shaderParameterFloat) {
      String[] var6 = shaderParameterFloat.getIndexNames1();
      if (var6 == null) {
         map.put(shaderParameterFloat.getName(), new ShaderParameterIndexed(shaderParameterFloat));
      } else {
         for (int var7 = 0; var7 < var6.length; var7++) {
            String var8 = var6[var7];
            String[] var9 = shaderParameterFloat.getIndexNames2();
            if (var9 == null) {
               map.put(shaderParameterFloat.getName() + "." + var8, new ShaderParameterIndexed(shaderParameterFloat, var7));
            } else {
               for (int var10 = 0; var10 < var9.length; var10++) {
                  String var11 = var9[var10];
                  map.put(shaderParameterFloat.getName() + "." + var8 + "." + var11, new ShaderParameterIndexed(shaderParameterFloat, var7, var10));
               }
            }
         }
      }
   }

   public boolean registerExpression(String string, IExpression iExpression) {
      if (this.mapExpressions.containsKey(string)) {
         SMCLog.warning("Expression already defined: " + string);
         return false;
      } else {
         this.mapExpressions.put(string, iExpression);
         return true;
      }
   }

   @Override
   public IExpression getExpression(String string) {
      return this.mapExpressions.get(string);
   }

   public boolean hasExpression(String string) {
      return this.mapExpressions.containsKey(string);
   }
}
