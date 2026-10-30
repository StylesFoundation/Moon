package net.minecraft.client.renderer.culling;

import net.minecraft.util.AxisAlignedBB;

public class Frustum implements ICamera {
   private final ClippingHelper clippingHelper;
   private double xPosition;
   private double yPosition;
   private double zPosition;

   public Frustum() {
      this(ClippingHelperImpl.getInstance());
   }

   public Frustum(ClippingHelper clippingHelper) {
      this.clippingHelper = clippingHelper;
   }

   @Override
   public void setPosition(double double1, double double2, double double3) {
      this.xPosition = double1;
      this.yPosition = double2;
      this.zPosition = double3;
   }

   public boolean isBoxInFrustum(double double1, double double2, double double3, double double4, double double5, double double6) {
      return this.clippingHelper
         .isBoxInFrustum(
            double1 - this.xPosition,
            double2 - this.yPosition,
            double3 - this.zPosition,
            double4 - this.xPosition,
            double5 - this.yPosition,
            double6 - this.zPosition
         );
   }

   @Override
   public boolean isBoundingBoxInFrustum(AxisAlignedBB axisAlignedBB) {
      return this.isBoxInFrustum(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
   }

   public boolean isBoxInFrustumFully(double double1, double double2, double double3, double double4, double double5, double double6) {
      return this.clippingHelper
         .isBoxInFrustumFully(
            double1 - this.xPosition,
            double2 - this.yPosition,
            double3 - this.zPosition,
            double4 - this.xPosition,
            double5 - this.yPosition,
            double6 - this.zPosition
         );
   }
}
