package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class ItemNameTag extends Item {
   public ItemNameTag() {
      this.setCreativeTab(CreativeTabs.tabTools);
   }

   @Override
   public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer entityPlayer, EntityLivingBase entityLivingBase) {
      if (!itemStack.hasDisplayName()) {
         return false;
      } else if (entityLivingBase instanceof EntityLiving var7) {
         var7.setCustomNameTag(itemStack.getDisplayName());
         var7.enablePersistence();
         itemStack.stackSize--;
         return true;
      } else {
         return super.itemInteractionForEntity(itemStack, entityPlayer, entityLivingBase);
      }
   }
}
