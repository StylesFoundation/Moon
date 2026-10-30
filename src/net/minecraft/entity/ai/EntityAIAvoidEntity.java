package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.realms.Tezzelator;
import net.minecraft.util.Vec3;

public class EntityAIAvoidEntity<T extends Entity> extends EntityAIBase {
   private final Predicate<Entity> canBeSeenSelector = entity -> entity.isEntityAlive() && this.theEntity.getEntitySenses().canSee(entity);
   protected EntityCreature theEntity;
   private final double farSpeed;
   private final double nearSpeed;
   protected T closestLivingEntity;
   private final float avoidDistance;
   private PathEntity entityPathEntity;
   private final PathNavigate entityPathNavigate;
   private final Class<T> classToAvoid;
   private final Predicate<? super T> avoidTargetSelector;

   public EntityAIAvoidEntity(EntityCreature entityCreature, Class<T> class2, float float3, double double4, double double5) {
      this(entityCreature, class2, Predicates.alwaysTrue(), float3, double4, double5);
   }

   public EntityAIAvoidEntity(EntityCreature entityCreature, Class<T> class2, Predicate<? super T> predicate, float float4, double double5, double double6) {
      this.theEntity = entityCreature;
      this.classToAvoid = class2;
      this.avoidTargetSelector = predicate;
      this.avoidDistance = float4;
      this.farSpeed = double5;
      this.nearSpeed = double6;
      this.entityPathNavigate = entityCreature.getNavigator();
      this.setMutexBits(1);
   }

   @Override
   public boolean shouldExecute() {
      List var4 = this.theEntity
         .worldObj
         .getEntitiesWithinAABB(
            this.classToAvoid,
            this.theEntity.getEntityBoundingBox().expand(this.avoidDistance, 3.0, this.avoidDistance),
            Predicates.and(new Predicate[]{Tezzelator.d, this.canBeSeenSelector, this.avoidTargetSelector})
         );
      if (var4.isEmpty()) {
         return false;
      }

      this.closestLivingEntity = (T)var4.get(0);
      Vec3 var5 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(
         this.theEntity, 16, 7, new Vec3(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ)
      );
      if (var5 == null) {
         return false;
      }

      if (this.closestLivingEntity.getDistanceSq(var5.xCoord, var5.yCoord, var5.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity)) {
         return false;
      }

      this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(var5.xCoord, var5.yCoord, var5.zCoord);
      return this.entityPathEntity != null && this.entityPathEntity.isDestinationSame(var5);
   }

   @Override
   public boolean continueExecuting() {
      return !this.entityPathNavigate.noPath();
   }

   @Override
   public void startExecuting() {
      this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
   }

   @Override
   public void resetTask() {
      this.closestLivingEntity = null;
   }

   @Override
   public void updateTask() {
      if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0) {
         this.theEntity.getNavigator().setSpeed(this.nearSpeed);
      } else {
         this.theEntity.getNavigator().setSpeed(this.farSpeed);
      }
   }
}
