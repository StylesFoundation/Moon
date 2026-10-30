package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class PathNavigateClimber extends PathNavigateGround {
   private BlockPos targetPosition;

   public PathNavigateClimber(EntityLiving entityLiving, World world) {
      super(entityLiving, world);
   }

   @Override
   public PathEntity getPathToPos(BlockPos blockPos) {
      this.targetPosition = blockPos;
      return super.getPathToPos(blockPos);
   }

   @Override
   public PathEntity getPathToEntityLiving(Entity entity) {
      this.targetPosition = new BlockPos(entity);
      return super.getPathToEntityLiving(entity);
   }

   @Override
   public boolean tryMoveToEntityLiving(Entity entity, double double2) {
      PathEntity var7 = this.getPathToEntityLiving(entity);
      if (var7 != null) {
         return this.setPath(var7, double2);
      }

      this.targetPosition = new BlockPos(entity);
      this.speed = double2;
      return true;
   }

   @Override
   public void onUpdateNavigation() {
      if (!this.noPath()) {
         super.onUpdateNavigation();
      } else if (this.targetPosition != null) {
         double var4 = this.theEntity.width * this.theEntity.width;
         if (!(this.theEntity.getDistanceSqToCenter(this.targetPosition) >= var4)
            || !(this.theEntity.posY <= this.targetPosition.getY())
               && !(
                  this.theEntity
                        .getDistanceSqToCenter(
                           new BlockPos(this.targetPosition.getX(), MathHelper.floor_double(this.theEntity.posY), this.targetPosition.getZ())
                        )
                     >= var4
               )) {
            this.targetPosition = null;
         } else {
            this.theEntity.getMoveHelper().setMoveTo(this.targetPosition.getX(), this.targetPosition.getY(), this.targetPosition.getZ(), this.speed);
         }
      }
   }
}
