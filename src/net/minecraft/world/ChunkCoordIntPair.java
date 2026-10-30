package net.minecraft.world;

import net.minecraft.util.BlockPos;

public class ChunkCoordIntPair {
   public final int chunkXPos;
   public final int chunkZPos;
   private int cachedHashCode = 0;

   public ChunkCoordIntPair(int integer1, int integer2) {
      this.chunkXPos = integer1;
      this.chunkZPos = integer2;
   }

   public static long chunkXZ2Int(int integer1, int integer2) {
      return integer1 & 4294967295L | (integer2 & 4294967295L) << 32;
   }

   @Override
   public int hashCode() {
      if (this.cachedHashCode == 0) {
         int var4 = 1664525 * this.chunkXPos + 1013904223;
         int var5 = 1664525 * (this.chunkZPos ^ -559038737) + 1013904223;
         this.cachedHashCode = var4 ^ var5;
      }

      return this.cachedHashCode;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         return !(object instanceof ChunkCoordIntPair var5) ? false : this.chunkXPos == var5.chunkXPos && this.chunkZPos == var5.chunkZPos;
      }
   }

   public int getCenterXPos() {
      return (this.chunkXPos << 4) + 8;
   }

   public int getCenterZPosition() {
      return (this.chunkZPos << 4) + 8;
   }

   public int getXStart() {
      return this.chunkXPos << 4;
   }

   public int getZStart() {
      return this.chunkZPos << 4;
   }

   public int getXEnd() {
      return (this.chunkXPos << 4) + 15;
   }

   public int getZEnd() {
      return (this.chunkZPos << 4) + 15;
   }

   public BlockPos getBlock(int integer1, int integer2, int integer3) {
      return new BlockPos((this.chunkXPos << 4) + integer1, integer2, (this.chunkZPos << 4) + integer3);
   }

   public BlockPos getCenterBlock(int integer) {
      return new BlockPos(this.getCenterXPos(), integer, this.getCenterZPosition());
   }

   @Override
   public String toString() {
      return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
   }
}
