package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPane extends Block {
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");
   private final boolean canDrop;

   protected BlockPane(Material material, boolean boolean2) {
      super(material);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(NORTH, Boolean.FALSE)
            .withProperty(EAST, Boolean.FALSE)
            .withProperty(SOUTH, Boolean.FALSE)
            .withProperty(WEST, Boolean.FALSE)
      );
      this.canDrop = boolean2;
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      return iBlockState.withProperty(NORTH, this.canPaneConnectToBlock(iBlockAccess.getBlockState(blockPos.north()).getBlock()))
         .withProperty(SOUTH, this.canPaneConnectToBlock(iBlockAccess.getBlockState(blockPos.south()).getBlock()))
         .withProperty(WEST, this.canPaneConnectToBlock(iBlockAccess.getBlockState(blockPos.west()).getBlock()))
         .withProperty(EAST, this.canPaneConnectToBlock(iBlockAccess.getBlockState(blockPos.east()).getBlock()));
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return !this.canDrop ? null : super.getItemDropped(iBlockState, random, integer);
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
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return iBlockAccess.getBlockState(blockPos).getBlock() != this && super.shouldSideBeRendered(iBlockAccess, blockPos, enumFacing);
   }

   @Override
   public void addCollisionBoxesToList(
      World world, BlockPos blockPos, IBlockState iBlockState, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, Entity entity
   ) {
      boolean var10 = this.canPaneConnectToBlock(world.getBlockState(blockPos.north()).getBlock());
      boolean var11 = this.canPaneConnectToBlock(world.getBlockState(blockPos.south()).getBlock());
      boolean var12 = this.canPaneConnectToBlock(world.getBlockState(blockPos.west()).getBlock());
      boolean var13 = this.canPaneConnectToBlock(world.getBlockState(blockPos.east()).getBlock());
      if ((!var12 || !var13) && (var12 || var13 || var10 || var11)) {
         if (var12) {
            this.setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
            super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
         } else if (var13) {
            this.setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
            super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
         }
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
         super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      }

      if ((!var10 || !var11) && (var12 || var13 || var10 || var11)) {
         if (var10) {
            this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
            super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
         } else if (var11) {
            this.setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
            super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
         }
      } else {
         this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
         super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      }
   }

   @Override
   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      float var6 = 0.4375F;
      float var7 = 0.5625F;
      float var8 = 0.4375F;
      float var9 = 0.5625F;
      boolean var10 = this.canPaneConnectToBlock(iBlockAccess.getBlockState(blockPos.north()).getBlock());
      boolean var11 = this.canPaneConnectToBlock(iBlockAccess.getBlockState(blockPos.south()).getBlock());
      boolean var12 = this.canPaneConnectToBlock(iBlockAccess.getBlockState(blockPos.west()).getBlock());
      boolean var13 = this.canPaneConnectToBlock(iBlockAccess.getBlockState(blockPos.east()).getBlock());
      if ((!var12 || !var13) && (var12 || var13 || var10 || var11)) {
         if (var12) {
            var6 = 0.0F;
         } else if (var13) {
            var7 = 1.0F;
         }
      } else {
         var6 = 0.0F;
         var7 = 1.0F;
      }

      if ((!var10 || !var11) && (var12 || var13 || var10 || var11)) {
         if (var10) {
            var8 = 0.0F;
         } else if (var11) {
            var9 = 1.0F;
         }
      } else {
         var8 = 0.0F;
         var9 = 1.0F;
      }

      this.setBlockBounds(var6, 0.0F, var8, var7, 1.0F, var9);
   }

   public final boolean canPaneConnectToBlock(Block block) {
      return block.isFullBlock() || block == Blocks.glass || block == Blocks.stained_glass || block == Blocks.stained_glass_pane || block instanceof BlockPane;
   }

   @Override
   protected boolean canSilkHarvest() {
      return true;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT_MIPPED;
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return 0;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, NORTH, EAST, WEST, SOUTH);
   }
}
