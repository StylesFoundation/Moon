package net.optifine;

import java.util.Comparator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;

public class ChunkPosComparator implements Comparator<ChunkCoordIntPair> {
   private final int chunkPosX;
   private final int chunkPosZ;
   private final double yawRad;
   private final double pitchNorm;

   public ChunkPosComparator(int integer1, int integer2, double double3, double double4) {
      this.chunkPosX = integer1;
      this.chunkPosZ = integer2;
      this.yawRad = double3;
      this.pitchNorm = 1.0 - MathHelper.clamp_double(Math.abs(double4) / (Math.PI / 2), 0.0, 1.0);
   }

   public int compare(ChunkCoordIntPair chunkCoordIntPair1, ChunkCoordIntPair chunkCoordIntPair2) {
      int var3 = this.getDistSq(chunkCoordIntPair1);
      int var4 = this.getDistSq(chunkCoordIntPair2);
      return var3 - var4;
   }

   private int getDistSq(ChunkCoordIntPair chunkCoordIntPair) {
      int var5 = chunkCoordIntPair.chunkXPos - this.chunkPosX;
      int var6 = chunkCoordIntPair.chunkZPos - this.chunkPosZ;
      int var7 = var5 * var5 + var6 * var6;
      double var8 = MathHelper.Method565(var6, var5);
      double var10 = Math.abs(var8 - this.yawRad);
      if (var10 > Math.PI) {
         var10 = (Math.PI * 2) - var10;
      }

      return (int)(var7 * 1000.0 * this.pitchNorm * var10 * var10);
   }
}
