package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIOwnerHurtByTarget extends EntityAITarget {
   EntityTameable theDefendingTameable;
   EntityLivingBase theOwnerAttacker;
   private int field_142051_e;

   public EntityAIOwnerHurtByTarget(EntityTameable entityTameable) {
      super(entityTameable, false);
      this.theDefendingTameable = entityTameable;
      this.setMutexBits(1);
   }

   @Override
   public boolean shouldExecute() {
      if (!this.theDefendingTameable.isTamed()) {
         return false;
      }

      EntityLivingBase var4 = this.theDefendingTameable.getOwner();
      if (var4 == null) {
         return false;
      }

      this.theOwnerAttacker = var4.getAITarget();
      int var5 = var4.getRevengeTimer();
      return var5 != this.field_142051_e
         && this.isSuitableTarget(this.theOwnerAttacker, false)
         && this.theDefendingTameable.shouldAttackEntity(this.theOwnerAttacker, var4);
   }

   @Override
   public void startExecuting() {
      this.taskOwner.setAttackTarget(this.theOwnerAttacker);
      EntityLivingBase var4 = this.theDefendingTameable.getOwner();
      if (var4 != null) {
         this.field_142051_e = var4.getRevengeTimer();
      }

      super.startExecuting();
   }
}
