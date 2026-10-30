package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockPotato extends BlockCrops {
   @Override
   protected Item getSeed() {
      return Items.potato;
   }

   @Override
   protected Item getCrop() {
      return Items.potato;
   }

   @Override
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
      super.dropBlockAsItemWithChance(world, blockPos, iBlockState, float4, integer);
      if (!world.isRemote && iBlockState.getValue(AGE) >= 7 && world.rand.nextInt(50) == 0) {
         spawnAsEntity(world, blockPos, new ItemStack(Items.poisonous_potato));
      }
   }
}
