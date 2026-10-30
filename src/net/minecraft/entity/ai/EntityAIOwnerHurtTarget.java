package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIOwnerHurtTarget extends EntityAITarget {
   EntityTameable theEntityTameable;
   EntityLivingBase theTarget;
   private int field_142050_e;

   public EntityAIOwnerHurtTarget(EntityTameable entityTameable) {
      super(entityTameable, false);
      this.theEntityTameable = entityTameable;
      this.setMutexBits(1);
   }

   @Override
   public boolean shouldExecute() {
      if (!this.theEntityTameable.isTamed()) {
         return false;
      }

      EntityLivingBase var4 = this.theEntityTameable.getOwner();
      if (var4 == null) {
         return false;
      }

      this.theTarget = var4.getLastAttacker();
      int var5 = var4.getLastAttackerTime();
      return var5 != this.field_142050_e && this.isSuitableTarget(this.theTarget, false) && this.theEntityTameable.shouldAttackEntity(this.theTarget, var4);
   }

   @Override
   public void startExecuting() {
      this.taskOwner.setAttackTarget(this.theTarget);
      EntityLivingBase var4 = this.theEntityTameable.getOwner();
      if (var4 != null) {
         this.field_142050_e = var4.getLastAttackerTime();
      }

      super.startExecuting();
   }
}
