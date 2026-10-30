package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class WorldGenAbstractTree extends WorldGenerator {
   public WorldGenAbstractTree(boolean boolean1) {
      super(boolean1);
   }

   protected boolean func_150523_a(Block block) {
      Material var5 = block.getMaterial();
      return var5 == Material.air
         || var5 == Material.leaves
         || block == Blocks.grass
         || block == Blocks.dirt
         || block == Blocks.log
         || block == Blocks.log2
         || block == Blocks.sapling
         || block == Blocks.vine;
   }

   public void func_180711_a(World world, Random random, BlockPos blockPos) {
   }

   protected void func_175921_a(World world, BlockPos blockPos) {
      if (world.getBlockState(blockPos).getBlock() != Blocks.dirt) {
         this.setBlockAndNotifyAdequately(world, blockPos, Blocks.dirt.getDefaultState());
      }
   }
}
