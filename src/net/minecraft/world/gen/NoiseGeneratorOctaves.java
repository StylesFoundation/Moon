package net.minecraft.world.gen;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.util.MathHelper;

public class NoiseGeneratorOctaves extends NoiseGenerator {
   private final NoiseGeneratorImproved[] generatorCollection;
   private final int octaves;

   public NoiseGeneratorOctaves(Random random, int integer) {
      this.octaves = integer;
      this.generatorCollection = new NoiseGeneratorImproved[integer];

      for (int var6 = 0; var6 < integer; var6++) {
         this.generatorCollection[var6] = new NoiseGeneratorImproved(random);
      }
   }

   public double[] generateNoiseOctaves(
      double[] arr, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, double double8, double double9, double double10
   ) {
      if (arr == null) {
         arr = new double[integer5 * integer6 * integer7];
      } else {
         Arrays.fill(arr, 0.0);
      }

      double var17 = 1.0;

      for (int var19 = 0; var19 < this.octaves; var19++) {
         double var20 = integer2 * var17 * double8;
         double var22 = integer3 * var17 * double9;
         double var24 = integer4 * var17 * double10;
         long var26 = MathHelper.floor_double_long(var20);
         long var28 = MathHelper.floor_double_long(var24);
         var20 -= var26;
         var24 -= var28;
         var26 %= 16777216L;
         var28 %= 16777216L;
         var20 += var26;
         var24 += var28;
         this.generatorCollection[var19]
            .populateNoiseArray(arr, var20, var22, var24, integer5, integer6, integer7, double8 * var17, double9 * var17, double10 * var17, var17);
         var17 /= 2.0;
      }

      return arr;
   }

   public double[] generateNoiseOctaves(double[] arr, int integer2, int integer3, int integer4, int integer5, double double6, double double7, double double8) {
      return this.generateNoiseOctaves(arr, integer2, 10, integer3, integer4, 1, integer5, double6, 1.0, double7);
   }
}
