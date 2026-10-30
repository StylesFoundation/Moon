package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCarpet extends Block {
   public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);

   protected BlockCarpet() {
      super(Material.carpet);
      this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setBlockBoundsFromMeta(0);
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      return iBlockState.getValue(COLOR).getMapColor();
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
   public void setBlockBoundsForItemRender() {
      this.setBlockBoundsFromMeta(0);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      this.setBlockBoundsFromMeta(0);
   }

   protected void setBlockBoundsFromMeta(int integer) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return super.canPlaceBlockAt(world, blockPos) && this.canBlockStay(world, blockPos);
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      this.checkForDrop(world, blockPos, iBlockState);
   }

   private boolean checkForDrop(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!this.canBlockStay(world, blockPos)) {
         this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         world.setBlockToAir(blockPos);
         return false;
      } else {
         return true;
      }
   }

   private boolean canBlockStay(World world, BlockPos blockPos) {
      return !world.isAirBlock(blockPos.down());
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return enumFacing == EnumFacing.UP || super.shouldSideBeRendered(iBlockAccess, blockPos, enumFacing);
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(COLOR).getMetadata();
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      for (int var7 = 0; var7 < 16; var7++) {
         list.add(new ItemStack(item, 1, var7));
      }
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(integer));
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(COLOR).getMetadata();
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, COLOR);
   }
}
