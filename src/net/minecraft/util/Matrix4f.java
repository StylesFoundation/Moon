package net.minecraft.util;

public class Matrix4f extends org.lwjgl.util.vector.Matrix4f {
   public Matrix4f(float[] arr) {
      this.m00 = arr[0];
      this.m01 = arr[1];
      this.m02 = arr[2];
      this.m03 = arr[3];
      this.m10 = arr[4];
      this.m11 = arr[5];
      this.m12 = arr[6];
      this.m13 = arr[7];
      this.m20 = arr[8];
      this.m21 = arr[9];
      this.m22 = arr[10];
      this.m23 = arr[11];
      this.m30 = arr[12];
      this.m31 = arr[13];
      this.m32 = arr[14];
      this.m33 = arr[15];
   }

   public Matrix4f() {
      this.m00 = this.m01 = this.m02 = this.m03 = this.m10 = this.m11 = this.m12 = this.m13 = this.m20 = this.m21 = this.m22 = this.m23 = this.m30 = this.m31 = this.m32 = this.m33 = 0.0F;
   }
}
