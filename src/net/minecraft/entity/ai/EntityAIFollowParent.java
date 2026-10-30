package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.passive.EntityAnimal;

public class EntityAIFollowParent extends EntityAIBase {
   EntityAnimal childAnimal;
   EntityAnimal parentAnimal;
   double moveSpeed;
   private int delayCounter;

   public EntityAIFollowParent(EntityAnimal entityAnimal, double double2) {
      this.childAnimal = entityAnimal;
      this.moveSpeed = double2;
   }

   @Override
   public boolean shouldExecute() {
      if (this.childAnimal.getGrowingAge() >= 0) {
         return false;
      }

      List<EntityAnimal> var4 = this.childAnimal.worldObj.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.getEntityBoundingBox().expand(8.0, 4.0, 8.0));
      EntityAnimal var5 = null;
      double var6 = Double.MAX_VALUE;

      for (EntityAnimal var9 : var4) {
         if (var9.getGrowingAge() >= 0) {
            double var10 = this.childAnimal.getDistanceSqToEntity(var9);
            if (var10 <= var6) {
               var6 = var10;
               var5 = var9;
            }
         }
      }

      if (var5 == null) {
         return false;
      }

      if (var6 < 9.0) {
         return false;
      }

      this.parentAnimal = var5;
      return true;
   }

   @Override
   public boolean continueExecuting() {
      if (this.childAnimal.getGrowingAge() >= 0) {
         return false;
      }

      if (!this.parentAnimal.isEntityAlive()) {
         return false;
      }

      double var4 = this.childAnimal.getDistanceSqToEntity(this.parentAnimal);
      return var4 >= 9.0 && var4 <= 256.0;
   }

   @Override
   public void startExecuting() {
      this.delayCounter = 0;
   }

   @Override
   public void resetTask() {
      this.parentAnimal = null;
   }

   @Override
   public void updateTask() {
      if (--this.delayCounter <= 0) {
         this.delayCounter = 10;
         this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
      }
   }
}
