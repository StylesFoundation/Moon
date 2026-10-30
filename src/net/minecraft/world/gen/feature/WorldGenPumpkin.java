package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenPumpkin extends WorldGenerator {
   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      for (int var7 = 0; var7 < 64; var7++) {
         BlockPos var8 = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
         if (world.isAirBlock(var8) && world.getBlockState(var8.down()).getBlock() == Blocks.grass && Blocks.pumpkin.canPlaceBlockAt(world, var8)) {
            world.setBlockState(var8, Blocks.pumpkin.getDefaultState().withProperty(BlockPumpkin.FACING, EnumFacing.Plane.HORIZONTAL.random(random)), 2);
         }
      }

      return true;
   }
}
