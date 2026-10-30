package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenCactus extends WorldGenerator {
   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      for (int var7 = 0; var7 < 10; var7++) {
         BlockPos var8 = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
         if (world.isAirBlock(var8)) {
            int var9 = 1 + random.nextInt(random.nextInt(3) + 1);

            for (int var10 = 0; var10 < var9; var10++) {
               if (Blocks.cactus.canBlockStay(world, var8)) {
                  world.setBlockState(var8.up(var10), Blocks.cactus.getDefaultState(), 2);
               }
            }
         }
      }

      return true;
   }
}
