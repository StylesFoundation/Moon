package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class EntityDamageSourceIndirect extends EntityDamageSource {
   private final Entity indirectEntity;

   public EntityDamageSourceIndirect(String string, Entity entity2, Entity entity3) {
      super(string, entity2);
      this.indirectEntity = entity3;
   }

   @Override
   public Entity getSourceOfDamage() {
      return this.damageSourceEntity;
   }

   @Override
   public Entity getEntity() {
      return this.indirectEntity;
   }

   @Override
   public IChatComponent getDeathMessage(EntityLivingBase entityLivingBase) {
      IChatComponent var5 = this.indirectEntity == null ? this.damageSourceEntity.getDisplayName() : this.indirectEntity.getDisplayName();
      ItemStack var6 = this.indirectEntity instanceof EntityLivingBase ? ((EntityLivingBase)this.indirectEntity).getHeldItem() : null;
      String var7 = "death.attack." + this.damageType;
      String var8 = var7 + ".item";
      return var6 != null && var6.hasDisplayName() && StatCollector.canTranslate(var8)
         ? new ChatComponentTranslation(var8, entityLivingBase.getDisplayName(), var5, var6.getChatComponent())
         : new ChatComponentTranslation(var7, entityLivingBase.getDisplayName(), var5);
   }
}
