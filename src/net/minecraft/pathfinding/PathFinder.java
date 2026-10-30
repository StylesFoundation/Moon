package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.pathfinder.NodeProcessor;

public class PathFinder {
   private final Path path = new Path();
   private final PathPoint[] pathOptions = new PathPoint[32];
   private final NodeProcessor nodeProcessor;

   public PathFinder(NodeProcessor nodeProcessor) {
      this.nodeProcessor = nodeProcessor;
   }

   public PathEntity createEntityPathTo(IBlockAccess iBlockAccess, Entity entity2, Entity entity3, float float4) {
      return this.createEntityPathTo(iBlockAccess, entity2, entity3.posX, entity3.getEntityBoundingBox().minY, entity3.posZ, float4);
   }

   public PathEntity createEntityPathTo(IBlockAccess iBlockAccess, Entity entity, BlockPos blockPos, float float4) {
      return this.createEntityPathTo(iBlockAccess, entity, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, float4);
   }

   private PathEntity createEntityPathTo(IBlockAccess iBlockAccess, Entity entity, double double3, double double4, double double5, float float6) {
      this.path.clearPath();
      this.nodeProcessor.initProcessor(iBlockAccess, entity);
      PathPoint var10 = this.nodeProcessor.getPathPointTo(entity);
      PathPoint var11 = this.nodeProcessor.getPathPointToCoords(entity, double3, double4, double5);
      PathEntity var12 = this.addToPath(entity, var10, var11, float6);
      this.nodeProcessor.postProcess();
      return var12;
   }

   private PathEntity addToPath(Entity entity, PathPoint pathPoint2, PathPoint pathPoint3, float float4) {
      pathPoint2.totalPathDistance = 0.0F;
      pathPoint2.distanceToNext = pathPoint2.distanceToSquared(pathPoint3);
      pathPoint2.distanceToTarget = pathPoint2.distanceToNext;
      this.path.clearPath();
      this.path.addPoint(pathPoint2);
      PathPoint var8 = pathPoint2;

      while (!this.path.isPathEmpty()) {
         PathPoint var9 = this.path.dequeue();
         if (var9.equals(pathPoint3)) {
            return this.createEntityPath(pathPoint2, pathPoint3);
         }

         if (var9.distanceToSquared(pathPoint3) < var8.distanceToSquared(pathPoint3)) {
            var8 = var9;
         }

         var9.visited = true;
         int var10 = this.nodeProcessor.findPathOptions(this.pathOptions, entity, var9, pathPoint3, float4);

         for (int var11 = 0; var11 < var10; var11++) {
            PathPoint var12 = this.pathOptions[var11];
            float var13 = var9.totalPathDistance + var9.distanceToSquared(var12);
            if (var13 < float4 * 2.0F && (!var12.isAssigned() || var13 < var12.totalPathDistance)) {
               var12.previous = var9;
               var12.totalPathDistance = var13;
               var12.distanceToNext = var12.distanceToSquared(pathPoint3);
               if (var12.isAssigned()) {
                  this.path.changeDistance(var12, var12.totalPathDistance + var12.distanceToNext);
               } else {
                  var12.distanceToTarget = var12.totalPathDistance + var12.distanceToNext;
                  this.path.addPoint(var12);
               }
            }
         }
      }

      return var8 == pathPoint2 ? null : this.createEntityPath(pathPoint2, var8);
   }

   private PathEntity createEntityPath(PathPoint pathPoint1, PathPoint pathPoint2) {
      int var6 = 1;

      for (PathPoint var7 = pathPoint2; var7.previous != null; var7 = var7.previous) {
         var6++;
      }

      PathPoint[] var10 = new PathPoint[var6];
      PathPoint var8 = pathPoint2;

      for (var10[--var6] = pathPoint2; var8.previous != null; var10[--var6] = var8) {
         var8 = var8.previous;
      }

      return new PathEntity(var10);
   }
}
