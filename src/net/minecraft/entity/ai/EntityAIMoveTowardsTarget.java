package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class EntityAIMoveTowardsTarget extends EntityAIBase {
   private final EntityCreature theEntity;
   private EntityLivingBase targetEntity;
   private double movePosX;
   private double movePosY;
   private double movePosZ;
   private final double speed;
   private final float maxTargetDistance;

   public EntityAIMoveTowardsTarget(EntityCreature entityCreature, double double2, float float3) {
      this.theEntity = entityCreature;
      this.speed = double2;
      this.maxTargetDistance = float3;
      this.setMutexBits(1);
   }

   @Override
   public boolean shouldExecute() {
      this.targetEntity = this.theEntity.getAttackTarget();
      if (this.targetEntity == null) {
         return false;
      }

      if (this.targetEntity.getDistanceSqToEntity(this.theEntity) > this.maxTargetDistance * this.maxTargetDistance) {
         return false;
      }

      Vec3 var4 = RandomPositionGenerator.findRandomTargetBlockTowards(
         this.theEntity, 16, 7, new Vec3(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ)
      );
      if (var4 == null) {
         return false;
      }

      this.movePosX = var4.xCoord;
      this.movePosY = var4.yCoord;
      this.movePosZ = var4.zCoord;
      return true;
   }

   @Override
   public boolean continueExecuting() {
      return !this.theEntity.getNavigator().noPath()
         && this.targetEntity.isEntityAlive()
         && this.targetEntity.getDistanceSqToEntity(this.theEntity) < this.maxTargetDistance * this.maxTargetDistance;
   }

   @Override
   public void resetTask() {
      this.targetEntity = null;
   }

   @Override
   public void startExecuting() {
      this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.speed);
   }
}
