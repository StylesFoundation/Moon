package net.optifine.shaders;

import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.util.MathHelper;

public class ClippingHelperShadow extends ClippingHelper {
   private static final ClippingHelperShadow instance = new ClippingHelperShadow();
   float[] frustumTest = new float[6];
   float[][] shadowClipPlanes = new float[10][4];
   int shadowClipPlaneCount;
   float[] matInvMP = new float[16];
   float[] vecIntersection = new float[4];

   @Override
   public boolean isBoxInFrustum(double double1, double double2, double double3, double double4, double double5, double double6) {
      for (int var16 = 0; var16 < this.shadowClipPlaneCount; var16++) {
         float[] var17 = this.shadowClipPlanes[var16];
         if (this.dot4(var17, double1, double2, double3) <= 0.0
            && this.dot4(var17, double4, double2, double3) <= 0.0
            && this.dot4(var17, double1, double5, double3) <= 0.0
            && this.dot4(var17, double4, double5, double3) <= 0.0
            && this.dot4(var17, double1, double2, double6) <= 0.0
            && this.dot4(var17, double4, double2, double6) <= 0.0
            && this.dot4(var17, double1, double5, double6) <= 0.0
            && this.dot4(var17, double4, double5, double6) <= 0.0) {
            return false;
         }
      }

      return true;
   }

   private double dot4(float[] arr, double double2, double double3, double double4) {
      return arr[0] * double2 + arr[1] * double3 + arr[2] * double4 + arr[3];
   }

   private double dot3(float[] arr1, float[] arr2) {
      return (double)arr1[0] * arr2[0] + (double)arr1[1] * arr2[1] + (double)arr1[2] * arr2[2];
   }

   public static ClippingHelper getInstance() {
      instance.init();
      return instance;
   }

   private void normalizePlane(float[] arr) {
      float var2 = MathHelper.sqrt_float(arr[0] * arr[0] + arr[1] * arr[1] + arr[2] * arr[2]);
      arr[0] /= var2;
      arr[1] /= var2;
      arr[2] /= var2;
      arr[3] /= var2;
   }

   private void normalize3(float[] arr) {
      float var5 = MathHelper.sqrt_float(arr[0] * arr[0] + arr[1] * arr[1] + arr[2] * arr[2]);
      if (var5 == 0.0F) {
         var5 = 1.0F;
      }

      arr[0] /= var5;
      arr[1] /= var5;
      arr[2] /= var5;
   }

   private void assignPlane(float[] arr, float float2, float float3, float float4, float float5) {
      float var6 = (float)Math.sqrt(float2 * float2 + float3 * float3 + float4 * float4);
      arr[0] = float2 / var6;
      arr[1] = float3 / var6;
      arr[2] = float4 / var6;
      arr[3] = float5 / var6;
   }

   private void copyPlane(float[] arr1, float[] arr2) {
      arr1[0] = arr2[0];
      arr1[1] = arr2[1];
      arr1[2] = arr2[2];
      arr1[3] = arr2[3];
   }

   private void cross3(float[] arr1, float[] arr2, float[] arr3) {
      arr1[0] = arr2[1] * arr3[2] - arr2[2] * arr3[1];
      arr1[1] = arr2[2] * arr3[0] - arr2[0] * arr3[2];
      arr1[2] = arr2[0] * arr3[1] - arr2[1] * arr3[0];
   }

   private void addShadowClipPlane(float[] arr) {
      this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], arr);
   }

   private float length(float float1, float float2, float float3) {
      return (float)Math.sqrt(float1 * float1 + float2 * float2 + float3 * float3);
   }

   private float distance(float float1, float float2, float float3, float float4, float float5, float float6) {
      return this.length(float1 - float4, float2 - float5, float3 - float6);
   }

   private void makeShadowPlane(float[] arr1, float[] arr2, float[] arr3, float[] arr4) {
      this.cross3(this.vecIntersection, arr2, arr3);
      this.cross3(arr1, this.vecIntersection, arr4);
      this.normalize3(arr1);
      float var5 = (float)this.dot3(arr2, arr3);
      float var6 = (float)this.dot3(arr1, arr3);
      float var7 = this.distance(arr1[0], arr1[1], arr1[2], arr3[0] * var6, arr3[1] * var6, arr3[2] * var6);
      float var8 = this.distance(arr2[0], arr2[1], arr2[2], arr3[0] * var5, arr3[1] * var5, arr3[2] * var5);
      float var9 = var7 / var8;
      float var10 = (float)this.dot3(arr1, arr2);
      float var11 = this.distance(arr1[0], arr1[1], arr1[2], arr2[0] * var10, arr2[1] * var10, arr2[2] * var10);
      float var12 = this.distance(arr3[0], arr3[1], arr3[2], arr2[0] * var5, arr2[1] * var5, arr2[2] * var5);
      float var13 = var11 / var12;
      arr1[3] = arr2[3] * var9 + arr3[3] * var13;
   }

   public void init() {
      float[] var4 = this.projectionMatrix;
      float[] var5 = this.modelviewMatrix;
      float[] var6 = this.clippingMatrix;
      System.arraycopy(Shaders.faProjection, 0, var4, 0, 16);
      System.arraycopy(Shaders.faModelView, 0, var5, 0, 16);
      SMath.multiplyMat4xMat4(var6, var5, var4);
      this.assignPlane(this.frustum[0], var6[3] - var6[0], var6[7] - var6[4], var6[11] - var6[8], var6[15] - var6[12]);
      this.assignPlane(this.frustum[1], var6[3] + var6[0], var6[7] + var6[4], var6[11] + var6[8], var6[15] + var6[12]);
      this.assignPlane(this.frustum[2], var6[3] + var6[1], var6[7] + var6[5], var6[11] + var6[9], var6[15] + var6[13]);
      this.assignPlane(this.frustum[3], var6[3] - var6[1], var6[7] - var6[5], var6[11] - var6[9], var6[15] - var6[13]);
      this.assignPlane(this.frustum[4], var6[3] - var6[2], var6[7] - var6[6], var6[11] - var6[10], var6[15] - var6[14]);
      this.assignPlane(this.frustum[5], var6[3] + var6[2], var6[7] + var6[6], var6[11] + var6[10], var6[15] + var6[14]);
      float[] var7 = Shaders.shadowLightPositionVector;
      float var8 = (float)this.dot3(this.frustum[0], var7);
      float var9 = (float)this.dot3(this.frustum[1], var7);
      float var10 = (float)this.dot3(this.frustum[2], var7);
      float var11 = (float)this.dot3(this.frustum[3], var7);
      float var12 = (float)this.dot3(this.frustum[4], var7);
      float var13 = (float)this.dot3(this.frustum[5], var7);
      this.shadowClipPlaneCount = 0;
      if (var8 >= 0.0F) {
         this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0]);
         if (var8 > 0.0F) {
            if (var10 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[2], var7);
            }

            if (var11 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[3], var7);
            }

            if (var12 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[4], var7);
            }

            if (var13 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[5], var7);
            }
         }
      }

      if (var9 >= 0.0F) {
         this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1]);
         if (var9 > 0.0F) {
            if (var10 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[2], var7);
            }

            if (var11 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[3], var7);
            }

            if (var12 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[4], var7);
            }

            if (var13 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[5], var7);
            }
         }
      }

      if (var10 >= 0.0F) {
         this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2]);
         if (var10 > 0.0F) {
            if (var8 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[0], var7);
            }

            if (var9 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[1], var7);
            }

            if (var12 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[4], var7);
            }

            if (var13 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[5], var7);
            }
         }
      }

      if (var11 >= 0.0F) {
         this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3]);
         if (var11 > 0.0F) {
            if (var8 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[0], var7);
            }

            if (var9 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[1], var7);
            }

            if (var12 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[4], var7);
            }

            if (var13 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[5], var7);
            }
         }
      }

      if (var12 >= 0.0F) {
         this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4]);
         if (var12 > 0.0F) {
            if (var8 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[0], var7);
            }

            if (var9 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[1], var7);
            }

            if (var10 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[2], var7);
            }

            if (var11 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[3], var7);
            }
         }
      }

      if (var13 >= 0.0F) {
         this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5]);
         if (var13 > 0.0F) {
            if (var8 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[0], var7);
            }

            if (var9 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[1], var7);
            }

            if (var10 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[2], var7);
            }

            if (var11 < 0.0F) {
               this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[3], var7);
            }
         }
      }
   }
}
