package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenLiquids extends WorldGenerator {
   private final Block block;

   public WorldGenLiquids(Block block) {
      this.block = block;
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      if (world.getBlockState(blockPos.up()).getBlock() != Blocks.stone) {
         return false;
      }

      if (world.getBlockState(blockPos.down()).getBlock() != Blocks.stone) {
         return false;
      }

      if (world.getBlockState(blockPos).getBlock().getMaterial() != Material.air && world.getBlockState(blockPos).getBlock() != Blocks.stone) {
         return false;
      }

      int var7 = 0;
      if (world.getBlockState(blockPos.west()).getBlock() == Blocks.stone) {
         var7++;
      }

      if (world.getBlockState(blockPos.east()).getBlock() == Blocks.stone) {
         var7++;
      }

      if (world.getBlockState(blockPos.north()).getBlock() == Blocks.stone) {
         var7++;
      }

      if (world.getBlockState(blockPos.south()).getBlock() == Blocks.stone) {
         var7++;
      }

      int var8 = 0;
      if (world.isAirBlock(blockPos.west())) {
         var8++;
      }

      if (world.isAirBlock(blockPos.east())) {
         var8++;
      }

      if (world.isAirBlock(blockPos.north())) {
         var8++;
      }

      if (world.isAirBlock(blockPos.south())) {
         var8++;
      }

      if (var7 == 3 && var8 == 1) {
         world.setBlockState(blockPos, this.block.getDefaultState(), 2);
         world.forceBlockUpdateTick(this.block, blockPos, random);
      }

      return true;
   }
}
