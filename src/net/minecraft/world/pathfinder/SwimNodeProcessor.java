package net.minecraft.world.pathfinder;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class SwimNodeProcessor extends NodeProcessor {
   @Override
   public void initProcessor(IBlockAccess iBlockAccess, Entity entity) {
      super.initProcessor(iBlockAccess, entity);
   }

   @Override
   public void postProcess() {
      super.postProcess();
   }

   @Override
   public PathPoint getPathPointTo(Entity entity) {
      return this.openPoint(
         MathHelper.floor_double(entity.getEntityBoundingBox().minX),
         MathHelper.floor_double(entity.getEntityBoundingBox().minY + 0.5),
         MathHelper.floor_double(entity.getEntityBoundingBox().minZ)
      );
   }

   @Override
   public PathPoint getPathPointToCoords(Entity entity, double double2, double double3, double double4) {
      return this.openPoint(
         MathHelper.floor_double(double2 - entity.width / 2.0F), MathHelper.floor_double(double3 + 0.5), MathHelper.floor_double(double4 - entity.width / 2.0F)
      );
   }

   @Override
   public int findPathOptions(PathPoint[] arr, Entity entity, PathPoint pathPoint3, PathPoint pathPoint4, float float5) {
      int var9 = 0;

      for (EnumFacing var13 : EnumFacing.values()) {
         PathPoint var14 = this.getSafePoint(
            entity, pathPoint3.xCoord + var13.getFrontOffsetX(), pathPoint3.yCoord + var13.getFrontOffsetY(), pathPoint3.zCoord + var13.getFrontOffsetZ()
         );
         if (var14 != null && !var14.visited && var14.distanceTo(pathPoint4) < float5) {
            arr[var9++] = var14;
         }
      }

      return var9;
   }

   private PathPoint getSafePoint(Entity entity, int integer2, int integer3, int integer4) {
      int var7 = this.func_176186_b(entity, integer2, integer3, integer4);
      return var7 == -1 ? this.openPoint(integer2, integer3, integer4) : null;
   }

   private int func_176186_b(Entity entity, int integer2, int integer3, int integer4) {
      BlockPos.MutableBlockPos var8 = new BlockPos.MutableBlockPos();

      for (int var9 = integer2; var9 < integer2 + this.entitySizeX; var9++) {
         for (int var10 = integer3; var10 < integer3 + this.entitySizeY; var10++) {
            for (int var11 = integer4; var11 < integer4 + this.entitySizeZ; var11++) {
               Block var12 = this.blockaccess.getBlockState(var8.set(var9, var10, var11)).getBlock();
               if (var12.getMaterial() != Material.water) {
                  return 0;
               }
            }
         }
      }

      return -1;
   }
}
