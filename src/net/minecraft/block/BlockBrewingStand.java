package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BlockBrewingStand extends BlockContainer {
   public static final PropertyBool[] HAS_BOTTLE = new PropertyBool[]{
      PropertyBool.create("has_bottle_0"), PropertyBool.create("has_bottle_1"), PropertyBool.create("has_bottle_2")
   };

   public BlockBrewingStand() {
      super(Material.iron);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(HAS_BOTTLE[0], Boolean.FALSE)
            .withProperty(HAS_BOTTLE[1], Boolean.FALSE)
            .withProperty(HAS_BOTTLE[2], Boolean.FALSE)
      );
   }

   @Override
   public String getLocalizedName() {
      return StatCollector.translateToLocal("item.brewingStand.name");
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public int getRenderType() {
      return 3;
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntityBrewingStand();
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public void addCollisionBoxesToList(
      World world, BlockPos blockPos, IBlockState iBlockState, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, Entity entity
   ) {
      this.setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      this.setBlockBoundsForItemRender();
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
   }

   @Override
   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!world.isRemote) {
         TileEntity var12 = world.getTileEntity(blockPos);
         if (var12 instanceof TileEntityBrewingStand) {
            entityPlayer.displayGUIChest((TileEntityBrewingStand)var12);
            entityPlayer.triggerAchievement(StatList.field_181729_M);
         }
      }

      return true;
   }

   @Override
   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      if (itemStack.hasDisplayName()) {
         TileEntity var9 = world.getTileEntity(blockPos);
         if (var9 instanceof TileEntityBrewingStand) {
            ((TileEntityBrewingStand)var9).setName(itemStack.getDisplayName());
         }
      }
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      double var5 = blockPos.getX() + 0.4F + random.nextFloat() * 0.2F;
      double var7 = blockPos.getY() + 0.7F + random.nextFloat() * 0.3F;
      double var9 = blockPos.getZ() + 0.4F + random.nextFloat() * 0.2F;
      world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var5, var7, var9, 0.0, 0.0, 0.0);
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      TileEntity var7 = world.getTileEntity(blockPos);
      if (var7 instanceof TileEntityBrewingStand) {
         InventoryHelper.dropInventoryItems(world, blockPos, (TileEntityBrewingStand)var7);
      }

      super.breakBlock(world, blockPos, iBlockState);
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Items.brewing_stand;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Items.brewing_stand;
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
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      IBlockState var5 = this.getDefaultState();

      for (int var6 = 0; var6 < 3; var6++) {
         var5 = var5.withProperty(HAS_BOTTLE[var6], (integer & 1 << var6) > 0);
      }

      return var5;
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0;

      for (int var6 = 0; var6 < 3; var6++) {
         if (iBlockState.getValue(HAS_BOTTLE[var6])) {
            var5 |= 1 << var6;
         }
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, HAS_BOTTLE[0], HAS_BOTTLE[1], HAS_BOTTLE[2]);
   }
}
