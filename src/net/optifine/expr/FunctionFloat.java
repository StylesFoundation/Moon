package net.optifine.expr;

import net.optifine.shaders.uniform.Smoother;

public class FunctionFloat implements IExpressionFloat {
   private final FunctionType type;
   private final IExpression[] arguments;
   private int smoothId = -1;

   public FunctionFloat(FunctionType functionType, IExpression[] arr) {
      this.type = functionType;
      this.arguments = arr;
   }

   @Override
   public float eval() {
      IExpression[] var4 = this.arguments;
      if (this.type == FunctionType.SMOOTH && !(var4[0] instanceof ConstantFloat)) {
         float var6 = evalFloat(var4, 0);
         float var7 = var4.length > 1 ? evalFloat(var4, 1) : 1.0F;
         float var8 = var4.length > 2 ? evalFloat(var4, 2) : var7;
         if (this.smoothId < 0) {
            this.smoothId = Smoother.getNextId();
         }

         return Smoother.getSmoothValue(this.smoothId, var6, var7, var8);
      } else {
         return this.type.evalFloat(this.arguments);
      }
   }

   private static float evalFloat(IExpression[] arr, int integer) {
      return ((IExpressionFloat)arr[integer]).eval();
   }

   @Override
   public ExpressionType getExpressionType() {
      return ExpressionType.FLOAT;
   }

   @Override
   public String toString() {
      return this.type + "()";
   }
}
