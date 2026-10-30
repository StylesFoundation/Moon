package net.minecraft.client.renderer.culling;

public class ClippingHelper {
   public float[][] frustum = new float[6][4];
   public float[] projectionMatrix = new float[16];
   public float[] modelviewMatrix = new float[16];
   public float[] clippingMatrix = new float[16];
   public boolean disabled = false;

   private float dot(float[] arr, float float2, float float3, float float4) {
      return arr[0] * float2 + arr[1] * float3 + arr[2] * float4 + arr[3];
   }

   public boolean isBoxInFrustum(double double1, double double2, double double3, double double4, double double5, double double6) {
      if (!this.disabled) {
         float var15 = (float)double1;
         float var16 = (float)double2;
         float var17 = (float)double3;
         float var18 = (float)double4;
         float var19 = (float)double5;
         float var20 = (float)double6;

         for (int var21 = 0; var21 < 6; var21++) {
            float[] var22 = this.frustum[var21];
            float var23 = var22[0];
            float var24 = var22[1];
            float var25 = var22[2];
            float var26 = var22[3];
            if (var23 * var15 + var24 * var16 + var25 * var17 + var26 <= 0.0F
               && var23 * var18 + var24 * var16 + var25 * var17 + var26 <= 0.0F
               && var23 * var15 + var24 * var19 + var25 * var17 + var26 <= 0.0F
               && var23 * var18 + var24 * var19 + var25 * var17 + var26 <= 0.0F
               && var23 * var15 + var24 * var16 + var25 * var20 + var26 <= 0.0F
               && var23 * var18 + var24 * var16 + var25 * var20 + var26 <= 0.0F
               && var23 * var15 + var24 * var19 + var25 * var20 + var26 <= 0.0F
               && var23 * var18 + var24 * var19 + var25 * var20 + var26 <= 0.0F) {
               return false;
            }
         }
      }

      return true;
   }

   public boolean isBoxInFrustumFully(double double1, double double2, double double3, double double4, double double5, double double6) {
      if (!this.disabled) {
         float var15 = (float)double1;
         float var16 = (float)double2;
         float var17 = (float)double3;
         float var18 = (float)double4;
         float var19 = (float)double5;
         float var20 = (float)double6;

         for (int var21 = 0; var21 < 6; var21++) {
            float[] var22 = this.frustum[var21];
            float var23 = var22[0];
            float var24 = var22[1];
            float var25 = var22[2];
            float var26 = var22[3];
            if (var21 < 4) {
               if (var23 * var15 + var24 * var16 + var25 * var17 + var26 <= 0.0F
                  || var23 * var18 + var24 * var16 + var25 * var17 + var26 <= 0.0F
                  || var23 * var15 + var24 * var19 + var25 * var17 + var26 <= 0.0F
                  || var23 * var18 + var24 * var19 + var25 * var17 + var26 <= 0.0F
                  || var23 * var15 + var24 * var16 + var25 * var20 + var26 <= 0.0F
                  || var23 * var18 + var24 * var16 + var25 * var20 + var26 <= 0.0F
                  || var23 * var15 + var24 * var19 + var25 * var20 + var26 <= 0.0F
                  || var23 * var18 + var24 * var19 + var25 * var20 + var26 <= 0.0F) {
                  return false;
               }
            } else if (var23 * var15 + var24 * var16 + var25 * var17 + var26 <= 0.0F
               && var23 * var18 + var24 * var16 + var25 * var17 + var26 <= 0.0F
               && var23 * var15 + var24 * var19 + var25 * var17 + var26 <= 0.0F
               && var23 * var18 + var24 * var19 + var25 * var17 + var26 <= 0.0F
               && var23 * var15 + var24 * var16 + var25 * var20 + var26 <= 0.0F
               && var23 * var18 + var24 * var16 + var25 * var20 + var26 <= 0.0F
               && var23 * var15 + var24 * var19 + var25 * var20 + var26 <= 0.0F
               && var23 * var18 + var24 * var19 + var25 * var20 + var26 <= 0.0F) {
               return false;
            }
         }
      }

      return true;
   }
}
