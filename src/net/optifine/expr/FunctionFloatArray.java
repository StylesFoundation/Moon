package net.optifine.expr;

public class FunctionFloatArray implements IExpressionFloatArray {
   private final FunctionType type;
   private final IExpression[] arguments;

   public FunctionFloatArray(FunctionType functionType, IExpression[] arr) {
      this.type = functionType;
      this.arguments = arr;
   }

   @Override
   public float[] eval() {
      return this.type.evalFloatArray(this.arguments);
   }

   @Override
   public ExpressionType getExpressionType() {
      return ExpressionType.FLOAT_ARRAY;
   }

   @Override
   public String toString() {
      return this.type + "()";
   }
}
