package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIAttackOnCollide extends EntityAIBase {
   World worldObj;
   protected EntityCreature attacker;
   int attackTick;
   double speedTowardsTarget;
   boolean longMemory;
   PathEntity entityPathEntity;
   Class<? extends Entity> classTarget;
   private int delayCounter;
   private double targetX;
   private double targetY;
   private double targetZ;

   public EntityAIAttackOnCollide(EntityCreature entityCreature, Class<? extends Entity> class2, double double3, boolean boolean4) {
      this(entityCreature, double3, boolean4);
      this.classTarget = class2;
   }

   public EntityAIAttackOnCollide(EntityCreature entityCreature, double double2, boolean boolean3) {
      this.attacker = entityCreature;
      this.worldObj = entityCreature.worldObj;
      this.speedTowardsTarget = double2;
      this.longMemory = boolean3;
      this.setMutexBits(3);
   }

   @Override
   public boolean shouldExecute() {
      EntityLivingBase var4 = this.attacker.getAttackTarget();
      if (var4 == null) {
         return false;
      }

      if (!var4.isEntityAlive()) {
         return false;
      }

      if (this.classTarget != null && !this.classTarget.isAssignableFrom(var4.getClass())) {
         return false;
      }

      this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(var4);
      return this.entityPathEntity != null;
   }

   @Override
   public boolean continueExecuting() {
      EntityLivingBase var4 = this.attacker.getAttackTarget();
      return var4 != null
         && var4.isEntityAlive()
         && (!this.longMemory ? !this.attacker.getNavigator().noPath() : this.attacker.isWithinHomeDistanceFromPosition(new BlockPos(var4)));
   }

   @Override
   public void startExecuting() {
      this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
      this.delayCounter = 0;
   }

   @Override
   public void resetTask() {
      this.attacker.getNavigator().clearPathEntity();
   }

   @Override
   public void updateTask() {
      EntityLivingBase var4 = this.attacker.getAttackTarget();
      this.attacker.getLookHelper().setLookPositionWithEntity(var4, 30.0F, 30.0F);
      double var5 = this.attacker.getDistanceSq(var4.posX, var4.getEntityBoundingBox().minY, var4.posZ);
      double var7 = this.func_179512_a(var4);
      this.delayCounter--;
      if ((this.longMemory || this.attacker.getEntitySenses().canSee(var4))
         && this.delayCounter <= 0
         && (
            this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0
               || var4.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0
               || this.attacker.getRNG().nextFloat() < 0.05F
         )) {
         this.targetX = var4.posX;
         this.targetY = var4.getEntityBoundingBox().minY;
         this.targetZ = var4.posZ;
         this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
         if (var5 > 1024.0) {
            this.delayCounter += 10;
         } else if (var5 > 256.0) {
            this.delayCounter += 5;
         }

         if (!this.attacker.getNavigator().tryMoveToEntityLiving(var4, this.speedTowardsTarget)) {
            this.delayCounter += 15;
         }
      }

      this.attackTick = Math.max(this.attackTick - 1, 0);
      if (var5 <= var7 && this.attackTick == 0) {
         this.attackTick = 20;
         if (this.attacker.getHeldItem() != null) {
            this.attacker.swingItem();
         }

         this.attacker.attackEntityAsMob(var4);
      }
   }

   protected double func_179512_a(EntityLivingBase entityLivingBase) {
      return this.attacker.width * 2.0F * this.attacker.width * 2.0F + entityLivingBase.width;
   }
}
