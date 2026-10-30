package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MapGenCavesHell extends MapGenBase {
   protected void func_180705_a(long long1, int integer2, int integer3, ChunkPrimer chunkPrimer, double double5, double double6, double double7) {
      this.func_180704_a(long1, integer2, integer3, chunkPrimer, double5, double6, double7, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5);
   }

   protected void func_180704_a(
      long long1,
      int integer2,
      int integer3,
      ChunkPrimer chunkPrimer,
      double double5,
      double double6,
      double double7,
      float float8,
      float float9,
      float float10,
      int integer11,
      int integer12,
      double double13
   ) {
      double var22 = integer2 * 16 + 8;
      double var24 = integer3 * 16 + 8;
      float var26 = 0.0F;
      float var27 = 0.0F;
      Random var28 = new Random(long1);
      if (integer12 <= 0) {
         int var29 = this.range * 16 - 16;
         integer12 = var29 - var28.nextInt(var29 / 4);
      }

      boolean var66 = false;
      if (integer11 == -1) {
         integer11 = integer12 / 2;
         var66 = true;
      }

      int var30 = var28.nextInt(integer12 / 2) + integer12 / 4;
      boolean var31 = var28.nextInt(6) == 0;

      while (integer11 < integer12) {
         double var32 = 1.5 + MathHelper.sin(integer11 * (float) Math.PI / integer12) * float8 * 1.0F;
         double var34 = var32 * double13;
         float var36 = MathHelper.cos(float10);
         float var37 = MathHelper.sin(float10);
         double5 += MathHelper.cos(float9) * var36;
         double6 += var37;
         double7 += MathHelper.sin(float9) * var36;
         if (var31) {
            float10 *= 0.92F;
         } else {
            float10 *= 0.7F;
         }

         float10 += var27 * 0.1F;
         float9 += var26 * 0.1F;
         var27 *= 0.9F;
         var26 *= 0.75F;
         var27 += (var28.nextFloat() - var28.nextFloat()) * var28.nextFloat() * 2.0F;
         var26 += (var28.nextFloat() - var28.nextFloat()) * var28.nextFloat() * 4.0F;
         if (!var66 && integer11 == var30 && float8 > 1.0F) {
            this.func_180704_a(
               var28.nextLong(),
               integer2,
               integer3,
               chunkPrimer,
               double5,
               double6,
               double7,
               var28.nextFloat() * 0.5F + 0.5F,
               float9 - (float) (Math.PI / 2),
               float10 / 3.0F,
               integer11,
               integer12,
               1.0
            );
            this.func_180704_a(
               var28.nextLong(),
               integer2,
               integer3,
               chunkPrimer,
               double5,
               double6,
               double7,
               var28.nextFloat() * 0.5F + 0.5F,
               float9 + (float) (Math.PI / 2),
               float10 / 3.0F,
               integer11,
               integer12,
               1.0
            );
            return;
         }

         if (var66 || var28.nextInt(4) != 0) {
            double var38 = double5 - var22;
            double var40 = double7 - var24;
            double var42 = integer12 - integer11;
            double var44 = float8 + 2.0F + 16.0F;
            if (var38 * var38 + var40 * var40 - var42 * var42 > var44 * var44) {
               return;
            }

            if (double5 >= var22 - 16.0 - var32 * 2.0
               && double7 >= var24 - 16.0 - var32 * 2.0
               && double5 <= var22 + 16.0 + var32 * 2.0
               && double7 <= var24 + 16.0 + var32 * 2.0) {
               int var46 = MathHelper.floor_double(double5 - var32) - integer2 * 16 - 1;
               int var47 = MathHelper.floor_double(double5 + var32) - integer2 * 16 + 1;
               int var48 = MathHelper.floor_double(double6 - var34) - 1;
               int var49 = MathHelper.floor_double(double6 + var34) + 1;
               int var50 = MathHelper.floor_double(double7 - var32) - integer3 * 16 - 1;
               int var51 = MathHelper.floor_double(double7 + var32) - integer3 * 16 + 1;
               if (var46 < 0) {
                  var46 = 0;
               }

               if (var47 > 16) {
                  var47 = 16;
               }

               if (var48 < 1) {
                  var48 = 1;
               }

               if (var49 > 120) {
                  var49 = 120;
               }

               if (var50 < 0) {
                  var50 = 0;
               }

               if (var51 > 16) {
                  var51 = 16;
               }

               boolean var52 = false;

               for (int var53 = var46; !var52 && var53 < var47; var53++) {
                  for (int var54 = var50; !var52 && var54 < var51; var54++) {
                     for (int var55 = var49 + 1; !var52 && var55 >= var48 - 1; var55--) {
                        if (var55 >= 0 && var55 < 128) {
                           IBlockState var56 = chunkPrimer.getBlockState(var53, var55, var54);
                           if (var56.getBlock() == Blocks.flowing_lava || var56.getBlock() == Blocks.lava) {
                              var52 = true;
                           }

                           if (var55 != var48 - 1 && var53 != var46 && var53 != var47 - 1 && var54 != var50 && var54 != var51 - 1) {
                              var55 = var48;
                           }
                        }
                     }
                  }
               }

               if (!var52) {
                  for (int var67 = var46; var67 < var47; var67++) {
                     double var68 = (var67 + integer2 * 16 + 0.5 - double5) / var32;

                     for (int var69 = var50; var69 < var51; var69++) {
                        double var57 = (var69 + integer3 * 16 + 0.5 - double7) / var32;

                        for (int var59 = var49; var59 > var48; var59--) {
                           double var60 = (var59 - 1 + 0.5 - double6) / var34;
                           if (var60 > -0.7 && var68 * var68 + var60 * var60 + var57 * var57 < 1.0) {
                              IBlockState var62 = chunkPrimer.getBlockState(var67, var59, var69);
                              if (var62.getBlock() == Blocks.netherrack || var62.getBlock() == Blocks.dirt || var62.getBlock() == Blocks.grass) {
                                 chunkPrimer.setBlockState(var67, var59, var69, Blocks.air.getDefaultState());
                              }
                           }
                        }
                     }
                  }

                  if (var66) {
                     break;
                  }
               }
            }
         }

         integer11++;
      }
   }

   @Override
   protected void recursiveGenerate(World world, int integer2, int integer3, int integer4, int integer5, ChunkPrimer chunkPrimer) {
      int var10 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(10) + 1) + 1);
      if (this.rand.nextInt(5) != 0) {
         var10 = 0;
      }

      for (int var11 = 0; var11 < var10; var11++) {
         double var12 = integer2 * 16 + this.rand.nextInt(16);
         double var14 = this.rand.nextInt(128);
         double var16 = integer3 * 16 + this.rand.nextInt(16);
         int var18 = 1;
         if (this.rand.nextInt(4) == 0) {
            this.func_180705_a(this.rand.nextLong(), integer4, integer5, chunkPrimer, var12, var14, var16);
            var18 = 1 + this.rand.nextInt(4);
         }

         for (int var19 = 0; var19 < var18; var19++) {
            float var20 = this.rand.nextFloat() * (float) Math.PI * 2.0F;
            float var21 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
            float var22 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
            this.func_180704_a(this.rand.nextLong(), integer4, integer5, chunkPrimer, var12, var14, var16, var22 * 2.0F, var20, var21, 0, 0, 0.5);
         }
      }
   }
}
