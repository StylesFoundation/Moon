package net.minecraft.item;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemFireball extends Item {
   public ItemFireball() {
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (world.isRemote) {
         return true;
      }

      blockPos = blockPos.offset(enumFacing);
      if (!entityPlayer.canPlayerEdit(blockPos, enumFacing, itemStack)) {
         return false;
      }

      if (world.getBlockState(blockPos).getBlock().getMaterial() == Material.air) {
         world.playSoundEffect(
            blockPos.getX() + 0.5,
            blockPos.getY() + 0.5,
            blockPos.getZ() + 0.5,
            "item.fireCharge.use",
            1.0F,
            (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2F + 1.0F
         );
         world.setBlockState(blockPos, Blocks.fire.getDefaultState());
      }

      if (!entityPlayer.capabilities.isCreativeMode) {
         itemStack.stackSize--;
      }

      return true;
   }
}
