package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenShrub extends WorldGenTrees {
   private final IBlockState leavesMetadata;
   private final IBlockState woodMetadata;

   public WorldGenShrub(IBlockState iBlockState1, IBlockState iBlockState2) {
      super(false);
      this.woodMetadata = iBlockState1;
      this.leavesMetadata = iBlockState2;
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      Block var7;
      while (((var7 = world.getBlockState(blockPos).getBlock()).getMaterial() == Material.air || var7.getMaterial() == Material.leaves) && blockPos.getY() > 0) {
         blockPos = blockPos.down();
      }

      Block var8 = world.getBlockState(blockPos).getBlock();
      if (var8 == Blocks.dirt || var8 == Blocks.grass) {
         blockPos = blockPos.up();
         this.setBlockAndNotifyAdequately(world, blockPos, this.woodMetadata);

         for (int var9 = blockPos.getY(); var9 <= blockPos.getY() + 2; var9++) {
            int var10 = var9 - blockPos.getY();
            int var11 = 2 - var10;

            for (int var12 = blockPos.getX() - var11; var12 <= blockPos.getX() + var11; var12++) {
               int var13 = var12 - blockPos.getX();

               for (int var14 = blockPos.getZ() - var11; var14 <= blockPos.getZ() + var11; var14++) {
                  int var15 = var14 - blockPos.getZ();
                  if (Math.abs(var13) != var11 || Math.abs(var15) != var11 || random.nextInt(2) != 0) {
                     BlockPos var16 = new BlockPos(var12, var9, var14);
                     if (!world.getBlockState(var16).getBlock().isFullBlock()) {
                        this.setBlockAndNotifyAdequately(world, var16, this.leavesMetadata);
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}
