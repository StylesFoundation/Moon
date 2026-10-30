package net.optifine.expr;

public class Parameters implements IParameters {
   private final ExpressionType[] parameterTypes;

   public Parameters(ExpressionType[] arr) {
      this.parameterTypes = arr;
   }

   @Override
   public ExpressionType[] getParameterTypes(IExpression[] arr) {
      return this.parameterTypes;
   }
}
