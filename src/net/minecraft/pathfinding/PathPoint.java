package net.minecraft.pathfinding;

import net.minecraft.util.MathHelper;

public class PathPoint {
   public final int xCoord;
   public final int yCoord;
   public final int zCoord;
   private final int hash;
   int index = -1;
   float totalPathDistance;
   float distanceToNext;
   float distanceToTarget;
   PathPoint previous;
   public boolean visited;

   public PathPoint(int integer1, int integer2, int integer3) {
      this.xCoord = integer1;
      this.yCoord = integer2;
      this.zCoord = integer3;
      this.hash = makeHash(integer1, integer2, integer3);
   }

   public static int makeHash(int integer1, int integer2, int integer3) {
      return integer2 & 0xFF | (integer1 & 32767) << 8 | (integer3 & 32767) << 24 | (integer1 < 0 ? Integer.MIN_VALUE : 0) | (integer3 < 0 ? 32768 : 0);
   }

   public float distanceTo(PathPoint pathPoint) {
      float var2 = pathPoint.xCoord - this.xCoord;
      float var3 = pathPoint.yCoord - this.yCoord;
      float var4 = pathPoint.zCoord - this.zCoord;
      return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
   }

   public float distanceToSquared(PathPoint pathPoint) {
      float var2 = pathPoint.xCoord - this.xCoord;
      float var3 = pathPoint.yCoord - this.yCoord;
      float var4 = pathPoint.zCoord - this.zCoord;
      return var2 * var2 + var3 * var3 + var4 * var4;
   }

   @Override
   public boolean equals(Object object) {
      return !(object instanceof PathPoint var5)
         ? false
         : this.hash == var5.hash && this.xCoord == var5.xCoord && this.yCoord == var5.yCoord && this.zCoord == var5.zCoord;
   }

   @Override
   public int hashCode() {
      return this.hash;
   }

   public boolean isAssigned() {
      return this.index >= 0;
   }

   @Override
   public String toString() {
      return this.xCoord + ", " + this.yCoord + ", " + this.zCoord;
   }
}
