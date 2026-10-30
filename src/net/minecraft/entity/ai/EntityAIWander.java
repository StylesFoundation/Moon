package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.Vec3;

public class EntityAIWander extends EntityAIBase {
   private final EntityCreature entity;
   private double xPosition;
   private double yPosition;
   private double zPosition;
   private final double speed;
   private int executionChance;
   private boolean mustUpdate;

   public EntityAIWander(EntityCreature entityCreature, double double2) {
      this(entityCreature, double2, 120);
   }

   public EntityAIWander(EntityCreature entityCreature, double double2, int integer) {
      this.entity = entityCreature;
      this.speed = double2;
      this.executionChance = integer;
      this.setMutexBits(1);
   }

   @Override
   public boolean shouldExecute() {
      if (!this.mustUpdate) {
         if (this.entity.getAge() >= 100) {
            return false;
         }

         if (this.entity.getRNG().nextInt(this.executionChance) != 0) {
            return false;
         }
      }

      Vec3 var4 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
      if (var4 == null) {
         return false;
      }

      this.xPosition = var4.xCoord;
      this.yPosition = var4.yCoord;
      this.zPosition = var4.zCoord;
      this.mustUpdate = false;
      return true;
   }

   @Override
   public boolean continueExecuting() {
      return !this.entity.getNavigator().noPath();
   }

   @Override
   public void startExecuting() {
      this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
   }

   public void makeUpdate() {
      this.mustUpdate = true;
   }

   public void setExecutionChance(int integer) {
      this.executionChance = integer;
   }
}
