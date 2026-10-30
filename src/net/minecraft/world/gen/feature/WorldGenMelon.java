package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenMelon extends WorldGenerator {
   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      for (int var7 = 0; var7 < 64; var7++) {
         BlockPos var8 = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
         if (Blocks.melon_block.canPlaceBlockAt(world, var8) && world.getBlockState(var8.down()).getBlock() == Blocks.grass) {
            world.setBlockState(var8, Blocks.melon_block.getDefaultState(), 2);
         }
      }

      return true;
   }
}
