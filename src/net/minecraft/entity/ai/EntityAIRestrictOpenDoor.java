package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIRestrictOpenDoor extends EntityAIBase {
   private final EntityCreature entityObj;
   private VillageDoorInfo frontDoor;

   public EntityAIRestrictOpenDoor(EntityCreature entityCreature) {
      this.entityObj = entityCreature;
      if (!(entityCreature.getNavigator() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
      }
   }

   @Override
   public boolean shouldExecute() {
      if (this.entityObj.worldObj.isDaytime()) {
         return false;
      }

      BlockPos var4 = new BlockPos(this.entityObj);
      Village var5 = this.entityObj.worldObj.getVillageCollection().getNearestVillage(var4, 16);
      if (var5 == null) {
         return false;
      }

      this.frontDoor = var5.getNearestDoor(var4);
      return this.frontDoor != null && this.frontDoor.getDistanceToInsideBlockSq(var4) < 2.25;
   }

   @Override
   public boolean continueExecuting() {
      return !this.entityObj.worldObj.isDaytime()
         && !this.frontDoor.getIsDetachedFromVillageFlag()
         && this.frontDoor.func_179850_c(new BlockPos(this.entityObj));
   }

   @Override
   public void startExecuting() {
      ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(false);
      ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(false);
   }

   @Override
   public void resetTask() {
      ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(true);
      ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(true);
      this.frontDoor = null;
   }

   @Override
   public void updateTask() {
      this.frontDoor.incrementDoorOpeningRestrictionCounter();
   }
}
