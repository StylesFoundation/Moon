package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenHellLava extends WorldGenerator {
   private final Block field_150553_a;
   private final boolean field_94524_b;

   public WorldGenHellLava(Block block, boolean boolean2) {
      this.field_150553_a = block;
      this.field_94524_b = boolean2;
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      if (world.getBlockState(blockPos.up()).getBlock() != Blocks.netherrack) {
         return false;
      }

      if (world.getBlockState(blockPos).getBlock().getMaterial() != Material.air && world.getBlockState(blockPos).getBlock() != Blocks.netherrack) {
         return false;
      }

      int var7 = 0;
      if (world.getBlockState(blockPos.west()).getBlock() == Blocks.netherrack) {
         var7++;
      }

      if (world.getBlockState(blockPos.east()).getBlock() == Blocks.netherrack) {
         var7++;
      }

      if (world.getBlockState(blockPos.north()).getBlock() == Blocks.netherrack) {
         var7++;
      }

      if (world.getBlockState(blockPos.south()).getBlock() == Blocks.netherrack) {
         var7++;
      }

      if (world.getBlockState(blockPos.down()).getBlock() == Blocks.netherrack) {
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

      if (world.isAirBlock(blockPos.down())) {
         var8++;
      }

      if (!this.field_94524_b && var7 == 4 && var8 == 1 || var7 == 5) {
         world.setBlockState(blockPos, this.field_150553_a.getDefaultState(), 2);
         world.forceBlockUpdateTick(this.field_150553_a, blockPos, random);
      }

      return true;
   }
}
