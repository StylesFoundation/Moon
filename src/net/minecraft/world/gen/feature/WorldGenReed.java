package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenReed extends WorldGenerator {
   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      for (int var7 = 0; var7 < 20; var7++) {
         BlockPos var8 = blockPos.add(random.nextInt(4) - random.nextInt(4), 0, random.nextInt(4) - random.nextInt(4));
         if (world.isAirBlock(var8)) {
            BlockPos var9 = var8.down();
            if (world.getBlockState(var9.west()).getBlock().getMaterial() == Material.water
               || world.getBlockState(var9.east()).getBlock().getMaterial() == Material.water
               || world.getBlockState(var9.north()).getBlock().getMaterial() == Material.water
               || world.getBlockState(var9.south()).getBlock().getMaterial() == Material.water) {
               int var10 = 2 + random.nextInt(random.nextInt(3) + 1);

               for (int var11 = 0; var11 < var10; var11++) {
                  if (Blocks.reeds.canBlockStay(world, var8)) {
                     world.setBlockState(var8.up(var11), Blocks.reeds.getDefaultState(), 2);
                  }
               }
            }
         }
      }

      return true;
   }
}
