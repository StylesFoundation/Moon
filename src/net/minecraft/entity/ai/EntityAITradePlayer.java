package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class EntityAITradePlayer extends EntityAIBase {
   private final EntityVillager villager;

   public EntityAITradePlayer(EntityVillager entityVillager) {
      this.villager = entityVillager;
      this.setMutexBits(5);
   }

   @Override
   public boolean shouldExecute() {
      if (!this.villager.isEntityAlive()) {
         return false;
      }

      if (this.villager.isInWater()) {
         return false;
      }

      if (!this.villager.onGround) {
         return false;
      }

      if (this.villager.velocityChanged) {
         return false;
      }

      EntityPlayer var4 = this.villager.getCustomer();
      return var4 != null && !(this.villager.getDistanceSqToEntity(var4) > 16.0) && var4.openContainer instanceof Container;
   }

   @Override
   public void startExecuting() {
      this.villager.getNavigator().clearPathEntity();
   }

   @Override
   public void resetTask() {
      this.villager.setCustomer(null);
   }
}
