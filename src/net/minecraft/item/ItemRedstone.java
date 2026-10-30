package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemRedstone extends Item {
   public ItemRedstone() {
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      boolean var12 = world.getBlockState(blockPos).getBlock().isReplaceable(world, blockPos);
      BlockPos var13 = var12 ? blockPos : blockPos.offset(enumFacing);
      if (!entityPlayer.canPlayerEdit(var13, enumFacing, itemStack)) {
         return false;
      } else {
         Block var14 = world.getBlockState(var13).getBlock();
         if (!world.canBlockBePlaced(var14, var13, false, enumFacing, null, itemStack)) {
            return false;
         } else if (Blocks.redstone_wire.canPlaceBlockAt(world, var13)) {
            itemStack.stackSize--;
            world.setBlockState(var13, Blocks.redstone_wire.getDefaultState());
            return true;
         } else {
            return false;
         }
      }
   }
}
