package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAIOpenDoor extends EntityAIDoorInteract {
   boolean closeDoor;
   int closeDoorTemporisation;

   public EntityAIOpenDoor(EntityLiving entityLiving, boolean boolean2) {
      super(entityLiving);
      this.theEntity = entityLiving;
      this.closeDoor = boolean2;
   }

   @Override
   public boolean continueExecuting() {
      return this.closeDoor && this.closeDoorTemporisation > 0 && super.continueExecuting();
   }

   @Override
   public void startExecuting() {
      this.closeDoorTemporisation = 20;
      this.doorBlock.toggleDoor(this.theEntity.worldObj, this.doorPosition, true);
   }

   @Override
   public void resetTask() {
      if (this.closeDoor) {
         this.doorBlock.toggleDoor(this.theEntity.worldObj, this.doorPosition, false);
      }
   }

   @Override
   public void updateTask() {
      this.closeDoorTemporisation--;
      super.updateTask();
   }
}
