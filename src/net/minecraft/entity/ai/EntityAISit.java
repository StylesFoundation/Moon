package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAISit extends EntityAIBase {
   private final EntityTameable theEntity;
   private boolean isSitting;

   public EntityAISit(EntityTameable entityTameable) {
      this.theEntity = entityTameable;
      this.setMutexBits(5);
   }

   @Override
   public boolean shouldExecute() {
      if (!this.theEntity.isTamed()) {
         return false;
      }

      if (this.theEntity.isInWater()) {
         return false;
      }

      if (!this.theEntity.onGround) {
         return false;
      }

      EntityLivingBase var4 = this.theEntity.getOwner();
      return var4 == null || (!(this.theEntity.getDistanceSqToEntity(var4) < 144.0) || var4.getAITarget() == null) && this.isSitting;
   }

   @Override
   public void startExecuting() {
      this.theEntity.getNavigator().clearPathEntity();
      this.theEntity.setSitting(true);
   }

   @Override
   public void resetTask() {
      this.theEntity.setSitting(false);
   }

   public void setSitting(boolean boolean1) {
      this.isSitting = boolean1;
   }
}
