package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemBucketMilk extends Item {
   public ItemBucketMilk() {
      this.setMaxStackSize(1);
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   @Override
   public ItemStack onItemUseFinish(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      if (!entityPlayer.capabilities.isCreativeMode) {
         itemStack.stackSize--;
      }

      if (!world.isRemote) {
         entityPlayer.clearActivePotions();
      }

      entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
      return itemStack.stackSize <= 0 ? new ItemStack(Items.bucket) : itemStack;
   }

   @Override
   public int getMaxItemUseDuration(ItemStack itemStack) {
      return 32;
   }

   @Override
   public EnumAction getItemUseAction(ItemStack itemStack) {
      return EnumAction.DRINK;
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
      return itemStack;
   }
}
