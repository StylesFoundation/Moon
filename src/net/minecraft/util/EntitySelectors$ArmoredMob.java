package net.minecraft.util;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EntitySelectors$ArmoredMob implements Predicate<Entity> {
   private final ItemStack armor;

   public EntitySelectors$ArmoredMob(ItemStack itemStack) {
      this.armor = itemStack;
   }

   public boolean apply(Entity entity) {
      if (!entity.isEntityAlive()) {
         return false;
      } else {
         return !(entity instanceof EntityLivingBase var5)
            ? false
            : var5.getEquipmentInSlot(EntityLiving.getArmorPosition(this.armor)) == null
               && (var5 instanceof EntityLiving ? ((EntityLiving)var5).canPickUpLoot() : var5 instanceof EntityArmorStand || var5 instanceof EntityPlayer);
      }
   }
}
