package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockCactus extends Block {
   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);

   protected BlockCactus() {
      super(Material.cactus);
      this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      BlockPos var8 = blockPos.up();
      if (world.isAirBlock(var8)) {
         int var9 = 1;

         while (world.getBlockState(blockPos.down(var9)).getBlock() == this) {
            var9++;
         }

         if (var9 < 3) {
            int var10 = iBlockState.getValue(AGE);
            if (var10 == 15) {
               world.setBlockState(var8, this.getDefaultState());
               IBlockState var11 = iBlockState.withProperty(AGE, 0);
               world.setBlockState(blockPos, var11, 4);
               this.onNeighborBlockChange(world, var8, var11, this);
            } else {
               world.setBlockState(blockPos, iBlockState.withProperty(AGE, var10 + 1), 4);
            }
         }
      }
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      return new AxisAlignedBB(
         blockPos.getX() + 0.0625F,
         blockPos.getY(),
         blockPos.getZ() + 0.0625F,
         blockPos.getX() + 1 - 0.0625F,
         blockPos.getY() + 1 - 0.0625F,
         blockPos.getZ() + 1 - 0.0625F
      );
   }

   @Override
   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos blockPos) {
      return new AxisAlignedBB(
         blockPos.getX() + 0.0625F,
         blockPos.getY(),
         blockPos.getZ() + 0.0625F,
         blockPos.getX() + 1 - 0.0625F,
         blockPos.getY() + 1,
         blockPos.getZ() + 1 - 0.0625F
      );
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return super.canPlaceBlockAt(world, blockPos) && this.canBlockStay(world, blockPos);
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!this.canBlockStay(world, blockPos)) {
         world.Method329(blockPos, true);
      }
   }

   public boolean canBlockStay(World world, BlockPos blockPos) {
      for (EnumFacing var7 : EnumFacing.Plane.HORIZONTAL) {
         if (world.getBlockState(blockPos.offset(var7)).getBlock().getMaterial().isSolid()) {
            return false;
         }
      }

      Block var8 = world.getBlockState(blockPos.down()).getBlock();
      return var8 == Blocks.cactus || var8 == Blocks.sand;
   }

   @Override
   public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState iBlockState, Entity entity) {
      entity.attackEntityFrom(DamageSource.cactus, 1.0F);
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
