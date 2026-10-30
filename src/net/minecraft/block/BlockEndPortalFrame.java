package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockEndPortalFrame extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
   public static final PropertyBool EYE = PropertyBool.create("eye");

   public BlockEndPortalFrame() {
      super(Material.rock, MapColor.greenColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(EYE, Boolean.FALSE));
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
   }

   @Override
   public void addCollisionBoxesToList(
      World world, BlockPos blockPos, IBlockState iBlockState, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, Entity entity
   ) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      if (world.getBlockState(blockPos).getValue(EYE)) {
         this.setBlockBounds(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
         super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      }

      this.setBlockBoundsForItemRender();
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return null;
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return this.getDefaultState().withProperty(FACING, entityLivingBase.getHorizontalFacing().getOpposite()).withProperty(EYE, Boolean.FALSE);
   }

   @Override
   public boolean hasComparatorInputOverride() {
      return true;
   }

   @Override
   public int getComparatorInputOverride(World world, BlockPos blockPos) {
      return world.getBlockState(blockPos).getValue(EYE) ? 15 : 0;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(EYE, (integer & 4) != 0).withProperty(FACING, EnumFacing.getHorizontal(integer & 3));
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(FACING).getHorizontalIndex();
      if (iBlockState.getValue(EYE)) {
         var5 |= 4;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, EYE);
   }
}
