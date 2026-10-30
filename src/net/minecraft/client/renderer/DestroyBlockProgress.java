package net.minecraft.client.renderer;

import net.minecraft.util.BlockPos;

public class DestroyBlockProgress {
   private final int miningPlayerEntId;
   private final BlockPos position;
   private int partialBlockProgress;
   private int createdAtCloudUpdateTick;

   public DestroyBlockProgress(int integer, BlockPos blockPos) {
      this.miningPlayerEntId = integer;
      this.position = blockPos;
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public void setPartialBlockDamage(int integer) {
      if (integer > 10) {
         integer = 10;
      }

      this.partialBlockProgress = integer;
   }

   public int getPartialBlockDamage() {
      return this.partialBlockProgress;
   }

   public void setCloudUpdateTick(int integer) {
      this.createdAtCloudUpdateTick = integer;
   }

   public int getCreationCloudUpdateTick() {
      return this.createdAtCloudUpdateTick;
   }
}
