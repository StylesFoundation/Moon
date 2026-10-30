package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockReed extends Block {
   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);

   protected BlockReed() {
      super(Material.plants);
      this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
      this.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
      this.setTickRandomly(true);
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if ((world.getBlockState(blockPos.down()).getBlock() == Blocks.reeds || this.checkForDrop(world, blockPos, iBlockState))
         && world.isAirBlock(blockPos.up())) {
         int var8 = 1;

         while (world.getBlockState(blockPos.down(var8)).getBlock() == this) {
            var8++;
         }

         if (var8 < 3) {
            int var9 = iBlockState.getValue(AGE);
            if (var9 == 15) {
               world.setBlockState(blockPos.up(), this.getDefaultState());
               world.setBlockState(blockPos, iBlockState.withProperty(AGE, 0), 4);
            } else {
               world.setBlockState(blockPos, iBlockState.withProperty(AGE, var9 + 1), 4);
            }
         }
      }
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      Block var6 = world.getBlockState(blockPos.down()).getBlock();
      if (var6 == this) {
         return true;
      }

      if (var6 != Blocks.grass && var6 != Blocks.dirt && var6 != Blocks.sand) {
         return false;
      }

      for (EnumFacing var8 : EnumFacing.Plane.HORIZONTAL) {
         if (world.getBlockState(blockPos.offset(var8).down()).getBlock().getMaterial() == Material.water) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      this.checkForDrop(world, blockPos, iBlockState);
   }

   protected final boolean checkForDrop(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (this.canBlockStay(world, blockPos)) {
         return true;
      }

      this.dropBlockAsItem(world, blockPos, iBlockState, 0);
      world.setBlockToAir(blockPos);
      return false;
   }

   public boolean canBlockStay(World world, BlockPos blockPos) {
      return this.canPlaceBlockAt(world, blockPos);
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      return null;
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Items.reeds;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Items.reeds;
   }

   @Override
   public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int integer) {
      return iBlockAccess.getBiomeGenForCoords(blockPos).getGrassColorAtPos(blockPos);
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(AGE, integer);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(AGE);
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, AGE);
   }
}
