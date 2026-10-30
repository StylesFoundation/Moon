package net.minecraft.item;

import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSign extends Item {
   public ItemSign() {
      this.maxStackSize = 16;
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (enumFacing == EnumFacing.DOWN) {
         return false;
      }

      if (!world.getBlockState(blockPos).getBlock().getMaterial().isSolid()) {
         return false;
      }

      blockPos = blockPos.offset(enumFacing);
      if (!entityPlayer.canPlayerEdit(blockPos, enumFacing, itemStack)) {
         return false;
      }

      if (!Blocks.standing_sign.canPlaceBlockAt(world, blockPos)) {
         return false;
      }

      if (world.isRemote) {
         return true;
      }

      if (enumFacing == EnumFacing.UP) {
         int var12 = MathHelper.floor_double((entityPlayer.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5) & 15;
         world.setBlockState(blockPos, Blocks.standing_sign.getDefaultState().withProperty(BlockStandingSign.ROTATION, var12), 3);
      } else {
         world.setBlockState(blockPos, Blocks.wall_sign.getDefaultState().withProperty(BlockWallSign.FACING, enumFacing), 3);
      }

      itemStack.stackSize--;
      TileEntity var14 = world.getTileEntity(blockPos);
      if (var14 instanceof TileEntitySign && !ItemBlock.setTileEntityNBT(world, entityPlayer, blockPos, itemStack)) {
         entityPlayer.openEditSign((TileEntitySign)var14);
      }

      return true;
   }
}
