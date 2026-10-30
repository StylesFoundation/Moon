package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface IGrowable {
   boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean boolean4);

   boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState);

   void grow(World world, Random random, BlockPos blockPos, IBlockState iBlockState);
}
