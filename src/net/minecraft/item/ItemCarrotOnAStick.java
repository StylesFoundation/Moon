package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemCarrotOnAStick extends Item {
   public ItemCarrotOnAStick() {
      this.setCreativeTab(CreativeTabs.tabTransport);
      this.setMaxStackSize(1);
      this.setMaxDamage(25);
   }

   @Override
   public boolean isFull3D() {
      return true;
   }

   @Override
   public boolean shouldRotateAroundWhenRendering() {
      return true;
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      if (entityPlayer.isRiding()
         && entityPlayer.ridingEntity instanceof EntityPig var7
         && var7.getAIControlledByPlayer().isControlledByPlayer()
         && itemStack.getMaxDamage() - itemStack.getMetadata() >= 7) {
         var7.getAIControlledByPlayer().boostSpeed();
         itemStack.damageItem(7, entityPlayer);
         if (itemStack.stackSize == 0) {
            ItemStack var9 = new ItemStack(Items.fishing_rod);
            var9.setTagCompound(itemStack.getTagCompound());
            return var9;
         }
      }

      entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
      return itemStack;
   }
}
