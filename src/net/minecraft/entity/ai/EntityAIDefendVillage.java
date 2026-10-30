package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.village.Village;

public class EntityAIDefendVillage extends EntityAITarget {
   EntityIronGolem irongolem;
   EntityLivingBase villageAgressorTarget;

   public EntityAIDefendVillage(EntityIronGolem entityIronGolem) {
      super(entityIronGolem, false, true);
      this.irongolem = entityIronGolem;
      this.setMutexBits(1);
   }

   @Override
   public boolean shouldExecute() {
      Village var4 = this.irongolem.getVillage();
      if (var4 == null) {
         return false;
      }

      this.villageAgressorTarget = var4.findNearestVillageAggressor(this.irongolem);
      if (this.villageAgressorTarget instanceof EntityCreeper) {
         return false;
      }

      if (!this.isSuitableTarget(this.villageAgressorTarget, false)) {
         if (this.taskOwner.getRNG().nextInt(20) == 0) {
            this.villageAgressorTarget = var4.getNearestTargetPlayer(this.irongolem);
            return this.isSuitableTarget(this.villageAgressorTarget, false);
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   @Override
   public void startExecuting() {
      this.irongolem.setAttackTarget(this.villageAgressorTarget);
      super.startExecuting();
   }
}
