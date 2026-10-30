package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.List;

public class AxisAlignedBB {
   public static final AxisAlignedBB a = new AxisAlignedBB(
      Double.NEGATIVE_INFINITY,
      Double.NEGATIVE_INFINITY,
      Double.NEGATIVE_INFINITY,
      Double.POSITIVE_INFINITY,
      Double.POSITIVE_INFINITY,
      Double.POSITIVE_INFINITY
   );
   public final double minX;
   public final double minY;
   public final double minZ;
   public final double maxX;
   public final double maxY;
   public final double maxZ;

   public AxisAlignedBB(double double1, double double2, double double3) {
      this.minX = this.maxX = double1;
      this.minY = this.maxY = double2;
      this.minZ = this.maxZ = double3;
   }

   public AxisAlignedBB(double double1, double double2, double double3, double double4, double double5, double double6) {
      this.minX = Math.min(double1, double4);
      this.minY = Math.min(double2, double5);
      this.minZ = Math.min(double3, double6);
      this.maxX = Math.max(double1, double4);
      this.maxY = Math.max(double2, double5);
      this.maxZ = Math.max(double3, double6);
   }

   public AxisAlignedBB(BlockPos blockPos1, BlockPos blockPos2) {
      this.minX = blockPos1.getX();
      this.minY = blockPos1.getY();
      this.minZ = blockPos1.getZ();
      this.maxX = blockPos2.getX();
      this.maxY = blockPos2.getY();
      this.maxZ = blockPos2.getZ();
   }

   public static AxisAlignedBB fromBounds(double double1, double double2, double double3, double double4, double double5, double double6) {
      double var12 = Math.min(double1, double4);
      double var14 = Math.min(double2, double5);
      double var16 = Math.min(double3, double6);
      double var18 = Math.max(double1, double4);
      double var20 = Math.max(double2, double5);
      double var22 = Math.max(double3, double6);
      return new AxisAlignedBB(var12, var14, var16, var18, var20, var22);
   }

   public AxisAlignedBB addCoord(double double1, double double2, double double3) {
      double var10 = this.minX;
      double var12 = this.minY;
      double var14 = this.minZ;
      double var16 = this.maxX;
      double var18 = this.maxY;
      double var20 = this.maxZ;
      if (double1 < 0.0) {
         var10 += double1;
      } else if (double1 > 0.0) {
         var16 += double1;
      }

      if (double2 < 0.0) {
         var12 += double2;
      } else if (double2 > 0.0) {
         var18 += double2;
      }

      if (double3 < 0.0) {
         var14 += double3;
      } else if (double3 > 0.0) {
         var20 += double3;
      }

      return new AxisAlignedBB(var10, var12, var14, var16, var18, var20);
   }

   public AxisAlignedBB expand(double double1, double double2, double double3) {
      double var7 = this.minX - double1;
      double var9 = this.minY - double2;
      double var11 = this.minZ - double3;
      double var13 = this.maxX + double1;
      double var15 = this.maxY + double2;
      double var17 = this.maxZ + double3;
      return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
   }

   public AxisAlignedBB union(AxisAlignedBB axisAlignedBB) {
      double var2 = Math.min(this.minX, axisAlignedBB.minX);
      double var4 = Math.min(this.minY, axisAlignedBB.minY);
      double var6 = Math.min(this.minZ, axisAlignedBB.minZ);
      double var8 = Math.max(this.maxX, axisAlignedBB.maxX);
      double var10 = Math.max(this.maxY, axisAlignedBB.maxY);
      double var12 = Math.max(this.maxZ, axisAlignedBB.maxZ);
      return new AxisAlignedBB(var2, var4, var6, var8, var10, var12);
   }

   public AxisAlignedBB offset(double double1, double double2, double double3) {
      return new AxisAlignedBB(this.minX + double1, this.minY + double2, this.minZ + double3, this.maxX + double1, this.maxY + double2, this.maxZ + double3);
   }

   public double calculateXOffset(AxisAlignedBB axisAlignedBB, double double2) {
      if (axisAlignedBB.maxY > this.minY && axisAlignedBB.minY < this.maxY && axisAlignedBB.maxZ > this.minZ && axisAlignedBB.minZ < this.maxZ) {
         if (double2 > 0.0 && axisAlignedBB.maxX <= this.minX) {
            double var9 = this.minX - axisAlignedBB.maxX;
            if (var9 < double2) {
               double2 = var9;
            }
         } else if (double2 < 0.0 && axisAlignedBB.minX >= this.maxX) {
            double var7 = this.maxX - axisAlignedBB.minX;
            if (var7 > double2) {
               double2 = var7;
            }
         }
      }

      return double2;
   }

   public double calculateYOffset(AxisAlignedBB axisAlignedBB, double double2) {
      if (axisAlignedBB.maxX > this.minX && axisAlignedBB.minX < this.maxX && axisAlignedBB.maxZ > this.minZ && axisAlignedBB.minZ < this.maxZ) {
         if (double2 > 0.0 && axisAlignedBB.maxY <= this.minY) {
            double var9 = this.minY - axisAlignedBB.maxY;
            if (var9 < double2) {
               double2 = var9;
            }
         } else if (double2 < 0.0 && axisAlignedBB.minY >= this.maxY) {
            double var7 = this.maxY - axisAlignedBB.minY;
            if (var7 > double2) {
               double2 = var7;
            }
         }
      }

      return double2;
   }

   public double calculateZOffset(AxisAlignedBB axisAlignedBB, double double2) {
      if (axisAlignedBB.maxX > this.minX && axisAlignedBB.minX < this.maxX && axisAlignedBB.maxY > this.minY && axisAlignedBB.minY < this.maxY) {
         if (double2 > 0.0 && axisAlignedBB.maxZ <= this.minZ) {
            double var9 = this.minZ - axisAlignedBB.maxZ;
            if (var9 < double2) {
               double2 = var9;
            }
         } else if (double2 < 0.0 && axisAlignedBB.minZ >= this.maxZ) {
            double var7 = this.maxZ - axisAlignedBB.minZ;
            if (var7 > double2) {
               double2 = var7;
            }
         }
      }

      return double2;
   }

   public boolean intersectsWith(AxisAlignedBB axisAlignedBB) {
      return axisAlignedBB.maxX > this.minX
         && axisAlignedBB.minX < this.maxX
         && axisAlignedBB.maxY > this.minY
         && axisAlignedBB.minY < this.maxY
         && axisAlignedBB.maxZ > this.minZ
         && axisAlignedBB.minZ < this.maxZ;
   }

   public boolean isVecInside(Vec3 vec3) {
      return vec3.xCoord > this.minX
         && vec3.xCoord < this.maxX
         && vec3.yCoord > this.minY
         && vec3.yCoord < this.maxY
         && vec3.zCoord > this.minZ
         && vec3.zCoord < this.maxZ;
   }

   public double getAverageEdgeLength() {
      double var1 = this.maxX - this.minX;
      double var3 = this.maxY - this.minY;
      double var5 = this.maxZ - this.minZ;
      return (var1 + var3 + var5) / 3.0;
   }

   public AxisAlignedBB contract(double double1, double double2, double double3) {
      double var7 = this.minX + double1;
      double var9 = this.minY + double2;
      double var11 = this.minZ + double3;
      double var13 = this.maxX - double1;
      double var15 = this.maxY - double2;
      double var17 = this.maxZ - double3;
      return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
   }

   public MovingObjectPosition calculateIntercept(Vec3 vec31, Vec3 vec32) {
      Vec3 var6 = vec31.getIntermediateWithXValue(vec32, this.minX);
      Vec3 var7 = vec31.getIntermediateWithXValue(vec32, this.maxX);
      Vec3 var8 = vec31.getIntermediateWithYValue(vec32, this.minY);
      Vec3 var9 = vec31.getIntermediateWithYValue(vec32, this.maxY);
      Vec3 var10 = vec31.getIntermediateWithZValue(vec32, this.minZ);
      Vec3 var11 = vec31.getIntermediateWithZValue(vec32, this.maxZ);
      if (!this.isVecInYZ(var6)) {
         var6 = null;
      }

      if (!this.isVecInYZ(var7)) {
         var7 = null;
      }

      if (!this.isVecInXZ(var8)) {
         var8 = null;
      }

      if (!this.isVecInXZ(var9)) {
         var9 = null;
      }

      if (!this.isVecInXY(var10)) {
         var10 = null;
      }

      if (!this.isVecInXY(var11)) {
         var11 = null;
      }

      Vec3 var12 = null;
      if (var6 != null) {
         var12 = var6;
      }

      if (var7 != null && (var12 == null || vec31.squareDistanceTo(var7) < vec31.squareDistanceTo(var12))) {
         var12 = var7;
      }

      if (var8 != null && (var12 == null || vec31.squareDistanceTo(var8) < vec31.squareDistanceTo(var12))) {
         var12 = var8;
      }

      if (var9 != null && (var12 == null || vec31.squareDistanceTo(var9) < vec31.squareDistanceTo(var12))) {
         var12 = var9;
      }

      if (var10 != null && (var12 == null || vec31.squareDistanceTo(var10) < vec31.squareDistanceTo(var12))) {
         var12 = var10;
      }

      if (var11 != null && (var12 == null || vec31.squareDistanceTo(var11) < vec31.squareDistanceTo(var12))) {
         var12 = var11;
      }

      if (var12 == null) {
         return null;
      }

      EnumFacing var13;
      if (var12 == var6) {
         var13 = EnumFacing.WEST;
      } else if (var12 == var7) {
         var13 = EnumFacing.EAST;
      } else if (var12 == var8) {
         var13 = EnumFacing.DOWN;
      } else if (var12 == var9) {
         var13 = EnumFacing.UP;
      } else if (var12 == var10) {
         var13 = EnumFacing.NORTH;
      } else {
         var13 = EnumFacing.SOUTH;
      }

      return new MovingObjectPosition(var12, var13);
   }

   private boolean isVecInYZ(Vec3 vec3) {
      return vec3 != null && vec3.yCoord >= this.minY && vec3.yCoord <= this.maxY && vec3.zCoord >= this.minZ && vec3.zCoord <= this.maxZ;
   }

   private boolean isVecInXZ(Vec3 vec3) {
      return vec3 != null && vec3.xCoord >= this.minX && vec3.xCoord <= this.maxX && vec3.zCoord >= this.minZ && vec3.zCoord <= this.maxZ;
   }

   private boolean isVecInXY(Vec3 vec3) {
      return vec3 != null && vec3.xCoord >= this.minX && vec3.xCoord <= this.maxX && vec3.yCoord >= this.minY && vec3.yCoord <= this.maxY;
   }

   @Override
   public String toString() {
      return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
   }

   public boolean hasNaN() {
      return Double.isNaN(this.minX)
         || Double.isNaN(this.minY)
         || Double.isNaN(this.minZ)
         || Double.isNaN(this.maxX)
         || Double.isNaN(this.maxY)
         || Double.isNaN(this.maxZ);
   }

   public List<Vec3> Method908() {
      return Lists.newArrayList(
         new Vec3[]{
            new Vec3(this.minX, this.minY, this.minZ),
            new Vec3(this.minX, this.minY, this.maxZ),
            new Vec3(this.maxX, this.minY, this.minZ),
            new Vec3(this.maxX, this.minY, this.maxZ),
            new Vec3(this.minX, this.maxY, this.minZ),
            new Vec3(this.minX, this.maxY, this.maxZ),
            new Vec3(this.maxX, this.maxY, this.minZ),
            new Vec3(this.maxX, this.maxY, this.maxZ)
         }
      );
   }
}
