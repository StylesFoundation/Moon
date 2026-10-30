package com.moon.util.render;

import java.nio.FloatBuffer;
import org.lwjgl.util.vector.Matrix4f;

public class MatrixUtils {
   public static Matrix4f createOrthographicMatrix(float left, float right, float bottom, float top, float near, float far) {
      Matrix4f matrix = new Matrix4f();
      float tx = -(right + left) / (right - left);
      float ty = -(top + bottom) / (top - bottom);
      float tz = -(far + near) / (far - near);
      matrix.m00 = 2.0F / (right - left);
      matrix.m11 = 2.0F / (top - bottom);
      matrix.m22 = -2.0F / (far - near);
      matrix.m03 = tx;
      matrix.m13 = ty;
      matrix.m23 = tz;
      return matrix;
   }

   public static Matrix4f createScaleMatrix(float x, float y, float z) {
      Matrix4f matrix = new Matrix4f();
      matrix.m00 = x;
      matrix.m11 = y;
      matrix.m22 = z;
      return matrix;
   }

   public static void writeMatrix(Matrix4f matrix, FloatBuffer buffer) {
      buffer.put(matrix.m00).put(matrix.m10).put(matrix.m20).put(matrix.m30);
      buffer.put(matrix.m01).put(matrix.m11).put(matrix.m21).put(matrix.m31);
      buffer.put(matrix.m02).put(matrix.m12).put(matrix.m22).put(matrix.m32);
      buffer.put(matrix.m03).put(matrix.m13).put(matrix.m23).put(matrix.m33);
      buffer.flip();
   }

   public static Matrix4f readMatrix(FloatBuffer buffer) {
      Matrix4f matrix = new Matrix4f();
      matrix.load(buffer.duplicate());
      return matrix;
   }
}
