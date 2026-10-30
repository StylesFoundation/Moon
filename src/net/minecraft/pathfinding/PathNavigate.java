package net.minecraft.pathfinding;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

public abstract class PathNavigate {
   protected EntityLiving theEntity;
   protected World worldObj;
   protected PathEntity currentPath;
   protected double speed;
   private final IAttributeInstance pathSearchRange;
   private int totalTicks;
   private int ticksAtLastPos;
   private Vec3 lastPosCheck = new Vec3(0.0, 0.0, 0.0);
   private float heightRequirement = 1.0F;
   private final PathFinder pathFinder;

   public PathNavigate(EntityLiving entityLiving, World world) {
      this.theEntity = entityLiving;
      this.worldObj = world;
      this.pathSearchRange = entityLiving.getEntityAttribute(SharedMonsterAttributes.followRange);
      this.pathFinder = this.getPathFinder();
   }

   protected abstract PathFinder getPathFinder();

   public void setSpeed(double double1) {
      this.speed = double1;
   }

   public float getPathSearchRange() {
      return (float)this.pathSearchRange.getAttributeValue();
   }

   public final PathEntity getPathToXYZ(double double1, double double2, double double3) {
      return this.getPathToPos(new BlockPos(MathHelper.floor_double(double1), (int)double2, MathHelper.floor_double(double3)));
   }

   public PathEntity getPathToPos(BlockPos blockPos) {
      if (!this.canNavigate()) {
         return null;
      }

      float var5 = this.getPathSearchRange();
      this.worldObj.theProfiler.startSection("pathfind");
      BlockPos var6 = new BlockPos(this.theEntity);
      int var7 = (int)(var5 + 8.0F);
      ChunkCache var8 = new ChunkCache(this.worldObj, var6.add(-var7, -var7, -var7), var6.add(var7, var7, var7), 0);
      PathEntity var9 = this.pathFinder.createEntityPathTo(var8, this.theEntity, blockPos, var5);
      this.worldObj.theProfiler.endSection();
      return var9;
   }

   public boolean tryMoveToXYZ(double double1, double double2, double double3, double double4) {
      PathEntity var9 = this.getPathToXYZ(MathHelper.floor_double(double1), (int)double2, MathHelper.floor_double(double3));
      return this.setPath(var9, double4);
   }

   public void setHeightRequirement(float float1) {
      this.heightRequirement = float1;
   }

   public PathEntity getPathToEntityLiving(Entity entity) {
      if (!this.canNavigate()) {
         return null;
      }

      float var5 = this.getPathSearchRange();
      this.worldObj.theProfiler.startSection("pathfind");
      BlockPos var6 = new BlockPos(this.theEntity).up();
      int var7 = (int)(var5 + 16.0F);
      ChunkCache var8 = new ChunkCache(this.worldObj, var6.add(-var7, -var7, -var7), var6.add(var7, var7, var7), 0);
      PathEntity var9 = this.pathFinder.createEntityPathTo(var8, this.theEntity, entity, var5);
      this.worldObj.theProfiler.endSection();
      return var9;
   }

   public boolean tryMoveToEntityLiving(Entity entity, double double2) {
      PathEntity var7 = this.getPathToEntityLiving(entity);
      return var7 != null && this.setPath(var7, double2);
   }

   public boolean setPath(PathEntity pathEntity, double double2) {
      if (pathEntity == null) {
         this.currentPath = null;
         return false;
      }

      if (!pathEntity.isSamePath(this.currentPath)) {
         this.currentPath = pathEntity;
      }

      this.removeSunnyPath();
      if (this.currentPath.getCurrentPathLength() == 0) {
         return false;
      }

      this.speed = double2;
      Vec3 var7 = this.getEntityPosition();
      this.ticksAtLastPos = this.totalTicks;
      this.lastPosCheck = var7;
      return true;
   }

   public PathEntity getPath() {
      return this.currentPath;
   }

   public void onUpdateNavigation() {
      this.totalTicks++;
      if (!this.noPath()) {
         if (this.canNavigate()) {
            this.pathFollow();
         } else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength()) {
            Vec3 var4 = this.getEntityPosition();
            Vec3 var5 = this.currentPath.getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex());
            if (var4.yCoord > var5.yCoord
               && !this.theEntity.onGround
               && MathHelper.floor_double(var4.xCoord) == MathHelper.floor_double(var5.xCoord)
               && MathHelper.floor_double(var4.zCoord) == MathHelper.floor_double(var5.zCoord)) {
               this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
            }
         }

         if (!this.noPath()) {
            Vec3 var11 = this.currentPath.getPosition(this.theEntity);
            if (var11 != null) {
               AxisAlignedBB var12 = new AxisAlignedBB(var11.xCoord, var11.yCoord, var11.zCoord, var11.xCoord, var11.yCoord, var11.zCoord)
                  .expand(0.5, 0.5, 0.5);
               List<AxisAlignedBB> var6 = this.worldObj.getCollidingBoundingBoxes(this.theEntity, var12.addCoord(0.0, -1.0, 0.0));
               double var7 = -1.0;
               var12 = var12.offset(0.0, 1.0, 0.0);

               for (AxisAlignedBB var10 : var6) {
                  var7 = var10.calculateYOffset(var12, var7);
               }

               this.theEntity.getMoveHelper().setMoveTo(var11.xCoord, var11.yCoord + var7, var11.zCoord, this.speed);
            }
         }
      }
   }

   protected void pathFollow() {
      Vec3 var4 = this.getEntityPosition();
      int var5 = this.currentPath.getCurrentPathLength();

      for (int var6 = this.currentPath.getCurrentPathIndex(); var6 < this.currentPath.getCurrentPathLength(); var6++) {
         if (this.currentPath.getPathPointFromIndex(var6).yCoord != (int)var4.yCoord) {
            var5 = var6;
            break;
         }
      }

      float var10 = this.theEntity.width * this.theEntity.width * this.heightRequirement;

      for (int var7 = this.currentPath.getCurrentPathIndex(); var7 < var5; var7++) {
         Vec3 var8 = this.currentPath.getVectorFromIndex(this.theEntity, var7);
         if (var4.squareDistanceTo(var8) < var10) {
            this.currentPath.setCurrentPathIndex(var7 + 1);
         }
      }

      int var11 = MathHelper.ceiling_float_int(this.theEntity.width);
      int var12 = (int)this.theEntity.height + 1;

      for (int var9 = var5 - 1; var9 >= this.currentPath.getCurrentPathIndex(); var9--) {
         if (this.isDirectPathBetweenPoints(var4, this.currentPath.getVectorFromIndex(this.theEntity, var9), var11, var12, var11)) {
            this.currentPath.setCurrentPathIndex(var9);
            break;
         }
      }

      this.checkForStuck(var4);
   }

   protected void checkForStuck(Vec3 vec3) {
      if (this.totalTicks - this.ticksAtLastPos > 100) {
         if (vec3.squareDistanceTo(this.lastPosCheck) < 2.25) {
            this.clearPathEntity();
         }

         this.ticksAtLastPos = this.totalTicks;
         this.lastPosCheck = vec3;
      }
   }

   public boolean noPath() {
      return this.currentPath == null || this.currentPath.isFinished();
   }

   public void clearPathEntity() {
      this.currentPath = null;
   }

   protected abstract Vec3 getEntityPosition();

   protected abstract boolean canNavigate();

   protected boolean isInLiquid() {
      return this.theEntity.isInWater() || this.theEntity.isInLava();
   }

   protected void removeSunnyPath() {
   }

   protected abstract boolean isDirectPathBetweenPoints(Vec3 vec31, Vec3 vec32, int integer3, int integer4, int integer5);
}
