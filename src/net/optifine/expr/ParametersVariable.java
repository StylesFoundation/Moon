package net.optifine.expr;

import java.util.ArrayList;
import java.util.Arrays;

public class ParametersVariable implements IParameters {
   private final ExpressionType[] first;
   private final ExpressionType[] repeat;
   private final ExpressionType[] last;
   private int maxCount = Integer.MAX_VALUE;
   private static final ExpressionType[] EMPTY = new ExpressionType[0];

   public ParametersVariable() {
      this(null, null, null);
   }

   public ParametersVariable(ExpressionType[] arr1, ExpressionType[] arr2, ExpressionType[] arr3) {
      this(arr1, arr2, arr3, Integer.MAX_VALUE);
   }

   public ParametersVariable(ExpressionType[] arr1, ExpressionType[] arr2, ExpressionType[] arr3, int integer) {
      this.first = normalize(arr1);
      this.repeat = normalize(arr2);
      this.last = normalize(arr3);
      this.maxCount = integer;
   }

   private static ExpressionType[] normalize(ExpressionType[] arr) {
      return arr == null ? EMPTY : arr;
   }

   public ExpressionType[] getFirst() {
      return this.first;
   }

   public ExpressionType[] getRepeat() {
      return this.repeat;
   }

   public ExpressionType[] getLast() {
      return this.last;
   }

   public int getCountRepeat() {
      return this.first == null ? 0 : this.first.length;
   }

   @Override
   public ExpressionType[] getParameterTypes(IExpression[] arr) {
      int var5 = this.first.length + this.last.length;
      int var6 = arr.length - var5;
      int var7 = 0;

      for (int var8 = 0; var8 + this.repeat.length <= var6 && var5 + var8 + this.repeat.length <= this.maxCount; var8 += this.repeat.length) {
         var7++;
      }

      ArrayList<ExpressionType> var10 = new ArrayList<>(Arrays.asList(this.first));

      for (int var9 = 0; var9 < var7; var9++) {
         var10.addAll(Arrays.asList(this.repeat));
      }

      var10.addAll(Arrays.asList(this.last));
      return var10.toArray(new ExpressionType[0]);
   }

   public ParametersVariable first(ExpressionType... arr) {
      return new ParametersVariable(arr, this.repeat, this.last);
   }

   public ParametersVariable repeat(ExpressionType... arr) {
      return new ParametersVariable(this.first, arr, this.last);
   }

   public ParametersVariable last(ExpressionType... arr) {
      return new ParametersVariable(this.first, this.repeat, arr);
   }

   public ParametersVariable maxCount(int integer) {
      return new ParametersVariable(this.first, this.repeat, this.last, integer);
   }
}
