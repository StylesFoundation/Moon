package net.minecraft.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

public class PathNavigateGround extends PathNavigate {
   protected WalkNodeProcessor nodeProcessor;
   private boolean shouldAvoidSun;

   public PathNavigateGround(EntityLiving entityLiving, World world) {
      super(entityLiving, world);
   }

   @Override
   protected PathFinder getPathFinder() {
      this.nodeProcessor = new WalkNodeProcessor();
      this.nodeProcessor.setEnterDoors(true);
      return new PathFinder(this.nodeProcessor);
   }

   @Override
   protected boolean canNavigate() {
      return this.theEntity.onGround
         || this.getCanSwim() && this.isInLiquid()
         || this.theEntity.isRiding() && this.theEntity instanceof EntityZombie && this.theEntity.ridingEntity instanceof EntityChicken;
   }

   @Override
   protected Vec3 getEntityPosition() {
      return new Vec3(this.theEntity.posX, this.getPathablePosY(), this.theEntity.posZ);
   }

   private int getPathablePosY() {
      if (this.theEntity.isInWater() && this.getCanSwim()) {
         int var4 = (int)this.theEntity.getEntityBoundingBox().minY;
         Block var5 = this.worldObj
            .getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), var4, MathHelper.floor_double(this.theEntity.posZ)))
            .getBlock();
         int var6 = 0;

         while (var5 == Blocks.flowing_water || var5 == Blocks.water) {
            var5 = this.worldObj
               .getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), ++var4, MathHelper.floor_double(this.theEntity.posZ)))
               .getBlock();
            if (++var6 > 16) {
               return (int)this.theEntity.getEntityBoundingBox().minY;
            }
         }

         return var4;
      } else {
         return (int)(this.theEntity.getEntityBoundingBox().minY + 0.5);
      }
   }

   @Override
   protected void removeSunnyPath() {
      super.removeSunnyPath();
      if (this.shouldAvoidSun) {
         if (this.worldObj
            .canSeeSky(
               new BlockPos(
                  MathHelper.floor_double(this.theEntity.posX),
                  (int)(this.theEntity.getEntityBoundingBox().minY + 0.5),
                  MathHelper.floor_double(this.theEntity.posZ)
               )
            )) {
            return;
         }

         for (int var4 = 0; var4 < this.currentPath.getCurrentPathLength(); var4++) {
            PathPoint var5 = this.currentPath.getPathPointFromIndex(var4);
            if (this.worldObj.canSeeSky(new BlockPos(var5.xCoord, var5.yCoord, var5.zCoord))) {
               this.currentPath.setCurrentPathLength(var4 - 1);
               return;
            }
         }
      }
   }

   @Override
   protected boolean isDirectPathBetweenPoints(Vec3 vec31, Vec3 vec32, int integer3, int integer4, int integer5) {
      int var9 = MathHelper.floor_double(vec31.xCoord);
      int var10 = MathHelper.floor_double(vec31.zCoord);
      double var11 = vec32.xCoord - vec31.xCoord;
      double var13 = vec32.zCoord - vec31.zCoord;
      double var15 = var11 * var11 + var13 * var13;
      if (var15 < 1.0E-8) {
         return false;
      }

      double var17 = 1.0 / Math.sqrt(var15);
      var11 *= var17;
      var13 *= var17;
      integer3 += 2;
      integer5 += 2;
      if (!this.isSafeToStandAt(var9, (int)vec31.yCoord, var10, integer3, integer4, integer5, vec31, var11, var13)) {
         return false;
      }

      integer3 -= 2;
      integer5 -= 2;
      double var19 = 1.0 / Math.abs(var11);
      double var21 = 1.0 / Math.abs(var13);
      double var23 = var9 - vec31.xCoord;
      double var25 = var10 - vec31.zCoord;
      if (var11 >= 0.0) {
         var23++;
      }

      if (var13 >= 0.0) {
         var25++;
      }

      var23 /= var11;
      var25 /= var13;
      int var27 = var11 < 0.0 ? -1 : 1;
      int var28 = var13 < 0.0 ? -1 : 1;
      int var29 = MathHelper.floor_double(vec32.xCoord);
      int var30 = MathHelper.floor_double(vec32.zCoord);
      int var31 = var29 - var9;
      int var32 = var30 - var10;

      while (var31 * var27 > 0 || var32 * var28 > 0) {
         if (var23 < var25) {
            var23 += var19;
            var9 += var27;
            var31 = var29 - var9;
         } else {
            var25 += var21;
            var10 += var28;
            var32 = var30 - var10;
         }

         if (!this.isSafeToStandAt(var9, (int)vec31.yCoord, var10, integer3, integer4, integer5, vec31, var11, var13)) {
            return false;
         }
      }

      return true;
   }

   private boolean isSafeToStandAt(
      int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, Vec3 vec3, double double8, double double9
   ) {
      int var15 = integer1 - integer4 / 2;
      int var16 = integer3 - integer6 / 2;
      if (!this.isPositionClear(var15, integer2, var16, integer4, integer5, integer6, vec3, double8, double9)) {
         return false;
      }

      for (int var17 = var15; var17 < var15 + integer4; var17++) {
         for (int var18 = var16; var18 < var16 + integer6; var18++) {
            double var19 = var17 + 0.5 - vec3.xCoord;
            double var21 = var18 + 0.5 - vec3.zCoord;
            if (var19 * double8 + var21 * double9 >= 0.0) {
               Material var24 = this.worldObj.getBlockState(new BlockPos(var17, integer2 - 1, var18)).getBlock().getMaterial();
               if (var24 == Material.air) {
                  return false;
               }

               if (var24 == Material.water && !this.theEntity.isInWater()) {
                  return false;
               }

               if (var24 == Material.lava) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private boolean isPositionClear(
      int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, Vec3 vec3, double double8, double double9
   ) {
      for (BlockPos var16 : BlockPos.getAllInBox(
         new BlockPos(integer1, integer2, integer3), new BlockPos(integer1 + integer4 - 1, integer2 + integer5 - 1, integer3 + integer6 - 1)
      )) {
         double var17 = var16.getX() + 0.5 - vec3.xCoord;
         double var19 = var16.getZ() + 0.5 - vec3.zCoord;
         if (var17 * double8 + var19 * double9 >= 0.0) {
            Block var21 = this.worldObj.getBlockState(var16).getBlock();
            if (!var21.isPassable(this.worldObj, var16)) {
               return false;
            }
         }
      }

      return true;
   }

   public void setAvoidsWater(boolean boolean1) {
      this.nodeProcessor.setAvoidsWater(boolean1);
   }

   public boolean getAvoidsWater() {
      return this.nodeProcessor.getAvoidsWater();
   }

   public void setBreakDoors(boolean boolean1) {
      this.nodeProcessor.setBreakDoors(boolean1);
   }

   public void setEnterDoors(boolean boolean1) {
      this.nodeProcessor.setEnterDoors(boolean1);
   }

   public boolean getEnterDoors() {
      return this.nodeProcessor.getEnterDoors();
   }

   public void setCanSwim(boolean boolean1) {
      this.nodeProcessor.setCanSwim(boolean1);
   }

   public boolean getCanSwim() {
      return this.nodeProcessor.getCanSwim();
   }

   public void setAvoidSun(boolean boolean1) {
      this.shouldAvoidSun = boolean1;
   }
}
