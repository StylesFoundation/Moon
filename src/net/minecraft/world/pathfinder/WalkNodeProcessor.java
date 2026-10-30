package net.minecraft.world.pathfinder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class WalkNodeProcessor extends NodeProcessor {
   private boolean canEnterDoors;
   private boolean canBreakDoors;
   private boolean avoidsWater;
   private boolean canSwim;
   private boolean shouldAvoidWater;

   @Override
   public void initProcessor(IBlockAccess iBlockAccess, Entity entity) {
      super.initProcessor(iBlockAccess, entity);
      this.shouldAvoidWater = this.avoidsWater;
   }

   @Override
   public void postProcess() {
      super.postProcess();
      this.avoidsWater = this.shouldAvoidWater;
   }

   @Override
   public PathPoint getPathPointTo(Entity entity) {
      int var5;
      if (this.canSwim && entity.isInWater()) {
         var5 = (int)entity.getEntityBoundingBox().minY;
         BlockPos.MutableBlockPos var6 = new BlockPos.MutableBlockPos(MathHelper.floor_double(entity.posX), var5, MathHelper.floor_double(entity.posZ));

         for (Block var7 = this.blockaccess.getBlockState(var6).getBlock();
            var7 == Blocks.flowing_water || var7 == Blocks.water;
            var7 = this.blockaccess.getBlockState(var6).getBlock()
         ) {
            var6.set(MathHelper.floor_double(entity.posX), ++var5, MathHelper.floor_double(entity.posZ));
         }

         this.avoidsWater = false;
      } else {
         var5 = MathHelper.floor_double(entity.getEntityBoundingBox().minY + 0.5);
      }

      return this.openPoint(MathHelper.floor_double(entity.getEntityBoundingBox().minX), var5, MathHelper.floor_double(entity.getEntityBoundingBox().minZ));
   }

   @Override
   public PathPoint getPathPointToCoords(Entity entity, double double2, double double3, double double4) {
      return this.openPoint(
         MathHelper.floor_double(double2 - entity.width / 2.0F), MathHelper.floor_double(double3), MathHelper.floor_double(double4 - entity.width / 2.0F)
      );
   }

   @Override
   public int findPathOptions(PathPoint[] arr, Entity entity, PathPoint pathPoint3, PathPoint pathPoint4, float float5) {
      int var9 = 0;
      byte var10 = 0;
      if (this.getVerticalOffset(entity, pathPoint3.xCoord, pathPoint3.yCoord + 1, pathPoint3.zCoord) == 1) {
         var10 = 1;
      }

      PathPoint var11 = this.getSafePoint(entity, pathPoint3.xCoord, pathPoint3.yCoord, pathPoint3.zCoord + 1, var10);
      PathPoint var12 = this.getSafePoint(entity, pathPoint3.xCoord - 1, pathPoint3.yCoord, pathPoint3.zCoord, var10);
      PathPoint var13 = this.getSafePoint(entity, pathPoint3.xCoord + 1, pathPoint3.yCoord, pathPoint3.zCoord, var10);
      PathPoint var14 = this.getSafePoint(entity, pathPoint3.xCoord, pathPoint3.yCoord, pathPoint3.zCoord - 1, var10);
      if (var11 != null && !var11.visited && var11.distanceTo(pathPoint4) < float5) {
         var9++;
         arr[0] = var11;
      }

      if (var12 != null && !var12.visited && var12.distanceTo(pathPoint4) < float5) {
         arr[var9++] = var12;
      }

      if (var13 != null && !var13.visited && var13.distanceTo(pathPoint4) < float5) {
         arr[var9++] = var13;
      }

      if (var14 != null && !var14.visited && var14.distanceTo(pathPoint4) < float5) {
         arr[var9++] = var14;
      }

      return var9;
   }

   private PathPoint getSafePoint(Entity entity, int integer2, int integer3, int integer4, int integer5) {
      PathPoint var9 = null;
      int var10 = this.getVerticalOffset(entity, integer2, integer3, integer4);
      if (var10 == 2) {
         return this.openPoint(integer2, integer3, integer4);
      }

      if (var10 == 1) {
         var9 = this.openPoint(integer2, integer3, integer4);
      }

      if (var9 == null && integer5 > 0 && var10 != -3 && var10 != -4 && this.getVerticalOffset(entity, integer2, integer3 + integer5, integer4) == 1) {
         var9 = this.openPoint(integer2, integer3 + integer5, integer4);
         integer3 += integer5;
      }

      if (var9 != null) {
         int var11 = 0;
         int var12 = 0;

         while (integer3 > 0) {
            var12 = this.getVerticalOffset(entity, integer2, integer3 - 1, integer4);
            if (this.avoidsWater && var12 == -1) {
               return null;
            }

            if (var12 != 1) {
               break;
            }

            if (var11++ >= entity.getMaxFallHeight()) {
               return null;
            }

            if (--integer3 <= 0) {
               return null;
            }

            var9 = this.openPoint(integer2, integer3, integer4);
         }

         if (var12 == -2) {
            return null;
         }
      }

      return var9;
   }

   private int getVerticalOffset(Entity entity, int integer2, int integer3, int integer4) {
      return func_176170_a(
         this.blockaccess,
         entity,
         integer2,
         integer3,
         integer4,
         this.entitySizeX,
         this.entitySizeY,
         this.entitySizeZ,
         this.avoidsWater,
         this.canBreakDoors,
         this.canEnterDoors
      );
   }

   public static int func_176170_a(
      IBlockAccess iBlockAccess,
      Entity entity,
      int integer3,
      int integer4,
      int integer5,
      int integer6,
      int integer7,
      int integer8,
      boolean boolean9,
      boolean boolean10,
      boolean boolean11
   ) {
      boolean var14 = false;
      BlockPos var15 = new BlockPos(entity);
      BlockPos.MutableBlockPos var16 = new BlockPos.MutableBlockPos();

      for (int var17 = integer3; var17 < integer3 + integer6; var17++) {
         for (int var18 = integer4; var18 < integer4 + integer7; var18++) {
            for (int var19 = integer5; var19 < integer5 + integer8; var19++) {
               var16.set(var17, var18, var19);
               Block var20 = iBlockAccess.getBlockState(var16).getBlock();
               if (var20.getMaterial() != Material.air) {
                  if (var20 == Blocks.trapdoor || var20 == Blocks.iron_trapdoor) {
                     var14 = true;
                  } else if (var20 != Blocks.flowing_water && var20 != Blocks.water) {
                     if (!boolean11 && var20 instanceof BlockDoor && var20.getMaterial() == Material.wood) {
                        return 0;
                     }
                  } else {
                     if (boolean9) {
                        return -1;
                     }

                     var14 = true;
                  }

                  if (entity.worldObj.getBlockState(var16).getBlock() instanceof BlockRailBase) {
                     if (!(entity.worldObj.getBlockState(var15).getBlock() instanceof BlockRailBase)
                        && !(entity.worldObj.getBlockState(var15.down()).getBlock() instanceof BlockRailBase)) {
                        return -3;
                     }
                  } else if (!var20.isPassable(iBlockAccess, var16) && (!boolean10 || !(var20 instanceof BlockDoor) || var20.getMaterial() != Material.wood)) {
                     if (var20 instanceof BlockFence || var20 instanceof BlockFenceGate || var20 instanceof BlockWall) {
                        return -3;
                     }

                     if (var20 == Blocks.trapdoor || var20 == Blocks.iron_trapdoor) {
                        return -4;
                     }

                     Material var21 = var20.getMaterial();
                     if (var21 != Material.lava) {
                        return 0;
                     }

                     if (!entity.isInLava()) {
                        return -2;
                     }
                  }
               }
            }
         }
      }

      return var14 ? 2 : 1;
   }

   public void setEnterDoors(boolean boolean1) {
      this.canEnterDoors = boolean1;
   }

   public void setBreakDoors(boolean boolean1) {
      this.canBreakDoors = boolean1;
   }

   public void setAvoidsWater(boolean boolean1) {
      this.avoidsWater = boolean1;
   }

   public void setCanSwim(boolean boolean1) {
      this.canSwim = boolean1;
   }

   public boolean getEnterDoors() {
      return this.canEnterDoors;
   }

   public boolean getCanSwim() {
      return this.canSwim;
   }

   public boolean getAvoidsWater() {
      return this.avoidsWater;
   }
}
