package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class EntityAIRunAroundLikeCrazy extends EntityAIBase {
   private final EntityHorse horseHost;
   private final double speed;
   private double targetX;
   private double targetY;
   private double targetZ;

   public EntityAIRunAroundLikeCrazy(EntityHorse entityHorse, double double2) {
      this.horseHost = entityHorse;
      this.speed = double2;
      this.setMutexBits(1);
   }

   @Override
   public boolean shouldExecute() {
      if (!this.horseHost.isTame() && this.horseHost.riddenByEntity != null) {
         Vec3 var4 = RandomPositionGenerator.findRandomTarget(this.horseHost, 5, 4);
         if (var4 == null) {
            return false;
         }

         this.targetX = var4.xCoord;
         this.targetY = var4.yCoord;
         this.targetZ = var4.zCoord;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void startExecuting() {
      this.horseHost.getNavigator().tryMoveToXYZ(this.targetX, this.targetY, this.targetZ, this.speed);
   }

   @Override
   public boolean continueExecuting() {
      return !this.horseHost.getNavigator().noPath() && this.horseHost.riddenByEntity != null;
   }

   @Override
   public void updateTask() {
      if (this.horseHost.getRNG().nextInt(50) == 0) {
         if (this.horseHost.riddenByEntity instanceof EntityPlayer) {
            int var4 = this.horseHost.getTemper();
            int var5 = this.horseHost.getMaxTemper();
            if (var5 > 0 && this.horseHost.getRNG().nextInt(var5) < var4) {
               this.horseHost.setTamedBy((EntityPlayer)this.horseHost.riddenByEntity);
               this.horseHost.worldObj.setEntityState(this.horseHost, (byte)7);
               return;
            }

            this.horseHost.increaseTemper(5);
         }

         this.horseHost.riddenByEntity.mountEntity(null);
         this.horseHost.riddenByEntity = null;
         this.horseHost.makeHorseRearWithSound();
         this.horseHost.worldObj.setEntityState(this.horseHost, (byte)6);
      }
   }
}
