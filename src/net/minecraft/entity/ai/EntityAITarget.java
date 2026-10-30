package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.StringUtils;

public abstract class EntityAITarget extends EntityAIBase {
   protected final EntityCreature taskOwner;
   protected boolean shouldCheckSight;
   private final boolean nearbyOnly;
   private int targetSearchStatus;
   private int targetSearchDelay;
   private int targetUnseenTicks;

   public EntityAITarget(EntityCreature entityCreature, boolean boolean2) {
      this(entityCreature, boolean2, false);
   }

   public EntityAITarget(EntityCreature entityCreature, boolean boolean2, boolean boolean3) {
      this.taskOwner = entityCreature;
      this.shouldCheckSight = boolean2;
      this.nearbyOnly = boolean3;
   }

   @Override
   public boolean continueExecuting() {
      EntityLivingBase var4 = this.taskOwner.getAttackTarget();
      if (var4 == null) {
         return false;
      }

      if (!var4.isEntityAlive()) {
         return false;
      }

      Team var5 = this.taskOwner.getTeam();
      Team var6 = var4.getTeam();
      if (var5 != null && var6 == var5) {
         return false;
      }

      double var7 = this.getTargetDistance();
      if (this.taskOwner.getDistanceSqToEntity(var4) > var7 * var7) {
         return false;
      }

      if (this.shouldCheckSight) {
         if (this.taskOwner.getEntitySenses().canSee(var4)) {
            this.targetUnseenTicks = 0;
         } else if (++this.targetUnseenTicks > 60) {
            return false;
         }
      }

      return !(var4 instanceof EntityPlayer) || !((EntityPlayer)var4).capabilities.disableDamage;
   }

   protected double getTargetDistance() {
      IAttributeInstance var4 = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.followRange);
      return var4 == null ? 16.0 : var4.getAttributeValue();
   }

   @Override
   public void startExecuting() {
      this.targetSearchStatus = 0;
      this.targetSearchDelay = 0;
      this.targetUnseenTicks = 0;
   }

   @Override
   public void resetTask() {
      this.taskOwner.setAttackTarget(null);
   }

   public static boolean isSuitableTarget(EntityLiving entityLiving, EntityLivingBase entityLivingBase, boolean boolean3, boolean boolean4) {
      if (entityLivingBase == null) {
         return false;
      }

      if (entityLivingBase == entityLiving) {
         return false;
      }

      if (!entityLivingBase.isEntityAlive()) {
         return false;
      }

      if (!entityLiving.canAttackClass((Class<? extends EntityLivingBase>)entityLivingBase.getClass())) {
         return false;
      }

      Team var7 = entityLiving.getTeam();
      Team var8 = entityLivingBase.getTeam();
      if (var7 != null && var8 == var7) {
         return false;
      }

      if (entityLiving instanceof IEntityOwnable && StringUtils.isNotEmpty(((IEntityOwnable)entityLiving).getOwnerId())) {
         if (entityLivingBase instanceof IEntityOwnable && ((IEntityOwnable)entityLiving).getOwnerId().equals(((IEntityOwnable)entityLivingBase).getOwnerId())) {
            return false;
         }

         if (entityLivingBase == ((IEntityOwnable)entityLiving).getOwner()) {
            return false;
         }
      } else if (entityLivingBase instanceof EntityPlayer && !boolean3 && ((EntityPlayer)entityLivingBase).capabilities.disableDamage) {
         return false;
      }

      return !boolean4 || entityLiving.getEntitySenses().canSee(entityLivingBase);
   }

   protected boolean isSuitableTarget(EntityLivingBase entityLivingBase, boolean boolean2) {
      if (!isSuitableTarget(this.taskOwner, entityLivingBase, boolean2, this.shouldCheckSight)) {
         return false;
      }

      if (!this.taskOwner.isWithinHomeDistanceFromPosition(new BlockPos(entityLivingBase))) {
         return false;
      }

      if (this.nearbyOnly) {
         if (--this.targetSearchDelay <= 0) {
            this.targetSearchStatus = 0;
         }

         if (this.targetSearchStatus == 0) {
            this.targetSearchStatus = this.canEasilyReach(entityLivingBase) ? 1 : 2;
         }

         return this.targetSearchStatus != 2;
      } else {
         return true;
      }
   }

   private boolean canEasilyReach(EntityLivingBase entityLivingBase) {
      this.targetSearchDelay = 10 + this.taskOwner.getRNG().nextInt(5);
      PathEntity var5 = this.taskOwner.getNavigator().getPathToEntityLiving(entityLivingBase);
      if (var5 == null) {
         return false;
      }

      PathPoint var6 = var5.getFinalPathPoint();
      if (var6 == null) {
         return false;
      }

      int var7 = var6.xCoord - MathHelper.floor_double(entityLivingBase.posX);
      int var8 = var6.zCoord - MathHelper.floor_double(entityLivingBase.posZ);
      return var7 * var7 + var8 * var8 <= 2.25;
   }
}
