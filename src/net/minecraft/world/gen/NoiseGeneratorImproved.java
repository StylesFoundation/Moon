package net.minecraft.world.gen;

import java.util.Random;

public class NoiseGeneratorImproved extends NoiseGenerator {
   private final int[] permutations = new int[512];
   public double xCoord;
   public double yCoord;
   public double zCoord;
   private static final double[] field_152381_e = new double[]{1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0, 0.0};
   private static final double[] field_152382_f = new double[]{1.0, 1.0, -1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0};
   private static final double[] field_152383_g = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 0.0, 1.0, 0.0, -1.0};
   private static final double[] field_152384_h = new double[]{1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0, 0.0};
   private static final double[] field_152385_i = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 0.0, 1.0, 0.0, -1.0};

   public NoiseGeneratorImproved() {
      this(new Random());
   }

   public NoiseGeneratorImproved(Random random) {
      this.xCoord = random.nextDouble() * 256.0;
      this.yCoord = random.nextDouble() * 256.0;
      this.zCoord = random.nextDouble() * 256.0;
      int var5 = 0;

      while (var5 < 256) {
         this.permutations[var5] = var5++;
      }

      for (int var8 = 0; var8 < 256; var8++) {
         int var6 = random.nextInt(256 - var8) + var8;
         int var7 = this.permutations[var8];
         this.permutations[var8] = this.permutations[var6];
         this.permutations[var6] = var7;
         this.permutations[var8 + 256] = this.permutations[var8];
      }
   }

   public final double lerp(double double1, double double2, double double3) {
      return double2 + double1 * (double3 - double2);
   }

   public final double func_76309_a(int integer, double double2, double double3) {
      int var8 = integer & 15;
      return field_152384_h[var8] * double2 + field_152385_i[var8] * double3;
   }

   public final double grad(int integer, double double2, double double3, double double4) {
      int var10 = integer & 15;
      return field_152381_e[var10] * double2 + field_152382_f[var10] * double3 + field_152383_g[var10] * double4;
   }

   public void populateNoiseArray(
      double[] arr,
      double double2,
      double double3,
      double double4,
      int integer5,
      int integer6,
      int integer7,
      double double8,
      double double9,
      double double10,
      double double11
   ) {
      if (integer6 == 1) {
         int var30 = 0;
         double var31 = 1.0 / double11;

         for (int var33 = 0; var33 < integer5; var33++) {
            double var34 = double2 + var33 * double8 + this.xCoord;
            int var36 = (int)var34;
            if (var34 < var36) {
               var36--;
            }

            int var37 = var36 & 0xFF;
            var34 -= var36;
            double var38 = var34 * var34 * var34 * (var34 * (var34 * 6.0 - 15.0) + 10.0);

            for (int var40 = 0; var40 < integer7; var40++) {
               double var41 = double4 + var40 * double10 + this.zCoord;
               int var43 = (int)var41;
               if (var41 < var43) {
                  var43--;
               }

               int var44 = var43 & 0xFF;
               var41 -= var43;
               double var45 = var41 * var41 * var41 * (var41 * (var41 * 6.0 - 15.0) + 10.0);
               int var22 = this.permutations[var37];
               int var23 = this.permutations[var22] + var44;
               int var24 = this.permutations[var37 + 1];
               int var25 = this.permutations[var24] + var44;
               double var26 = this.lerp(
                  var38, this.func_76309_a(this.permutations[var23], var34, var41), this.grad(this.permutations[var25], var34 - 1.0, 0.0, var41)
               );
               double var28 = this.lerp(
                  var38,
                  this.grad(this.permutations[var23 + 1], var34, 0.0, var41 - 1.0),
                  this.grad(this.permutations[var25 + 1], var34 - 1.0, 0.0, var41 - 1.0)
               );
               double var47 = this.lerp(var45, var26, var28);
               int var49 = var30++;
               arr[var49] += var47 * var31;
            }
         }
      } else {
         int var68 = 0;
         double var69 = 1.0 / double11;
         int var70 = -1;
         double var32 = 0.0;
         double var76 = 0.0;
         double var77 = 0.0;
         double var78 = 0.0;

         for (int var79 = 0; var79 < integer5; var79++) {
            double var81 = double2 + var79 * double8 + this.xCoord;
            int var83 = (int)var81;
            if (var81 < var83) {
               var83--;
            }

            int var84 = var83 & 0xFF;
            var81 -= var83;
            double var85 = var81 * var81 * var81 * (var81 * (var81 * 6.0 - 15.0) + 10.0);

            for (int var86 = 0; var86 < integer7; var86++) {
               double var48 = double4 + var86 * double10 + this.zCoord;
               int var50 = (int)var48;
               if (var48 < var50) {
                  var50--;
               }

               int var51 = var50 & 0xFF;
               var48 -= var50;
               double var52 = var48 * var48 * var48 * (var48 * (var48 * 6.0 - 15.0) + 10.0);

               for (int var54 = 0; var54 < integer6; var54++) {
                  double var55 = double3 + var54 * double9 + this.yCoord;
                  int var57 = (int)var55;
                  if (var55 < var57) {
                     var57--;
                  }

                  int var58 = var57 & 0xFF;
                  var55 -= var57;
                  double var59 = var55 * var55 * var55 * (var55 * (var55 * 6.0 - 15.0) + 10.0);
                  if (var54 == 0 || var58 != var70) {
                     var70 = var58;
                     int var71 = this.permutations[var84] + var58;
                     int var27 = this.permutations[var71] + var51;
                     int var72 = this.permutations[var71 + 1] + var51;
                     int var29 = this.permutations[var84 + 1] + var58;
                     int var73 = this.permutations[var29] + var51;
                     int var74 = this.permutations[var29 + 1] + var51;
                     var32 = this.lerp(
                        var85, this.grad(this.permutations[var27], var81, var55, var48), this.grad(this.permutations[var73], var81 - 1.0, var55, var48)
                     );
                     var76 = this.lerp(
                        var85,
                        this.grad(this.permutations[var72], var81, var55 - 1.0, var48),
                        this.grad(this.permutations[var74], var81 - 1.0, var55 - 1.0, var48)
                     );
                     var77 = this.lerp(
                        var85,
                        this.grad(this.permutations[var27 + 1], var81, var55, var48 - 1.0),
                        this.grad(this.permutations[var73 + 1], var81 - 1.0, var55, var48 - 1.0)
                     );
                     var78 = this.lerp(
                        var85,
                        this.grad(this.permutations[var72 + 1], var81, var55 - 1.0, var48 - 1.0),
                        this.grad(this.permutations[var74 + 1], var81 - 1.0, var55 - 1.0, var48 - 1.0)
                     );
                  }

                  double var61 = this.lerp(var59, var32, var76);
                  double var63 = this.lerp(var59, var77, var78);
                  double var65 = this.lerp(var52, var61, var63);
                  int var67 = var68++;
                  arr[var67] += var65 * var69;
               }
            }
         }
      }
   }
}
