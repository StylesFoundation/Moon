package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenIcePath extends WorldGenerator {
   private final Block block = Blocks.packed_ice;
   private final int basePathWidth;

   public WorldGenIcePath(int integer) {
      this.basePathWidth = integer;
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      while (world.isAirBlock(blockPos) && blockPos.getY() > 2) {
         blockPos = blockPos.down();
      }

      if (world.getBlockState(blockPos).getBlock() != Blocks.snow) {
         return false;
      }

      int var7 = random.nextInt(this.basePathWidth - 2) + 2;

      for (int var9 = blockPos.getX() - var7; var9 <= blockPos.getX() + var7; var9++) {
         for (int var10 = blockPos.getZ() - var7; var10 <= blockPos.getZ() + var7; var10++) {
            int var11 = var9 - blockPos.getX();
            int var12 = var10 - blockPos.getZ();
            if (var11 * var11 + var12 * var12 <= var7 * var7) {
               for (int var13 = blockPos.getY() - 1; var13 <= blockPos.getY() + 1; var13++) {
                  BlockPos var14 = new BlockPos(var9, var13, var10);
                  Block var15 = world.getBlockState(var14).getBlock();
                  if (var15 == Blocks.dirt || var15 == Blocks.snow || var15 == Blocks.ice) {
                     world.setBlockState(var14, this.block.getDefaultState(), 2);
                  }
               }
            }
         }
      }

      return true;
   }
}
