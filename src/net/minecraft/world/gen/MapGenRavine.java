package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MapGenRavine extends MapGenBase {
   private final float[] field_75046_d = new float[1024];

   protected void func_180707_a(
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
      Random var22 = new Random(long1);
      double var23 = integer2 * 16 + 8;
      double var25 = integer3 * 16 + 8;
      float var27 = 0.0F;
      float var28 = 0.0F;
      if (integer12 <= 0) {
         int var29 = this.range * 16 - 16;
         integer12 = var29 - var22.nextInt(var29 / 4);
      }

      boolean var66 = false;
      if (integer11 == -1) {
         integer11 = integer12 / 2;
         var66 = true;
      }

      float var30 = 1.0F;

      for (int var31 = 0; var31 < 256; var31++) {
         if (var31 == 0 || var22.nextInt(3) == 0) {
            var30 = 1.0F + var22.nextFloat() * var22.nextFloat() * 1.0F;
         }

         this.field_75046_d[var31] = var30 * var30;
      }

      for (; integer11 < integer12; integer11++) {
         double var67 = 1.5 + MathHelper.sin(integer11 * (float) Math.PI / integer12) * float8 * 1.0F;
         double var33 = var67 * double13;
         var67 *= var22.nextFloat() * 0.25 + 0.75;
         var33 *= var22.nextFloat() * 0.25 + 0.75;
         float var35 = MathHelper.cos(float10);
         float var36 = MathHelper.sin(float10);
         double5 += MathHelper.cos(float9) * var35;
         double6 += var36;
         double7 += MathHelper.sin(float9) * var35;
         float10 = float10 * 0.7F + var28 * 0.05F;
         float9 += var27 * 0.05F;
         var28 *= 0.8F;
         var27 *= 0.5F;
         var28 += (var22.nextFloat() - var22.nextFloat()) * var22.nextFloat() * 2.0F;
         var27 += (var22.nextFloat() - var22.nextFloat()) * var22.nextFloat() * 4.0F;
         if (var66 || var22.nextInt(4) != 0) {
            double var37 = double5 - var23;
            double var39 = double7 - var25;
            double var41 = integer12 - integer11;
            double var43 = float8 + 2.0F + 16.0F;
            if (var37 * var37 + var39 * var39 - var41 * var41 > var43 * var43) {
               return;
            }

            if (double5 >= var23 - 16.0 - var67 * 2.0
               && double7 >= var25 - 16.0 - var67 * 2.0
               && double5 <= var23 + 16.0 + var67 * 2.0
               && double7 <= var25 + 16.0 + var67 * 2.0) {
               int var45 = MathHelper.floor_double(double5 - var67) - integer2 * 16 - 1;
               int var46 = MathHelper.floor_double(double5 + var67) - integer2 * 16 + 1;
               int var47 = MathHelper.floor_double(double6 - var33) - 1;
               int var48 = MathHelper.floor_double(double6 + var33) + 1;
               int var49 = MathHelper.floor_double(double7 - var67) - integer3 * 16 - 1;
               int var50 = MathHelper.floor_double(double7 + var67) - integer3 * 16 + 1;
               if (var45 < 0) {
                  var45 = 0;
               }

               if (var46 > 16) {
                  var46 = 16;
               }

               if (var47 < 1) {
                  var47 = 1;
               }

               if (var48 > 248) {
                  var48 = 248;
               }

               if (var49 < 0) {
                  var49 = 0;
               }

               if (var50 > 16) {
                  var50 = 16;
               }

               boolean var51 = false;

               for (int var52 = var45; !var51 && var52 < var46; var52++) {
                  for (int var53 = var49; !var51 && var53 < var50; var53++) {
                     for (int var54 = var48 + 1; !var51 && var54 >= var47 - 1; var54--) {
                        if (var54 >= 0 && var54 < 256) {
                           IBlockState var55 = chunkPrimer.getBlockState(var52, var54, var53);
                           if (var55.getBlock() == Blocks.flowing_water || var55.getBlock() == Blocks.water) {
                              var51 = true;
                           }

                           if (var54 != var47 - 1 && var52 != var45 && var52 != var46 - 1 && var53 != var49 && var53 != var50 - 1) {
                              var54 = var47;
                           }
                        }
                     }
                  }
               }

               if (!var51) {
                  BlockPos.MutableBlockPos var70 = new BlockPos.MutableBlockPos();

                  for (int var71 = var45; var71 < var46; var71++) {
                     double var72 = (var71 + integer2 * 16 + 0.5 - double5) / var67;

                     for (int var56 = var49; var56 < var50; var56++) {
                        double var57 = (var56 + integer3 * 16 + 0.5 - double7) / var67;
                        boolean var59 = false;
                        if (var72 * var72 + var57 * var57 < 1.0) {
                           for (int var60 = var48; var60 > var47; var60--) {
                              double var61 = (var60 - 1 + 0.5 - double6) / var33;
                              if ((var72 * var72 + var57 * var57) * this.field_75046_d[var60 - 1] + var61 * var61 / 6.0 < 1.0) {
                                 IBlockState var63 = chunkPrimer.getBlockState(var71, var60, var56);
                                 if (var63.getBlock() == Blocks.grass) {
                                    var59 = true;
                                 }

                                 if (var63.getBlock() == Blocks.stone || var63.getBlock() == Blocks.dirt || var63.getBlock() == Blocks.grass) {
                                    if (var60 - 1 < 10) {
                                       chunkPrimer.setBlockState(var71, var60, var56, Blocks.flowing_lava.getDefaultState());
                                    } else {
                                       chunkPrimer.setBlockState(var71, var60, var56, Blocks.air.getDefaultState());
                                       if (var59 && chunkPrimer.getBlockState(var71, var60 - 1, var56).getBlock() == Blocks.dirt) {
                                          var70.set(var71 + integer2 * 16, 0, var56 + integer3 * 16);
                                          chunkPrimer.setBlockState(var71, var60 - 1, var56, this.worldObj.getBiomeGenForCoords(var70).topBlock);
                                       }
                                    }
                                 }
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
      }
   }

   @Override
   protected void recursiveGenerate(World world, int integer2, int integer3, int integer4, int integer5, ChunkPrimer chunkPrimer) {
      if (this.rand.nextInt(50) == 0) {
         double var10 = integer2 * 16 + this.rand.nextInt(16);
         double var12 = this.rand.nextInt(this.rand.nextInt(40) + 8) + 20;
         double var14 = integer3 * 16 + this.rand.nextInt(16);

         for (int var17 = 0; var17 < 1; var17++) {
            float var18 = this.rand.nextFloat() * (float) Math.PI * 2.0F;
            float var19 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
            float var20 = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;
            this.func_180707_a(this.rand.nextLong(), integer4, integer5, chunkPrimer, var10, var12, var14, var20, var18, var19, 0, 0, 3.0);
         }
      }
   }
}
