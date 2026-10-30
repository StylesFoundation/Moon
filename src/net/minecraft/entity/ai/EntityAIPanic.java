package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.Vec3;

public class EntityAIPanic extends EntityAIBase {
   private final EntityCreature theEntityCreature;
   protected double speed;
   private double randPosX;
   private double randPosY;
   private double randPosZ;

   public EntityAIPanic(EntityCreature entityCreature, double double2) {
      this.theEntityCreature = entityCreature;
      this.speed = double2;
      this.setMutexBits(1);
   }

   @Override
   public boolean shouldExecute() {
      if (this.theEntityCreature.getAITarget() == null && !this.theEntityCreature.isBurning()) {
         return false;
      }

      Vec3 var4 = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);
      if (var4 == null) {
         return false;
      }

      this.randPosX = var4.xCoord;
      this.randPosY = var4.yCoord;
      this.randPosZ = var4.zCoord;
      return true;
   }

   @Override
   public void startExecuting() {
      this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
   }

   @Override
   public boolean continueExecuting() {
      return !this.theEntityCreature.getNavigator().noPath();
   }
}
