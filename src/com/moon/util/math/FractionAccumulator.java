package com.moon.util.math;

public class FractionAccumulator {
   private double fraction;
   private double carry;

   public double consume(double amount) {
      amount = Math.min(this.carry, amount);
      double result = this.accumulate(amount, 0.0);
      this.carry -= amount;
      return result;
   }

   public double accumulate(double value, double weight) {
      double whole = Math.floor(value);
      double fractional = value - whole;
      this.carry += fractional * weight;
      fractional -= fractional * weight;
      this.fraction += fractional * (1.0 - weight);
      return whole + this.takeWhole();
   }

   public double takeWhole() {
      double whole = Math.floor(this.fraction);
      this.fraction -= whole;
      return whole;
   }

   public double getFraction() {
      return this.fraction;
   }

   public double getCarry() {
      return this.carry;
   }

   public static int zero() {
      return 0;
   }

   static {
      if (zero() != 0) {
      }
   }
}
