package net.minecraft.client.renderer.culling;

import net.minecraft.util.AxisAlignedBB;

public interface ICamera {
   boolean isBoundingBoxInFrustum(AxisAlignedBB axisAlignedBB);

   void setPosition(double double1, double double2, double double3);
}
