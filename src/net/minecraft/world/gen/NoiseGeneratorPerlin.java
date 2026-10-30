package net.minecraft.world.gen;

import java.util.Arrays;
import java.util.Random;

public class NoiseGeneratorPerlin extends NoiseGenerator {
   private final NoiseGeneratorSimplex[] field_151603_a;
   private final int field_151602_b;

   public NoiseGeneratorPerlin(Random random, int integer) {
      this.field_151602_b = integer;
      this.field_151603_a = new NoiseGeneratorSimplex[integer];

      for (int var5 = 0; var5 < integer; var5++) {
         this.field_151603_a[var5] = new NoiseGeneratorSimplex(random);
      }
   }

   public double func_151601_a(double double1, double double2) {
      double var8 = 0.0;
      double var10 = 1.0;

      for (int var12 = 0; var12 < this.field_151602_b; var12++) {
         var8 += this.field_151603_a[var12].func_151605_a(double1 * var10, double2 * var10) / var10;
         var10 /= 2.0;
      }

      return var8;
   }

   public double[] func_151599_a(double[] arr, double double2, double double3, int integer4, int integer5, double double6, double double7, double double8) {
      return this.func_151600_a(arr, double2, double3, integer4, integer5, double6, double7, double8, 0.5);
   }

   public double[] func_151600_a(
      double[] arr, double double2, double double3, int integer4, int integer5, double double6, double double7, double double8, double double9
   ) {
      if (arr != null && arr.length >= integer4 * integer5) {
         Arrays.fill(arr, 0.0);
      } else {
         arr = new double[integer4 * integer5];
      }

      double var19 = 1.0;
      double var21 = 1.0;

      for (int var23 = 0; var23 < this.field_151602_b; var23++) {
         this.field_151603_a[var23].func_151606_a(arr, double2, double3, integer4, integer5, double6 * var21 * var19, double7 * var21 * var19, 0.55 / var19);
         var21 *= double8;
         var19 *= double9;
      }

      return arr;
   }
}
