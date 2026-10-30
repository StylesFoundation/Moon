package net.minecraft.world.gen.feature;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenMinable extends WorldGenerator {
   private final IBlockState oreBlock;
   private final int numberOfBlocks;
   private final Predicate<IBlockState> predicate;

   public WorldGenMinable(IBlockState iBlockState, int integer) {
      this(iBlockState, integer, BlockHelper.forBlock(Blocks.stone));
   }

   public WorldGenMinable(IBlockState iBlockState, int integer, Predicate<IBlockState> predicate) {
      this.oreBlock = iBlockState;
      this.numberOfBlocks = integer;
      this.predicate = predicate;
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      float var7 = random.nextFloat() * (float) Math.PI;
      double var8 = blockPos.getX() + 8 + MathHelper.sin(var7) * this.numberOfBlocks / 8.0F;
      double var10 = blockPos.getX() + 8 - MathHelper.sin(var7) * this.numberOfBlocks / 8.0F;
      double var12 = blockPos.getZ() + 8 + MathHelper.cos(var7) * this.numberOfBlocks / 8.0F;
      double var14 = blockPos.getZ() + 8 - MathHelper.cos(var7) * this.numberOfBlocks / 8.0F;
      double var16 = blockPos.getY() + random.nextInt(3) - 2;
      double var18 = blockPos.getY() + random.nextInt(3) - 2;

      for (int var20 = 0; var20 < this.numberOfBlocks; var20++) {
         float var21 = (float)var20 / this.numberOfBlocks;
         double var22 = var8 + (var10 - var8) * var21;
         double var24 = var16 + (var18 - var16) * var21;
         double var26 = var12 + (var14 - var12) * var21;
         double var28 = random.nextDouble() * this.numberOfBlocks / 16.0;
         double var30 = (MathHelper.sin((float) Math.PI * var21) + 1.0F) * var28 + 1.0;
         double var32 = (MathHelper.sin((float) Math.PI * var21) + 1.0F) * var28 + 1.0;
         int var34 = MathHelper.floor_double(var22 - var30 / 2.0);
         int var35 = MathHelper.floor_double(var24 - var32 / 2.0);
         int var36 = MathHelper.floor_double(var26 - var30 / 2.0);
         int var37 = MathHelper.floor_double(var22 + var30 / 2.0);
         int var38 = MathHelper.floor_double(var24 + var32 / 2.0);
         int var39 = MathHelper.floor_double(var26 + var30 / 2.0);

         for (int var40 = var34; var40 <= var37; var40++) {
            double var41 = (var40 + 0.5 - var22) / (var30 / 2.0);
            if (var41 * var41 < 1.0) {
               for (int var43 = var35; var43 <= var38; var43++) {
                  double var44 = (var43 + 0.5 - var24) / (var32 / 2.0);
                  if (var41 * var41 + var44 * var44 < 1.0) {
                     for (int var46 = var36; var46 <= var39; var46++) {
                        double var47 = (var46 + 0.5 - var26) / (var30 / 2.0);
                        if (var41 * var41 + var44 * var44 + var47 * var47 < 1.0) {
                           BlockPos var49 = new BlockPos(var40, var43, var46);
                           if (this.predicate.apply(world.getBlockState(var49))) {
                              world.setBlockState(var49, this.oreBlock, 2);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}
