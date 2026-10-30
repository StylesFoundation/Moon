package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;

public class BlockMushroom extends BlockBush implements IGrowable {
   protected BlockMushroom() {
      this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.4F, 0.7F);
      this.setTickRandomly(true);
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (random.nextInt(25) == 0) {
         int var8 = 5;

         for (BlockPos var11 : BlockPos.getAllInBoxMutable(blockPos.add(-4, -1, -4), blockPos.add(4, 1, 4))) {
            if (world.getBlockState(var11).getBlock() == this) {
               if (--var8 <= 0) {
                  return;
               }
            }
         }

         BlockPos var12 = blockPos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

         for (int var13 = 0; var13 < 4; var13++) {
            if (world.isAirBlock(var12) && this.canBlockStay(world, var12, this.getDefaultState())) {
               blockPos = var12;
            }

            var12 = blockPos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
         }

         if (world.isAirBlock(var12) && this.canBlockStay(world, var12, this.getDefaultState())) {
            world.setBlockState(var12, this.getDefaultState(), 2);
         }
      }
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return super.canPlaceBlockAt(world, blockPos) && this.canBlockStay(world, blockPos, this.getDefaultState());
   }

   @Override
   protected boolean canPlaceBlockOn(Block block) {
      return block.isFullBlock();
   }

   @Override
   public boolean canBlockStay(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
         IBlockState var7 = world.getBlockState(blockPos.down());
         return var7.getBlock() == Blocks.mycelium
            || var7.getBlock() == Blocks.dirt && var7.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL
            || world.getLight(blockPos) < 13 && this.canPlaceBlockOn(var7.getBlock());
      } else {
         return false;
      }
   }

   public boolean generateBigMushroom(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      world.setBlockToAir(blockPos);
      WorldGenBigMushroom var8 = null;
      if (this == Blocks.brown_mushroom) {
         var8 = new WorldGenBigMushroom(Blocks.brown_mushroom_block);
      } else if (this == Blocks.red_mushroom) {
         var8 = new WorldGenBigMushroom(Blocks.red_mushroom_block);
      }

      if (var8 != null && var8.generate(world, random, blockPos)) {
         return true;
      }

      world.setBlockState(blockPos, iBlockState, 3);
      return false;
   }

   @Override
   public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean boolean4) {
      return true;
   }

   @Override
   public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      return random.nextFloat() < 0.4;
   }

   @Override
   public void grow(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      this.generateBigMushroom(world, blockPos, iBlockState, random);
   }
}
