package net.optifine.shaders;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class SMath {
   static void multiplyMat4xMat4(float[] arr1, float[] arr2, float[] arr3) {
      for (int var6 = 0; var6 < 4; var6++) {
         for (int var7 = 0; var7 < 4; var7++) {
            arr1[4 * var6 + var7] = arr2[4 * var6] * arr3[var7]
               + arr2[4 * var6 + 1] * arr3[4 + var7]
               + arr2[4 * var6 + 2] * arr3[8 + var7]
               + arr2[4 * var6 + 3] * arr3[12 + var7];
         }
      }
   }

   static void multiplyMat4xVec4(float[] arr1, float[] arr2, float[] arr3) {
      arr1[0] = arr2[0] * arr3[0] + arr2[4] * arr3[1] + arr2[8] * arr3[2] + arr2[12] * arr3[3];
      arr1[1] = arr2[1] * arr3[0] + arr2[5] * arr3[1] + arr2[9] * arr3[2] + arr2[13] * arr3[3];
      arr1[2] = arr2[2] * arr3[0] + arr2[6] * arr3[1] + arr2[10] * arr3[2] + arr2[14] * arr3[3];
      arr1[3] = arr2[3] * arr3[0] + arr2[7] * arr3[1] + arr2[11] * arr3[2] + arr2[15] * arr3[3];
   }

   static void invertMat4(float[] arr1, float[] arr2) {
      arr1[0] = arr2[5] * arr2[10] * arr2[15]
         - arr2[5] * arr2[11] * arr2[14]
         - arr2[9] * arr2[6] * arr2[15]
         + arr2[9] * arr2[7] * arr2[14]
         + arr2[13] * arr2[6] * arr2[11]
         - arr2[13] * arr2[7] * arr2[10];
      arr1[1] = -arr2[1] * arr2[10] * arr2[15]
         + arr2[1] * arr2[11] * arr2[14]
         + arr2[9] * arr2[2] * arr2[15]
         - arr2[9] * arr2[3] * arr2[14]
         - arr2[13] * arr2[2] * arr2[11]
         + arr2[13] * arr2[3] * arr2[10];
      arr1[2] = arr2[1] * arr2[6] * arr2[15]
         - arr2[1] * arr2[7] * arr2[14]
         - arr2[5] * arr2[2] * arr2[15]
         + arr2[5] * arr2[3] * arr2[14]
         + arr2[13] * arr2[2] * arr2[7]
         - arr2[13] * arr2[3] * arr2[6];
      arr1[3] = -arr2[1] * arr2[6] * arr2[11]
         + arr2[1] * arr2[7] * arr2[10]
         + arr2[5] * arr2[2] * arr2[11]
         - arr2[5] * arr2[3] * arr2[10]
         - arr2[9] * arr2[2] * arr2[7]
         + arr2[9] * arr2[3] * arr2[6];
      arr1[4] = -arr2[4] * arr2[10] * arr2[15]
         + arr2[4] * arr2[11] * arr2[14]
         + arr2[8] * arr2[6] * arr2[15]
         - arr2[8] * arr2[7] * arr2[14]
         - arr2[12] * arr2[6] * arr2[11]
         + arr2[12] * arr2[7] * arr2[10];
      arr1[5] = arr2[0] * arr2[10] * arr2[15]
         - arr2[0] * arr2[11] * arr2[14]
         - arr2[8] * arr2[2] * arr2[15]
         + arr2[8] * arr2[3] * arr2[14]
         + arr2[12] * arr2[2] * arr2[11]
         - arr2[12] * arr2[3] * arr2[10];
      arr1[6] = -arr2[0] * arr2[6] * arr2[15]
         + arr2[0] * arr2[7] * arr2[14]
         + arr2[4] * arr2[2] * arr2[15]
         - arr2[4] * arr2[3] * arr2[14]
         - arr2[12] * arr2[2] * arr2[7]
         + arr2[12] * arr2[3] * arr2[6];
      arr1[7] = arr2[0] * arr2[6] * arr2[11]
         - arr2[0] * arr2[7] * arr2[10]
         - arr2[4] * arr2[2] * arr2[11]
         + arr2[4] * arr2[3] * arr2[10]
         + arr2[8] * arr2[2] * arr2[7]
         - arr2[8] * arr2[3] * arr2[6];
      arr1[8] = arr2[4] * arr2[9] * arr2[15]
         - arr2[4] * arr2[11] * arr2[13]
         - arr2[8] * arr2[5] * arr2[15]
         + arr2[8] * arr2[7] * arr2[13]
         + arr2[12] * arr2[5] * arr2[11]
         - arr2[12] * arr2[7] * arr2[9];
      arr1[9] = -arr2[0] * arr2[9] * arr2[15]
         + arr2[0] * arr2[11] * arr2[13]
         + arr2[8] * arr2[1] * arr2[15]
         - arr2[8] * arr2[3] * arr2[13]
         - arr2[12] * arr2[1] * arr2[11]
         + arr2[12] * arr2[3] * arr2[9];
      arr1[10] = arr2[0] * arr2[5] * arr2[15]
         - arr2[0] * arr2[7] * arr2[13]
         - arr2[4] * arr2[1] * arr2[15]
         + arr2[4] * arr2[3] * arr2[13]
         + arr2[12] * arr2[1] * arr2[7]
         - arr2[12] * arr2[3] * arr2[5];
      arr1[11] = -arr2[0] * arr2[5] * arr2[11]
         + arr2[0] * arr2[7] * arr2[9]
         + arr2[4] * arr2[1] * arr2[11]
         - arr2[4] * arr2[3] * arr2[9]
         - arr2[8] * arr2[1] * arr2[7]
         + arr2[8] * arr2[3] * arr2[5];
      arr1[12] = -arr2[4] * arr2[9] * arr2[14]
         + arr2[4] * arr2[10] * arr2[13]
         + arr2[8] * arr2[5] * arr2[14]
         - arr2[8] * arr2[6] * arr2[13]
         - arr2[12] * arr2[5] * arr2[10]
         + arr2[12] * arr2[6] * arr2[9];
      arr1[13] = arr2[0] * arr2[9] * arr2[14]
         - arr2[0] * arr2[10] * arr2[13]
         - arr2[8] * arr2[1] * arr2[14]
         + arr2[8] * arr2[2] * arr2[13]
         + arr2[12] * arr2[1] * arr2[10]
         - arr2[12] * arr2[2] * arr2[9];
      arr1[14] = -arr2[0] * arr2[5] * arr2[14]
         + arr2[0] * arr2[6] * arr2[13]
         + arr2[4] * arr2[1] * arr2[14]
         - arr2[4] * arr2[2] * arr2[13]
         - arr2[12] * arr2[1] * arr2[6]
         + arr2[12] * arr2[2] * arr2[5];
      arr1[15] = arr2[0] * arr2[5] * arr2[10]
         - arr2[0] * arr2[6] * arr2[9]
         - arr2[4] * arr2[1] * arr2[10]
         + arr2[4] * arr2[2] * arr2[9]
         + arr2[8] * arr2[1] * arr2[6]
         - arr2[8] * arr2[2] * arr2[5];
      float var5 = arr2[0] * arr1[0] + arr2[1] * arr1[4] + arr2[2] * arr1[8] + arr2[3] * arr1[12];
      if (var5 != 0.0) {
         for (int var6 = 0; var6 < 16; var6++) {
            arr1[var6] /= var5;
         }
      } else {
         Arrays.fill(arr1, 0.0F);
      }
   }

   static void invertMat4FBFA(FloatBuffer floatBuffer1, FloatBuffer floatBuffer2, float[] arr3, float[] arr4) {
      floatBuffer2.get(arr4);
      invertMat4(arr3, arr4);
      floatBuffer1.put(arr3);
   }
}
