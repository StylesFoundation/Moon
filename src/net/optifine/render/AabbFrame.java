package net.optifine.render;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.util.AxisAlignedBB;

public class AabbFrame extends AxisAlignedBB {
   private int frameCount = -1;
   private boolean inFrustumFully = false;

   public AabbFrame(double double1, double double2, double double3, double double4, double double5, double double6) {
      super(double1, double2, double3, double4, double5, double6);
   }

   public boolean isBoundingBoxInFrustumFully(ICamera iCamera, int integer) {
      if (this.frameCount != integer) {
         this.inFrustumFully = iCamera instanceof Frustum
            && ((Frustum)iCamera).isBoxInFrustumFully(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
         this.frameCount = integer;
      }

      return this.inFrustumFully;
   }
}
