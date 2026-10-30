package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSnow extends Block {
   public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 1, 8);

   protected BlockSnow() {
      super(Material.snow);
      this.setDefaultState(this.blockState.getBaseState().withProperty(LAYERS, 1));
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setBlockBoundsForItemRender();
   }

   @Override
   public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return iBlockAccess.getBlockState(blockPos).getValue(LAYERS) < 5;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      int var4 = iBlockState.getValue(LAYERS) - 1;
      return new AxisAlignedBB(
         blockPos.getX() + this.minX,
         blockPos.getY() + this.minY,
         blockPos.getZ() + this.minZ,
         blockPos.getX() + this.maxX,
         blockPos.getY() + var4 * 0.125F,
         blockPos.getZ() + this.maxZ
      );
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
      this.getBoundsForLayers(0);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      IBlockState var3 = iBlockAccess.getBlockState(blockPos);
      this.getBoundsForLayers(var3.getValue(LAYERS));
   }

   protected void getBoundsForLayers(int integer) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, integer / 8.0F, 1.0F);
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      IBlockState var6 = world.getBlockState(blockPos.down());
      Block var7 = var6.getBlock();
      return var7 != Blocks.ice
         && var7 != Blocks.packed_ice
         && (var7.getMaterial() == Material.leaves || var7 == this && var6.getValue(LAYERS) >= 7 || var7.isOpaqueCube() && var7.blockMaterial.blocksMovement());
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      this.checkAndDropBlock(world, blockPos, iBlockState);
   }

   private boolean checkAndDropBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!this.canPlaceBlockAt(world, blockPos)) {
         this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         world.setBlockToAir(blockPos);
         return false;
      } else {
         return true;
      }
   }

   @Override
   public void harvestBlock(World world, EntityPlayer entityPlayer, BlockPos blockPos, IBlockState iBlockState, TileEntity tileEntity) {
      spawnAsEntity(world, blockPos, new ItemStack(Items.snowball, iBlockState.getValue(LAYERS) + 1, 0));
      world.setBlockToAir(blockPos);
      entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Items.snowball;
   }

   @Override
   public int quantityDropped(Random random) {
      return 0;
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (world.getLightFor(EnumSkyBlock.BLOCK, blockPos) > 11) {
         this.dropBlockAsItem(world, blockPos, world.getBlockState(blockPos), 0);
         world.setBlockToAir(blockPos);
      }
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return enumFacing == EnumFacing.UP || super.shouldSideBeRendered(iBlockAccess, blockPos, enumFacing);
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(LAYERS, (integer & 7) + 1);
   }

   @Override
   public boolean isReplaceable(World world, BlockPos blockPos) {
      return world.getBlockState(blockPos).getValue(LAYERS) == 1;
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(LAYERS) - 1;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, LAYERS);
   }
}
