package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSign extends BlockContainer {
   protected BlockSign() {
      super(Material.wood);
      this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      return null;
   }

   @Override
   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos blockPos) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      return super.getSelectedBoundingBox(world, blockPos);
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return true;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean canSpawnInBlock() {
      return true;
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntitySign();
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Items.sign;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Items.sign;
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (world.isRemote) {
         return true;
      }

      TileEntity var12 = world.getTileEntity(blockPos);
      return var12 instanceof TileEntitySign && ((TileEntitySign)var12).executeCommand(entityPlayer);
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return !this.hasInvalidNeighbor(world, blockPos) && super.canPlaceBlockAt(world, blockPos);
   }
}
