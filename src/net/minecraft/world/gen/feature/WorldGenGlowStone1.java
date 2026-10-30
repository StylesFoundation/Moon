package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenGlowStone1 extends WorldGenerator {
   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      if (!world.isAirBlock(blockPos)) {
         return false;
      }

      if (world.getBlockState(blockPos.up()).getBlock() != Blocks.netherrack) {
         return false;
      }

      world.setBlockState(blockPos, Blocks.glowstone.getDefaultState(), 2);

      for (int var7 = 0; var7 < 1500; var7++) {
         BlockPos var8 = blockPos.add(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));
         if (world.getBlockState(var8).getBlock().getMaterial() == Material.air) {
            int var9 = 0;

            for (EnumFacing var13 : EnumFacing.values()) {
               if (world.getBlockState(var8.offset(var13)).getBlock() == Blocks.glowstone) {
                  var9++;
               }

               if (var9 > 1) {
                  break;
               }
            }

            if (var9 == 1) {
               world.setBlockState(var8, Blocks.glowstone.getDefaultState(), 2);
            }
         }
      }

      return true;
   }
}
