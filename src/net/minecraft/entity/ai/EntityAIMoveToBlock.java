package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class EntityAIMoveToBlock extends EntityAIBase {
   private final EntityCreature theEntity;
   private final double movementSpeed;
   protected int runDelay;
   private int timeoutCounter;
   private int field_179490_f;
   protected BlockPos destinationBlock = BlockPos.ORIGIN;
   private boolean isAboveDestination;
   private final int searchLength;

   public EntityAIMoveToBlock(EntityCreature entityCreature, double double2, int integer) {
      this.theEntity = entityCreature;
      this.movementSpeed = double2;
      this.searchLength = integer;
      this.setMutexBits(5);
   }

   @Override
   public boolean shouldExecute() {
      if (this.runDelay > 0) {
         this.runDelay--;
         return false;
      } else {
         this.runDelay = 200 + this.theEntity.getRNG().nextInt(200);
         return this.searchForDestination();
      }
   }

   @Override
   public boolean continueExecuting() {
      return this.timeoutCounter >= -this.field_179490_f && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.theEntity.worldObj, this.destinationBlock);
   }

   @Override
   public void startExecuting() {
      this.theEntity
         .getNavigator()
         .tryMoveToXYZ(this.destinationBlock.getX() + 0.5, this.destinationBlock.getY() + 1, this.destinationBlock.getZ() + 0.5, this.movementSpeed);
      this.timeoutCounter = 0;
      this.field_179490_f = this.theEntity.getRNG().nextInt(this.theEntity.getRNG().nextInt(1200) + 1200) + 1200;
   }

   @Override
   public void resetTask() {
   }

   @Override
   public void updateTask() {
      if (this.theEntity.getDistanceSqToCenter(this.destinationBlock.up()) > 1.0) {
         this.isAboveDestination = false;
         this.timeoutCounter++;
         if (this.timeoutCounter % 40 == 0) {
            this.theEntity
               .getNavigator()
               .tryMoveToXYZ(this.destinationBlock.getX() + 0.5, this.destinationBlock.getY() + 1, this.destinationBlock.getZ() + 0.5, this.movementSpeed);
         }
      } else {
         this.isAboveDestination = true;
         this.timeoutCounter--;
      }
   }

   protected boolean getIsAboveDestination() {
      return this.isAboveDestination;
   }

   private boolean searchForDestination() {
      BlockPos var5 = new BlockPos(this.theEntity);

      for (byte var6 = 0; var6 <= 1; var6 = (byte)(var6 > 0 ? -var6 : 1 - var6)) {
         for (int var7 = 0; var7 < this.searchLength; var7++) {
            for (byte var8 = 0; var8 <= var7; var8 = (byte)(var8 > 0 ? -var8 : 1 - var8)) {
               for (byte var9 = (byte)(var8 < var7 && var8 > -var7 ? var7 : 0); var9 <= var7; var9 = (byte)(var9 > 0 ? -var9 : 1 - var9)) {
                  BlockPos var10 = var5.add(var8, var6 - 1, var9);
                  if (this.theEntity.isWithinHomeDistanceFromPosition(var10) && this.shouldMoveTo(this.theEntity.worldObj, var10)) {
                     this.destinationBlock = var10;
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   protected abstract boolean shouldMoveTo(World world, BlockPos blockPos);
}
