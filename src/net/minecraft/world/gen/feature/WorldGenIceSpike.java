package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenIceSpike extends WorldGenerator {
   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      while (world.isAirBlock(blockPos) && blockPos.getY() > 2) {
         blockPos = blockPos.down();
      }

      if (world.getBlockState(blockPos).getBlock() != Blocks.snow) {
         return false;
      }

      blockPos = blockPos.up(random.nextInt(4));
      int var7 = random.nextInt(4) + 7;
      int var8 = var7 / 4 + random.nextInt(2);
      if (var8 > 1 && random.nextInt(60) == 0) {
         blockPos = blockPos.up(10 + random.nextInt(30));
      }

      for (int var9 = 0; var9 < var7; var9++) {
         float var10 = (1.0F - (float)var9 / var7) * var8;
         int var11 = MathHelper.ceiling_float_int(var10);

         for (int var12 = -var11; var12 <= var11; var12++) {
            float var13 = MathHelper.abs_int(var12) - 0.25F;

            for (int var14 = -var11; var14 <= var11; var14++) {
               float var15 = MathHelper.abs_int(var14) - 0.25F;
               if ((var12 == 0 && var14 == 0 || var13 * var13 + var15 * var15 <= var10 * var10)
                  && (var12 != -var11 && var12 != var11 && var14 != -var11 && var14 != var11 || random.nextFloat() <= 0.75F)) {
                  Block var16 = world.getBlockState(blockPos.add(var12, var9, var14)).getBlock();
                  if (var16.getMaterial() == Material.air || var16 == Blocks.dirt || var16 == Blocks.snow || var16 == Blocks.ice) {
                     this.setBlockAndNotifyAdequately(world, blockPos.add(var12, var9, var14), Blocks.packed_ice.getDefaultState());
                  }

                  if (var9 != 0 && var11 > 1) {
                     var16 = world.getBlockState(blockPos.add(var12, -var9, var14)).getBlock();
                     if (var16.getMaterial() == Material.air || var16 == Blocks.dirt || var16 == Blocks.snow || var16 == Blocks.ice) {
                        this.setBlockAndNotifyAdequately(world, blockPos.add(var12, -var9, var14), Blocks.packed_ice.getDefaultState());
                     }
                  }
               }
            }
         }
      }

      int var18 = var8 - 1;
      if (var18 < 0) {
         var18 = 0;
      } else if (var18 > 1) {
         var18 = 1;
      }

      for (int var19 = -var18; var19 <= var18; var19++) {
         for (int var20 = -var18; var20 <= var18; var20++) {
            BlockPos var21 = blockPos.add(var19, -1, var20);
            int var22 = 50;
            if (Math.abs(var19) == 1 && Math.abs(var20) == 1) {
               var22 = random.nextInt(5);
            }

            while (var21.getY() > 50) {
               Block var23 = world.getBlockState(var21).getBlock();
               if (var23.getMaterial() != Material.air && var23 != Blocks.dirt && var23 != Blocks.snow && var23 != Blocks.ice && var23 != Blocks.packed_ice) {
                  break;
               }

               this.setBlockAndNotifyAdequately(world, var21, Blocks.packed_ice.getDefaultState());
               var21 = var21.down();
               if (--var22 <= 0) {
                  var21 = var21.down(random.nextInt(5) + 1);
                  var22 = random.nextInt(5);
               }
            }
         }
      }

      return true;
   }
}
