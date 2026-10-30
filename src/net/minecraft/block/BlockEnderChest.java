package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockEnderChest extends BlockContainer {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

   protected BlockEnderChest() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
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
   public int getRenderType() {
      return 2;
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Item.getItemFromBlock(Blocks.obsidian);
   }

   @Override
   public int quantityDropped(Random random) {
      return 8;
   }

   @Override
   protected boolean canSilkHarvest() {
      return true;
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return this.getDefaultState().withProperty(FACING, entityLivingBase.getHorizontalFacing().getOpposite());
   }

   @Override
   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      world.setBlockState(blockPos, iBlockState.withProperty(FACING, entityLivingBase.getHorizontalFacing().getOpposite()), 2);
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      InventoryEnderChest var12 = entityPlayer.getInventoryEnderChest();
      TileEntity var13 = world.getTileEntity(blockPos);
      if (var12 == null || !(var13 instanceof TileEntityEnderChest)) {
         return true;
      }

      if (world.getBlockState(blockPos.up()).getBlock().isNormalCube()) {
         return true;
      }

      if (world.isRemote) {
         return true;
      }

      var12.setChestTileEntity((TileEntityEnderChest)var13);
      entityPlayer.displayGUIChest(var12);
      entityPlayer.triggerAchievement(StatList.field_181738_V);
      return true;
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntityEnderChest();
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      for (int var8 = 0; var8 < 3; var8++) {
         int var9 = random.nextInt(2) * 2 - 1;
         int var10 = random.nextInt(2) * 2 - 1;
         double var11 = blockPos.getX() + 0.5 + 0.25 * var9;
         double var13 = blockPos.getY() + random.nextFloat();
         double var15 = blockPos.getZ() + 0.5 + 0.25 * var10;
         double var17 = random.nextFloat() * var9;
         double var19 = (random.nextFloat() - 0.5) * 0.125;
         double var21 = random.nextFloat() * var10;
         world.spawnParticle(EnumParticleTypes.PORTAL, var11, var13, var15, var17, var19, var21);
      }
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      EnumFacing var5 = EnumFacing.getFront(integer);
      if (var5.getAxis() == EnumFacing.Axis.Y) {
         var5 = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(FACING, var5);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(FACING).getIndex();
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING);
   }
}
