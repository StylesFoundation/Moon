package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class PathEntity {
   private final PathPoint[] points;
   private int currentPathIndex;
   private int pathLength;

   public PathEntity(PathPoint[] arr) {
      this.points = arr;
      this.pathLength = arr.length;
   }

   public void incrementPathIndex() {
      this.currentPathIndex++;
   }

   public boolean isFinished() {
      return this.currentPathIndex >= this.pathLength;
   }

   public PathPoint getFinalPathPoint() {
      return this.pathLength > 0 ? this.points[this.pathLength - 1] : null;
   }

   public PathPoint getPathPointFromIndex(int integer) {
      return this.points[integer];
   }

   public int getCurrentPathLength() {
      return this.pathLength;
   }

   public void setCurrentPathLength(int integer) {
      this.pathLength = integer;
   }

   public int getCurrentPathIndex() {
      return this.currentPathIndex;
   }

   public void setCurrentPathIndex(int integer) {
      this.currentPathIndex = integer;
   }

   public Vec3 getVectorFromIndex(Entity entity, int integer) {
      double var3 = this.points[integer].xCoord + (int)(entity.width + 1.0F) * 0.5;
      double var5 = this.points[integer].yCoord;
      double var7 = this.points[integer].zCoord + (int)(entity.width + 1.0F) * 0.5;
      return new Vec3(var3, var5, var7);
   }

   public Vec3 getPosition(Entity entity) {
      return this.getVectorFromIndex(entity, this.currentPathIndex);
   }

   public boolean isSamePath(PathEntity pathEntity) {
      if (pathEntity == null) {
         return false;
      }

      if (pathEntity.points.length != this.points.length) {
         return false;
      }

      for (int var5 = 0; var5 < this.points.length; var5++) {
         if (this.points[var5].xCoord != pathEntity.points[var5].xCoord
            || this.points[var5].yCoord != pathEntity.points[var5].yCoord
            || this.points[var5].zCoord != pathEntity.points[var5].zCoord) {
            return false;
         }
      }

      return true;
   }

   public boolean isDestinationSame(Vec3 vec3) {
      PathPoint var5 = this.getFinalPathPoint();
      return var5 != null && var5.xCoord == (int)vec3.xCoord && var5.zCoord == (int)vec3.zCoord;
   }
}
