package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.util.MathHelper;

public class EntityAIArrowAttack extends EntityAIBase {
   private final EntityLiving entityHost;
   private final IRangedAttackMob rangedAttackEntityHost;
   private EntityLivingBase attackTarget;
   private int rangedAttackTime = -1;
   private final double entityMoveSpeed;
   private int field_75318_f;
   private final int field_96561_g;
   private final int maxRangedAttackTime;
   private final float field_96562_i;
   private final float maxAttackDistance;

   public EntityAIArrowAttack(IRangedAttackMob iRangedAttackMob, double double2, int integer, float float4) {
      this(iRangedAttackMob, double2, integer, integer, float4);
   }

   public EntityAIArrowAttack(IRangedAttackMob iRangedAttackMob, double double2, int integer3, int integer4, float float5) {
      if (!(iRangedAttackMob instanceof EntityLivingBase)) {
         throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
      }

      this.rangedAttackEntityHost = iRangedAttackMob;
      this.entityHost = (EntityLiving)iRangedAttackMob;
      this.entityMoveSpeed = double2;
      this.field_96561_g = integer3;
      this.maxRangedAttackTime = integer4;
      this.field_96562_i = float5;
      this.maxAttackDistance = float5 * float5;
      this.setMutexBits(3);
   }

   @Override
   public boolean shouldExecute() {
      EntityLivingBase var4 = this.entityHost.getAttackTarget();
      if (var4 == null) {
         return false;
      }

      this.attackTarget = var4;
      return true;
   }

   @Override
   public boolean continueExecuting() {
      return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
   }

   @Override
   public void resetTask() {
      this.attackTarget = null;
      this.field_75318_f = 0;
      this.rangedAttackTime = -1;
   }

   @Override
   public void updateTask() {
      double var4 = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.getEntityBoundingBox().minY, this.attackTarget.posZ);
      boolean var6 = this.entityHost.getEntitySenses().canSee(this.attackTarget);
      if (var6) {
         this.field_75318_f++;
      } else {
         this.field_75318_f = 0;
      }

      if (var4 <= this.maxAttackDistance && this.field_75318_f >= 20) {
         this.entityHost.getNavigator().clearPathEntity();
      } else {
         this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
      }

      this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
      if (--this.rangedAttackTime == 0) {
         if (var4 > this.maxAttackDistance || !var6) {
            return;
         }

         float var7 = MathHelper.sqrt_double(var4) / this.field_96562_i;
         float var8 = MathHelper.clamp_float(var7, 0.1F, 1.0F);
         this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, var8);
         this.rangedAttackTime = MathHelper.floor_float(var7 * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
      } else if (this.rangedAttackTime < 0) {
         float var9 = MathHelper.sqrt_double(var4) / this.field_96562_i;
         this.rangedAttackTime = MathHelper.floor_float(var9 * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
      }
   }
}
