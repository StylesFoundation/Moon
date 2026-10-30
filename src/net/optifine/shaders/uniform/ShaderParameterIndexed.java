package net.optifine.shaders.uniform;

import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpressionFloat;

public class ShaderParameterIndexed implements IExpressionFloat {
   private final ShaderParameterFloat type;
   private final int index1;
   private final int index2;

   public ShaderParameterIndexed(ShaderParameterFloat shaderParameterFloat) {
      this(shaderParameterFloat, 0, 0);
   }

   public ShaderParameterIndexed(ShaderParameterFloat shaderParameterFloat, int integer) {
      this(shaderParameterFloat, integer, 0);
   }

   public ShaderParameterIndexed(ShaderParameterFloat shaderParameterFloat, int integer2, int integer3) {
      this.type = shaderParameterFloat;
      this.index1 = integer2;
      this.index2 = integer3;
   }

   @Override
   public float eval() {
      return this.type.eval(this.index1, this.index2);
   }

   @Override
   public ExpressionType getExpressionType() {
      return ExpressionType.FLOAT;
   }

   @Override
   public String toString() {
      return this.type.getIndexNames1() == null
         ? this.type + ""
         : (
            this.type.getIndexNames2() == null
               ? this.type + "." + this.type.getIndexNames1()[this.index1]
               : this.type + "." + this.type.getIndexNames1()[this.index1] + "." + this.type.getIndexNames2()[this.index2]
         );
   }
}
