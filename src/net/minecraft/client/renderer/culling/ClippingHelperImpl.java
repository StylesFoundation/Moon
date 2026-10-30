package net.minecraft.client.renderer.culling;

import java.nio.FloatBuffer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

public class ClippingHelperImpl extends ClippingHelper {
   private static final ClippingHelperImpl instance = new ClippingHelperImpl();
   private final FloatBuffer projectionMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
   private final FloatBuffer modelviewMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
   private final FloatBuffer field_78564_h = GLAllocation.createDirectFloatBuffer(16);

   public static ClippingHelper getInstance() {
      instance.init();
      return instance;
   }

   private void normalize(float[] arr) {
      float var2 = MathHelper.sqrt_float(arr[0] * arr[0] + arr[1] * arr[1] + arr[2] * arr[2]);
      arr[0] /= var2;
      arr[1] /= var2;
      arr[2] /= var2;
      arr[3] /= var2;
   }

   public void init() {
      this.projectionMatrixBuffer.clear();
      this.modelviewMatrixBuffer.clear();
      this.field_78564_h.clear();
      GlStateManager.getFloat(2983, this.projectionMatrixBuffer);
      GlStateManager.getFloat(2982, this.modelviewMatrixBuffer);
      float[] var3 = this.projectionMatrix;
      float[] var4 = this.modelviewMatrix;
      this.projectionMatrixBuffer.flip().limit(16);
      this.projectionMatrixBuffer.get(var3);
      this.modelviewMatrixBuffer.flip().limit(16);
      this.modelviewMatrixBuffer.get(var4);
      this.clippingMatrix[0] = var4[0] * var3[0] + var4[1] * var3[4] + var4[2] * var3[8] + var4[3] * var3[12];
      this.clippingMatrix[1] = var4[0] * var3[1] + var4[1] * var3[5] + var4[2] * var3[9] + var4[3] * var3[13];
      this.clippingMatrix[2] = var4[0] * var3[2] + var4[1] * var3[6] + var4[2] * var3[10] + var4[3] * var3[14];
      this.clippingMatrix[3] = var4[0] * var3[3] + var4[1] * var3[7] + var4[2] * var3[11] + var4[3] * var3[15];
      this.clippingMatrix[4] = var4[4] * var3[0] + var4[5] * var3[4] + var4[6] * var3[8] + var4[7] * var3[12];
      this.clippingMatrix[5] = var4[4] * var3[1] + var4[5] * var3[5] + var4[6] * var3[9] + var4[7] * var3[13];
      this.clippingMatrix[6] = var4[4] * var3[2] + var4[5] * var3[6] + var4[6] * var3[10] + var4[7] * var3[14];
      this.clippingMatrix[7] = var4[4] * var3[3] + var4[5] * var3[7] + var4[6] * var3[11] + var4[7] * var3[15];
      this.clippingMatrix[8] = var4[8] * var3[0] + var4[9] * var3[4] + var4[10] * var3[8] + var4[11] * var3[12];
      this.clippingMatrix[9] = var4[8] * var3[1] + var4[9] * var3[5] + var4[10] * var3[9] + var4[11] * var3[13];
      this.clippingMatrix[10] = var4[8] * var3[2] + var4[9] * var3[6] + var4[10] * var3[10] + var4[11] * var3[14];
      this.clippingMatrix[11] = var4[8] * var3[3] + var4[9] * var3[7] + var4[10] * var3[11] + var4[11] * var3[15];
      this.clippingMatrix[12] = var4[12] * var3[0] + var4[13] * var3[4] + var4[14] * var3[8] + var4[15] * var3[12];
      this.clippingMatrix[13] = var4[12] * var3[1] + var4[13] * var3[5] + var4[14] * var3[9] + var4[15] * var3[13];
      this.clippingMatrix[14] = var4[12] * var3[2] + var4[13] * var3[6] + var4[14] * var3[10] + var4[15] * var3[14];
      this.clippingMatrix[15] = var4[12] * var3[3] + var4[13] * var3[7] + var4[14] * var3[11] + var4[15] * var3[15];
      float[] var5 = this.frustum[0];
      var5[0] = this.clippingMatrix[3] - this.clippingMatrix[0];
      var5[1] = this.clippingMatrix[7] - this.clippingMatrix[4];
      var5[2] = this.clippingMatrix[11] - this.clippingMatrix[8];
      var5[3] = this.clippingMatrix[15] - this.clippingMatrix[12];
      this.normalize(var5);
      float[] var6 = this.frustum[1];
      var6[0] = this.clippingMatrix[3] + this.clippingMatrix[0];
      var6[1] = this.clippingMatrix[7] + this.clippingMatrix[4];
      var6[2] = this.clippingMatrix[11] + this.clippingMatrix[8];
      var6[3] = this.clippingMatrix[15] + this.clippingMatrix[12];
      this.normalize(var6);
      float[] var7 = this.frustum[2];
      var7[0] = this.clippingMatrix[3] + this.clippingMatrix[1];
      var7[1] = this.clippingMatrix[7] + this.clippingMatrix[5];
      var7[2] = this.clippingMatrix[11] + this.clippingMatrix[9];
      var7[3] = this.clippingMatrix[15] + this.clippingMatrix[13];
      this.normalize(var7);
      float[] var8 = this.frustum[3];
      var8[0] = this.clippingMatrix[3] - this.clippingMatrix[1];
      var8[1] = this.clippingMatrix[7] - this.clippingMatrix[5];
      var8[2] = this.clippingMatrix[11] - this.clippingMatrix[9];
      var8[3] = this.clippingMatrix[15] - this.clippingMatrix[13];
      this.normalize(var8);
      float[] var9 = this.frustum[4];
      var9[0] = this.clippingMatrix[3] - this.clippingMatrix[2];
      var9[1] = this.clippingMatrix[7] - this.clippingMatrix[6];
      var9[2] = this.clippingMatrix[11] - this.clippingMatrix[10];
      var9[3] = this.clippingMatrix[15] - this.clippingMatrix[14];
      this.normalize(var9);
      float[] var10 = this.frustum[5];
      var10[0] = this.clippingMatrix[3] + this.clippingMatrix[2];
      var10[1] = this.clippingMatrix[7] + this.clippingMatrix[6];
      var10[2] = this.clippingMatrix[11] + this.clippingMatrix[10];
      var10[3] = this.clippingMatrix[15] + this.clippingMatrix[14];
      this.normalize(var10);
   }
}
