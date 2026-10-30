package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHopper extends BlockContainer {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", enumFacing -> enumFacing != EnumFacing.UP);
   public static final PropertyBool ENABLED = PropertyBool.create("enabled");

   public BlockHopper() {
      super(Material.iron, MapColor.stoneColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN).withProperty(ENABLED, Boolean.TRUE));
      this.setCreativeTab(CreativeTabs.tabRedstone);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public void addCollisionBoxesToList(
      World world, BlockPos blockPos, IBlockState iBlockState, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, Entity entity
   ) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.125F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.125F);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      this.setBlockBounds(0.875F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      this.setBlockBounds(0.0F, 0.0F, 0.875F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      EnumFacing var12 = enumFacing.getOpposite();
      if (var12 == EnumFacing.UP) {
         var12 = EnumFacing.DOWN;
      }

      return this.getDefaultState().withProperty(FACING, var12).withProperty(ENABLED, Boolean.TRUE);
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntityHopper();
   }

   @Override
   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      super.onBlockPlacedBy(world, blockPos, iBlockState, entityLivingBase, itemStack);
      if (itemStack.hasDisplayName()) {
         TileEntity var9 = world.getTileEntity(blockPos);
         if (var9 instanceof TileEntityHopper) {
            ((TileEntityHopper)var9).setCustomName(itemStack.getDisplayName());
         }
      }
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.updateState(world, blockPos, iBlockState);
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!world.isRemote) {
         TileEntity var12 = world.getTileEntity(blockPos);
         if (var12 instanceof TileEntityHopper) {
            entityPlayer.displayGUIChest((TileEntityHopper)var12);
            entityPlayer.triggerAchievement(StatList.field_181732_P);
         }
      }

      return true;
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      this.updateState(world, blockPos, iBlockState);
   }

   private void updateState(World world, BlockPos blockPos, IBlockState iBlockState) {
      boolean var7 = !world.isBlockPowered(blockPos);
      if (var7 != iBlockState.getValue(ENABLED)) {
         world.setBlockState(blockPos, iBlockState.withProperty(ENABLED, var7), 4);
      }
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      TileEntity var7 = world.getTileEntity(blockPos);
      if (var7 instanceof TileEntityHopper) {
         InventoryHelper.dropInventoryItems(world, blockPos, (TileEntityHopper)var7);
         world.updateComparatorOutputLevel(blockPos, this);
      }

      super.breakBlock(world, blockPos, iBlockState);
   }

   @Override
   public int getRenderType() {
      return 3;
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
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return true;
   }

   public static EnumFacing getFacing(int integer) {
      return EnumFacing.getFront(integer & 7);
   }

   public static boolean isEnabled(int integer) {
      return (integer & 8) != 8;
   }

   @Override
   public boolean hasComparatorInputOverride() {
      return true;
   }

   @Override
   public int getComparatorInputOverride(World world, BlockPos blockPos) {
      return Container.calcRedstone(world.getTileEntity(blockPos));
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT_MIPPED;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(FACING, getFacing(integer)).withProperty(ENABLED, isEnabled(integer));
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(FACING).getIndex();
      if (!iBlockState.getValue(ENABLED)) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, ENABLED);
   }
}
