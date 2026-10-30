package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenFire extends WorldGenerator {
   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      for (int var7 = 0; var7 < 64; var7++) {
         BlockPos var8 = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
         if (world.isAirBlock(var8) && world.getBlockState(var8.down()).getBlock() == Blocks.netherrack) {
            world.setBlockState(var8, Blocks.fire.getDefaultState(), 2);
         }
      }

      return true;
   }
}
