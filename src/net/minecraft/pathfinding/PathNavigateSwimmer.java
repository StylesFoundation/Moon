package net.minecraft.pathfinding;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.pathfinder.SwimNodeProcessor;

public class PathNavigateSwimmer extends PathNavigate {
   public PathNavigateSwimmer(EntityLiving entityLiving, World world) {
      super(entityLiving, world);
   }

   @Override
   protected PathFinder getPathFinder() {
      return new PathFinder(new SwimNodeProcessor());
   }

   @Override
   protected boolean canNavigate() {
      return this.isInLiquid();
   }

   @Override
   protected Vec3 getEntityPosition() {
      return new Vec3(this.theEntity.posX, this.theEntity.posY + this.theEntity.height * 0.5, this.theEntity.posZ);
   }

   @Override
   protected void pathFollow() {
      Vec3 var4 = this.getEntityPosition();
      float var5 = this.theEntity.width * this.theEntity.width;
      byte var6 = 6;
      if (var4.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex())) < var5) {
         this.currentPath.incrementPathIndex();
      }

      for (int var7 = Math.min(this.currentPath.getCurrentPathIndex() + var6, this.currentPath.getCurrentPathLength() - 1);
         var7 > this.currentPath.getCurrentPathIndex();
         var7--
      ) {
         Vec3 var8 = this.currentPath.getVectorFromIndex(this.theEntity, var7);
         if (var8.squareDistanceTo(var4) <= 36.0 && this.isDirectPathBetweenPoints(var4, var8, 0, 0, 0)) {
            this.currentPath.setCurrentPathIndex(var7);
            break;
         }
      }

      this.checkForStuck(var4);
   }

   @Override
   protected void removeSunnyPath() {
      super.removeSunnyPath();
   }

   @Override
   protected boolean isDirectPathBetweenPoints(Vec3 vec31, Vec3 vec32, int integer3, int integer4, int integer5) {
      MovingObjectPosition var9 = this.worldObj
         .rayTraceBlocks(vec31, new Vec3(vec32.xCoord, vec32.yCoord + this.theEntity.height * 0.5, vec32.zCoord), false, true, false);
      return var9 == null || var9.typeOfHit == MovingObjectPosition.MovingObjectType.MISS;
   }
}
