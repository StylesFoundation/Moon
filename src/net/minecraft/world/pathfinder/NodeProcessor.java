package net.minecraft.world.pathfinder;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public abstract class NodeProcessor {
   protected IBlockAccess blockaccess;
   protected IntHashMap<PathPoint> pointMap = new IntHashMap<>();
   protected int entitySizeX;
   protected int entitySizeY;
   protected int entitySizeZ;

   public void initProcessor(IBlockAccess iBlockAccess, Entity entity) {
      this.blockaccess = iBlockAccess;
      this.pointMap.clearMap();
      this.entitySizeX = MathHelper.floor_float(entity.width + 1.0F);
      this.entitySizeY = MathHelper.floor_float(entity.height + 1.0F);
      this.entitySizeZ = MathHelper.floor_float(entity.width + 1.0F);
   }

   public void postProcess() {
   }

   protected PathPoint openPoint(int integer1, int integer2, int integer3) {
      int var7 = PathPoint.makeHash(integer1, integer2, integer3);
      PathPoint var8 = this.pointMap.lookup(var7);
      if (var8 == null) {
         var8 = new PathPoint(integer1, integer2, integer3);
         this.pointMap.addKey(var7, var8);
      }

      return var8;
   }

   public abstract PathPoint getPathPointTo(Entity entity);

   public abstract PathPoint getPathPointToCoords(Entity entity, double double2, double double3, double double4);

   public abstract int findPathOptions(PathPoint[] arr, Entity entity, PathPoint pathPoint3, PathPoint pathPoint4, float float5);
}
