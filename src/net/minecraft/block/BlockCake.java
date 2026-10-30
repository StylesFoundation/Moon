package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCake extends Block {
   public static final PropertyInteger BITES = PropertyInteger.create("bites", 0, 6);

   protected BlockCake() {
      super(Material.cake);
      this.setDefaultState(this.blockState.getBaseState().withProperty(BITES, 0));
      this.setTickRandomly(true);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      float var4 = (1 + iBlockAccess.getBlockState(blockPos).getValue(BITES) * 2) / 16.0F;
      this.setBlockBounds(var4, 0.0F, 0.0625F, 0.9375F, 0.5F, 0.9375F);
   }

   @Override
   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.5F, 0.9375F);
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      float var5 = (1 + iBlockState.getValue(BITES) * 2) / 16.0F;
      return new AxisAlignedBB(
         blockPos.getX() + var5,
         blockPos.getY(),
         blockPos.getZ() + 0.0625F,
         blockPos.getX() + 1 - 0.0625F,
         blockPos.getY() + 0.5F,
         blockPos.getZ() + 1 - 0.0625F
      );
   }

   @Override
   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos blockPos) {
      return this.getCollisionBoundingBox(world, blockPos, world.getBlockState(blockPos));
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
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      this.eatCake(world, blockPos, iBlockState, entityPlayer);
      return true;
   }

   @Override
   public void onBlockClicked(World world, BlockPos blockPos, EntityPlayer entityPlayer) {
      this.eatCake(world, blockPos, world.getBlockState(blockPos), entityPlayer);
   }

   private void eatCake(World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer) {
      if (entityPlayer.canEat(false)) {
         entityPlayer.triggerAchievement(StatList.field_181724_H);
         entityPlayer.getFoodStats().addStats(2, 0.1F);
         int var8 = iBlockState.getValue(BITES);
         if (var8 < 6) {
            world.setBlockState(blockPos, iBlockState.withProperty(BITES, var8 + 1), 3);
         } else {
            world.setBlockToAir(blockPos);
         }
      }
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return super.canPlaceBlockAt(world, blockPos) && this.canBlockStay(world, blockPos);
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!this.canBlockStay(world, blockPos)) {
         world.setBlockToAir(blockPos);
      }
   }

   private boolean canBlockStay(World world, BlockPos blockPos) {
      return world.getBlockState(blockPos.down()).getBlock().getMaterial().isSolid();
   }

   @Override
   public int quantityDropped(Random random) {
      return 0;
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return null;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Items.cake;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(BITES, integer);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(BITES);
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, BITES);
   }

   @Override
   public int getComparatorInputOverride(World world, BlockPos blockPos) {
      return (7 - world.getBlockState(blockPos).getValue(BITES)) * 2;
   }

   @Override
   public boolean hasComparatorInputOverride() {
      return true;
   }
}
