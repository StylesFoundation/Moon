package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;

public class ItemSaddle extends Item {
   public ItemSaddle() {
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.tabTransport);
   }

   @Override
   public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer entityPlayer, EntityLivingBase entityLivingBase) {
      if (entityLivingBase instanceof EntityPig var7) {
         if (!var7.getSaddled() && !var7.isChild()) {
            var7.setSaddled(true);
            var7.worldObj.playSoundAtEntity(var7, "mob.horse.leather", 0.5F, 1.0F);
            itemStack.stackSize--;
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean hitEntity(ItemStack itemStack, EntityLivingBase entityLivingBase2, EntityLivingBase entityLivingBase3) {
      this.itemInteractionForEntity(itemStack, null, entityLivingBase2);
      return true;
   }
}
