package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenSand extends WorldGenerator {
   private final Block block;
   private final int radius;

   public WorldGenSand(Block block, int integer) {
      this.block = block;
      this.radius = integer;
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      if (world.getBlockState(blockPos).getBlock().getMaterial() != Material.water) {
         return false;
      }

      int var7 = random.nextInt(this.radius - 2) + 2;

      for (int var9 = blockPos.getX() - var7; var9 <= blockPos.getX() + var7; var9++) {
         for (int var10 = blockPos.getZ() - var7; var10 <= blockPos.getZ() + var7; var10++) {
            int var11 = var9 - blockPos.getX();
            int var12 = var10 - blockPos.getZ();
            if (var11 * var11 + var12 * var12 <= var7 * var7) {
               for (int var13 = blockPos.getY() - 2; var13 <= blockPos.getY() + 2; var13++) {
                  BlockPos var14 = new BlockPos(var9, var13, var10);
                  Block var15 = world.getBlockState(var14).getBlock();
                  if (var15 == Blocks.dirt || var15 == Blocks.grass) {
                     world.setBlockState(var14, this.block.getDefaultState(), 2);
                  }
               }
            }
         }
      }

      return true;
   }
}
