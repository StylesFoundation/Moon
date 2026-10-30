package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenWaterlily extends WorldGenerator {
   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      for (int var7 = 0; var7 < 10; var7++) {
         int var8 = blockPos.getX() + random.nextInt(8) - random.nextInt(8);
         int var9 = blockPos.getY() + random.nextInt(4) - random.nextInt(4);
         int var10 = blockPos.getZ() + random.nextInt(8) - random.nextInt(8);
         if (world.isAirBlock(new BlockPos(var8, var9, var10)) && Blocks.waterlily.canPlaceBlockAt(world, new BlockPos(var8, var9, var10))) {
            world.setBlockState(new BlockPos(var8, var9, var10), Blocks.waterlily.getDefaultState(), 2);
         }
      }

      return true;
   }
}
