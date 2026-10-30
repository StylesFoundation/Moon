package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EntityDamageSource extends DamageSource {
   protected Entity damageSourceEntity;
   private boolean isThornsDamage = false;

   public EntityDamageSource(String string, Entity entity) {
      super(string);
      this.damageSourceEntity = entity;
   }

   public EntityDamageSource setIsThornsDamage() {
      this.isThornsDamage = true;
      return this;
   }

   public boolean getIsThornsDamage() {
      return this.isThornsDamage;
   }

   @Override
   public Entity getEntity() {
      return this.damageSourceEntity;
   }

   @Override
   public IChatComponent getDeathMessage(EntityLivingBase entityLivingBase) {
      ItemStack var5 = this.damageSourceEntity instanceof EntityLivingBase ? ((EntityLivingBase)this.damageSourceEntity).getHeldItem() : null;
      String var6 = "death.attack." + this.damageType;
      String var7 = var6 + ".item";
      return var5 != null && var5.hasDisplayName() && StatCollector.canTranslate(var7)
         ? new ChatComponentTranslation(var7, entityLivingBase.getDisplayName(), this.damageSourceEntity.getDisplayName(), var5.getChatComponent())
         : new ChatComponentTranslation(var6, entityLivingBase.getDisplayName(), this.damageSourceEntity.getDisplayName());
   }

   @Override
   public boolean isDifficultyScaled() {
      return this.damageSourceEntity != null && this.damageSourceEntity instanceof EntityLivingBase && !(this.damageSourceEntity instanceof EntityPlayer);
   }
}
