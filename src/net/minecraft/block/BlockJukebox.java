package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockJukebox extends BlockContainer {
   public static final PropertyBool HAS_RECORD = PropertyBool.create("has_record");

   protected BlockJukebox() {
      super(Material.wood, MapColor.dirtColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(HAS_RECORD, Boolean.FALSE));
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (iBlockState.getValue(HAS_RECORD)) {
         this.dropRecord(world, blockPos, iBlockState);
         iBlockState = iBlockState.withProperty(HAS_RECORD, Boolean.FALSE);
         world.setBlockState(blockPos, iBlockState, 2);
         return true;
      } else {
         return false;
      }
   }

   public void insertRecord(World world, BlockPos blockPos, IBlockState iBlockState, ItemStack itemStack) {
      if (!world.isRemote) {
         TileEntity var8 = world.getTileEntity(blockPos);
         if (var8 instanceof BlockJukebox.TileEntityJukebox) {
            ((BlockJukebox.TileEntityJukebox)var8).setRecord(new ItemStack(itemStack.getItem(), 1, itemStack.getMetadata()));
            world.setBlockState(blockPos, iBlockState.withProperty(HAS_RECORD, Boolean.TRUE), 2);
         }
      }
   }

   private void dropRecord(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!world.isRemote && world.getTileEntity(blockPos) instanceof BlockJukebox.TileEntityJukebox var8) {
         ItemStack var9 = var8.getRecord();
         if (var9 != null) {
            world.playAuxSFX(1005, blockPos, 0);
            world.playRecord(blockPos, null);
            var8.setRecord(null);
            double var11 = world.rand.nextFloat() * 0.7F + 0.15F;
            double var13 = world.rand.nextFloat() * 0.7F + 0.060000002F + 0.6;
            double var15 = world.rand.nextFloat() * 0.7F + 0.15F;
            ItemStack var17 = var9.copy();
            EntityItem var18 = new EntityItem(world, blockPos.getX() + var11, blockPos.getY() + var13, blockPos.getZ() + var15, var17);
            var18.setDefaultPickupDelay();
            world.spawnEntityInWorld(var18);
         }
      }
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.dropRecord(world, blockPos, iBlockState);
      super.breakBlock(world, blockPos, iBlockState);
   }

   @Override
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
      if (!world.isRemote) {
         super.dropBlockAsItemWithChance(world, blockPos, iBlockState, float4, 0);
      }
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new BlockJukebox.TileEntityJukebox();
   }

   @Override
   public boolean hasComparatorInputOverride() {
      return true;
   }

   @Override
   public int getComparatorInputOverride(World world, BlockPos blockPos) {
      TileEntity var6 = world.getTileEntity(blockPos);
      if (var6 instanceof BlockJukebox.TileEntityJukebox) {
         ItemStack var7 = ((BlockJukebox.TileEntityJukebox)var6).getRecord();
         if (var7 != null) {
            return Item.getIdFromItem(var7.getItem()) + 1 - Item.getIdFromItem(Items.record_13);
         }
      }

      return 0;
   }

   @Override
   public int getRenderType() {
      return 3;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(HAS_RECORD, integer > 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(HAS_RECORD) ? 1 : 0;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, HAS_RECORD);
   }

   public static class TileEntityJukebox extends TileEntity {
      private ItemStack record;

      @Override
      public void readFromNBT(NBTTagCompound nBTTagCompound) {
         super.readFromNBT(nBTTagCompound);
         if (nBTTagCompound.hasKey("RecordItem", 10)) {
            this.setRecord(ItemStack.loadItemStackFromNBT(nBTTagCompound.getCompoundTag("RecordItem")));
         } else if (nBTTagCompound.getInteger("Record") > 0) {
            this.setRecord(new ItemStack(Item.getItemById(nBTTagCompound.getInteger("Record")), 1, 0));
         }
      }

      @Override
      public void writeToNBT(NBTTagCompound nBTTagCompound) {
         super.writeToNBT(nBTTagCompound);
         if (this.getRecord() != null) {
            nBTTagCompound.setTag("RecordItem", this.getRecord().writeToNBT(new NBTTagCompound()));
         }
      }

      public ItemStack getRecord() {
         return this.record;
      }

      public void setRecord(ItemStack itemStack) {
         this.record = itemStack;
         this.markDirty();
      }
   }
}
