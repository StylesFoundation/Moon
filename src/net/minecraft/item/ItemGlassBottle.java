package net.minecraft.item;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemGlassBottle extends Item {
   public ItemGlassBottle() {
      this.setCreativeTab(CreativeTabs.tabBrewing);
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      MovingObjectPosition var7 = this.getMovingObjectPositionFromPlayer(world, entityPlayer, true);
      if (var7 != null && var7.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
         BlockPos var8 = var7.getBlockPos();
         if (!world.isBlockModifiable(entityPlayer, var8)) {
            return itemStack;
         }

         if (!entityPlayer.canPlayerEdit(var8.offset(var7.sideHit), var7.sideHit, itemStack)) {
            return itemStack;
         }

         if (world.getBlockState(var8).getBlock().getMaterial() == Material.water) {
            itemStack.stackSize--;
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            if (itemStack.stackSize <= 0) {
               return new ItemStack(Items.potionitem);
            }

            if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.potionitem))) {
               entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.potionitem, 1, 0), false);
            }
         }
      }

      return itemStack;
   }
}
