package net.minecraft.item;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemFlintAndSteel extends Item {
   public ItemFlintAndSteel() {
      this.maxStackSize = 1;
      this.setMaxDamage(64);
      this.setCreativeTab(CreativeTabs.tabTools);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      blockPos = blockPos.offset(enumFacing);
      if (!entityPlayer.canPlayerEdit(blockPos, enumFacing, itemStack)) {
         return false;
      }

      if (world.getBlockState(blockPos).getBlock().getMaterial() == Material.air) {
         world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
         world.setBlockState(blockPos, Blocks.fire.getDefaultState());
      }

      itemStack.damageItem(1, entityPlayer);
      return true;
   }
}
