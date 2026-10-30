package net.minecraft.village;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class VillageDoorInfo {
   private final BlockPos doorBlockPos;
   private final BlockPos insideBlock;
   private final EnumFacing insideDirection;
   private int lastActivityTimestamp;
   private boolean isDetachedFromVillageFlag;
   private int doorOpeningRestrictionCounter;

   public VillageDoorInfo(BlockPos blockPos, int integer2, int integer3, int integer4) {
      this(blockPos, getFaceDirection(integer2, integer3), integer4);
   }

   private static EnumFacing getFaceDirection(int integer1, int integer2) {
      return integer1 < 0 ? EnumFacing.WEST : (integer1 > 0 ? EnumFacing.EAST : (integer2 < 0 ? EnumFacing.NORTH : EnumFacing.SOUTH));
   }

   public VillageDoorInfo(BlockPos blockPos, EnumFacing enumFacing, int integer) {
      this.doorBlockPos = blockPos;
      this.insideDirection = enumFacing;
      this.insideBlock = blockPos.offset(enumFacing, 2);
      this.lastActivityTimestamp = integer;
   }

   public int getDistanceSquared(int integer1, int integer2, int integer3) {
      return (int)this.doorBlockPos.distanceSq(integer1, integer2, integer3);
   }

   public int getDistanceToDoorBlockSq(BlockPos blockPos) {
      return (int)blockPos.distanceSq(this.getDoorBlockPos());
   }

   public int getDistanceToInsideBlockSq(BlockPos blockPos) {
      return (int)this.insideBlock.distanceSq(blockPos);
   }

   public boolean func_179850_c(BlockPos blockPos) {
      int var5 = blockPos.getX() - this.doorBlockPos.getX();
      int var6 = blockPos.getZ() - this.doorBlockPos.getY();
      return var5 * this.insideDirection.getFrontOffsetX() + var6 * this.insideDirection.getFrontOffsetZ() >= 0;
   }

   public void resetDoorOpeningRestrictionCounter() {
      this.doorOpeningRestrictionCounter = 0;
   }

   public void incrementDoorOpeningRestrictionCounter() {
      this.doorOpeningRestrictionCounter++;
   }

   public int getDoorOpeningRestrictionCounter() {
      return this.doorOpeningRestrictionCounter;
   }

   public BlockPos getDoorBlockPos() {
      return this.doorBlockPos;
   }

   public BlockPos getInsideBlockPos() {
      return this.insideBlock;
   }

   public int getInsideOffsetX() {
      return this.insideDirection.getFrontOffsetX() * 2;
   }

   public int getInsideOffsetZ() {
      return this.insideDirection.getFrontOffsetZ() * 2;
   }

   public int getInsidePosY() {
      return this.lastActivityTimestamp;
   }

   public void func_179849_a(int integer) {
      this.lastActivityTimestamp = integer;
   }

   public boolean getIsDetachedFromVillageFlag() {
      return this.isDetachedFromVillageFlag;
   }

   public void setIsDetachedFromVillageFlag(boolean boolean1) {
      this.isDetachedFromVillageFlag = boolean1;
   }
}
