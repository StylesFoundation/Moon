package net.minecraft.pathfinding;

public class Path {
   private PathPoint[] pathPoints = new PathPoint[1024];
   private int count;

   public PathPoint addPoint(PathPoint pathPoint) {
      if (pathPoint.index >= 0) {
         throw new IllegalStateException("OW KNOWS!");
      }

      if (this.count == this.pathPoints.length) {
         PathPoint[] var5 = new PathPoint[this.count << 1];
         System.arraycopy(this.pathPoints, 0, var5, 0, this.count);
         this.pathPoints = var5;
      }

      this.pathPoints[this.count] = pathPoint;
      pathPoint.index = this.count;
      this.sortBack(this.count++);
      return pathPoint;
   }

   public void clearPath() {
      this.count = 0;
   }

   public PathPoint dequeue() {
      PathPoint var4 = this.pathPoints[0];
      this.pathPoints[0] = this.pathPoints[--this.count];
      this.pathPoints[this.count] = null;
      if (this.count > 0) {
         this.sortForward(0);
      }

      var4.index = -1;
      return var4;
   }

   public void changeDistance(PathPoint pathPoint, float float2) {
      float var6 = pathPoint.distanceToTarget;
      pathPoint.distanceToTarget = float2;
      if (float2 < var6) {
         this.sortBack(pathPoint.index);
      } else {
         this.sortForward(pathPoint.index);
      }
   }

   private void sortBack(int integer) {
      PathPoint var5 = this.pathPoints[integer];
      float var7 = var5.distanceToTarget;

      while (integer > 0) {
         int var6 = integer - 1 >> 1;
         PathPoint var8 = this.pathPoints[var6];
         if (var7 >= var8.distanceToTarget) {
            break;
         }

         this.pathPoints[integer] = var8;
         var8.index = integer;
         integer = var6;
      }

      this.pathPoints[integer] = var5;
      var5.index = integer;
   }

   private void sortForward(int integer) {
      PathPoint var5 = this.pathPoints[integer];
      float var6 = var5.distanceToTarget;

      while (true) {
         int var7 = 1 + (integer << 1);
         int var8 = var7 + 1;
         if (var7 >= this.count) {
            break;
         }

         PathPoint var9 = this.pathPoints[var7];
         float var10 = var9.distanceToTarget;
         PathPoint var11;
         float var12;
         if (var8 >= this.count) {
            var11 = null;
            var12 = Float.POSITIVE_INFINITY;
         } else {
            var11 = this.pathPoints[var8];
            var12 = var11.distanceToTarget;
         }

         if (var10 < var12) {
            if (var10 >= var6) {
               break;
            }

            this.pathPoints[integer] = var9;
            var9.index = integer;
            integer = var7;
         } else {
            if (var12 >= var6) {
               break;
            }

            this.pathPoints[integer] = var11;
            var11.index = integer;
            integer = var8;
         }
      }

      this.pathPoints[integer] = var5;
      var5.index = integer;
   }

   public boolean isPathEmpty() {
      return this.count == 0;
   }
}
