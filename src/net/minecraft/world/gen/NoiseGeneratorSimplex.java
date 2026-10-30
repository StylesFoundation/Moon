package net.minecraft.world.gen;

import java.util.Random;

public class NoiseGeneratorSimplex {
   private static final int[][] field_151611_e = new int[][]{
      {1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}, {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1}, {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}
   };
   public static final double field_151614_a = Math.sqrt(3.0);
   private final int[] field_151608_f = new int[512];
   public double field_151612_b;
   public double field_151613_c;
   public double field_151610_d;
   private static final double field_151609_g = 0.5 * (field_151614_a - 1.0);
   private static final double field_151615_h = (3.0 - field_151614_a) / 6.0;

   public NoiseGeneratorSimplex() {
      this(new Random());
   }

   public NoiseGeneratorSimplex(Random random) {
      this.field_151612_b = random.nextDouble() * 256.0;
      this.field_151613_c = random.nextDouble() * 256.0;
      this.field_151610_d = random.nextDouble() * 256.0;
      int var4 = 0;

      while (var4 < 256) {
         this.field_151608_f[var4] = var4++;
      }

      for (int var7 = 0; var7 < 256; var7++) {
         int var5 = random.nextInt(256 - var7) + var7;
         int var6 = this.field_151608_f[var7];
         this.field_151608_f[var7] = this.field_151608_f[var5];
         this.field_151608_f[var5] = var6;
         this.field_151608_f[var7 + 256] = this.field_151608_f[var7];
      }
   }

   private static int func_151607_a(double double1) {
      return double1 > 0.0 ? (int)double1 : (int)double1 - 1;
   }

   private static double func_151604_a(int[] arr, double double2, double double3) {
      return arr[0] * double2 + arr[1] * double3;
   }

   public double func_151605_a(double double1, double double2) {
      double var8 = 0.5 * (field_151614_a - 1.0);
      double var10 = (double1 + double2) * var8;
      int var12 = func_151607_a(double1 + var10);
      int var13 = func_151607_a(double2 + var10);
      double var14 = (3.0 - field_151614_a) / 6.0;
      double var16 = (var12 + var13) * var14;
      double var18 = var12 - var16;
      double var20 = var13 - var16;
      double var22 = double1 - var18;
      double var24 = double2 - var20;
      byte var26;
      byte var27;
      if (var22 > var24) {
         var26 = 1;
         var27 = 0;
      } else {
         var26 = 0;
         var27 = 1;
      }

      double var28 = var22 - var26 + var14;
      double var30 = var24 - var27 + var14;
      double var32 = var22 - 1.0 + 2.0 * var14;
      double var34 = var24 - 1.0 + 2.0 * var14;
      int var36 = var12 & 0xFF;
      int var37 = var13 & 0xFF;
      int var38 = this.field_151608_f[var36 + this.field_151608_f[var37]] % 12;
      int var39 = this.field_151608_f[var36 + var26 + this.field_151608_f[var37 + var27]] % 12;
      int var40 = this.field_151608_f[var36 + 1 + this.field_151608_f[var37 + 1]] % 12;
      double var41 = 0.5 - var22 * var22 - var24 * var24;
      double var43;
      if (var41 < 0.0) {
         var43 = 0.0;
      } else {
         var41 *= var41;
         var43 = var41 * var41 * func_151604_a(field_151611_e[var38], var22, var24);
      }

      double var45 = 0.5 - var28 * var28 - var30 * var30;
      double var47;
      if (var45 < 0.0) {
         var47 = 0.0;
      } else {
         var45 *= var45;
         var47 = var45 * var45 * func_151604_a(field_151611_e[var39], var28, var30);
      }

      double var49 = 0.5 - var32 * var32 - var34 * var34;
      double var51;
      if (var49 < 0.0) {
         var51 = 0.0;
      } else {
         var49 *= var49;
         var51 = var49 * var49 * func_151604_a(field_151611_e[var40], var32, var34);
      }

      return 70.0 * (var43 + var47 + var51);
   }

   public void func_151606_a(double[] arr, double double2, double double3, int integer4, int integer5, double double6, double double7, double double8) {
      int var17 = 0;

      for (int var18 = 0; var18 < integer5; var18++) {
         double var19 = (double3 + var18) * double7 + this.field_151613_c;

         for (int var21 = 0; var21 < integer4; var21++) {
            double var22 = (double2 + var21) * double6 + this.field_151612_b;
            double var24 = (var22 + var19) * field_151609_g;
            int var26 = func_151607_a(var22 + var24);
            int var27 = func_151607_a(var19 + var24);
            double var28 = (var26 + var27) * field_151615_h;
            double var30 = var26 - var28;
            double var32 = var27 - var28;
            double var34 = var22 - var30;
            double var36 = var19 - var32;
            byte var38;
            byte var39;
            if (var34 > var36) {
               var38 = 1;
               var39 = 0;
            } else {
               var38 = 0;
               var39 = 1;
            }

            double var40 = var34 - var38 + field_151615_h;
            double var42 = var36 - var39 + field_151615_h;
            double var44 = var34 - 1.0 + 2.0 * field_151615_h;
            double var46 = var36 - 1.0 + 2.0 * field_151615_h;
            int var48 = var26 & 0xFF;
            int var49 = var27 & 0xFF;
            int var50 = this.field_151608_f[var48 + this.field_151608_f[var49]] % 12;
            int var51 = this.field_151608_f[var48 + var38 + this.field_151608_f[var49 + var39]] % 12;
            int var52 = this.field_151608_f[var48 + 1 + this.field_151608_f[var49 + 1]] % 12;
            double var53 = 0.5 - var34 * var34 - var36 * var36;
            double var55;
            if (var53 < 0.0) {
               var55 = 0.0;
            } else {
               var53 *= var53;
               var55 = var53 * var53 * func_151604_a(field_151611_e[var50], var34, var36);
            }

            double var57 = 0.5 - var40 * var40 - var42 * var42;
            double var59;
            if (var57 < 0.0) {
               var59 = 0.0;
            } else {
               var57 *= var57;
               var59 = var57 * var57 * func_151604_a(field_151611_e[var51], var40, var42);
            }

            double var61 = 0.5 - var44 * var44 - var46 * var46;
            double var63;
            if (var61 < 0.0) {
               var63 = 0.0;
            } else {
               var61 *= var61;
               var63 = var61 * var61 * func_151604_a(field_151611_e[var52], var44, var46);
            }

            int var65 = var17++;
            arr[var65] += 70.0 * (var55 + var59 + var63) * double8;
         }
      }
   }
}
