package net.optifine.util;

import net.minecraft.util.MathHelper;

public class MathUtils {
   private static final float[] ASIN_TABLE = new float[65536];

   public static float asin(float float1) {
      return ASIN_TABLE[(int)((float1 + 1.0F) * 32767.5) & 65535];
   }

   public static float acos(float float1) {
      return (float) (Math.PI / 2) - ASIN_TABLE[(int)((float1 + 1.0F) * 32767.5) & 65535];
   }

   public static int getAverage(int[] arr) {
      return arr.length == 0 ? 0 : getSum(arr) / arr.length;
   }

   public static int getSum(int[] arr) {
      if (arr.length == 0) {
         return 0;
      }

      int var4 = 0;

      for (int var8 : arr) {
         var4 += var8;
      }

      return var4;
   }

   public static int roundDownToPowerOfTwo(int integer) {
      int var4 = MathHelper.roundUpToPowerOfTwo(integer);
      return integer == var4 ? var4 : var4 / 2;
   }

   public static boolean equalsDelta(float float1, float float2, float float3) {
      return Math.abs(float1 - float2) <= float3;
   }

   public static float toDeg(float float1) {
      return float1 * 180.0F / MathHelper.PI;
   }

   public static float toRad(float float1) {
      return float1 / 180.0F * MathHelper.PI;
   }

   public static float roundToFloat(double double1) {
      return (float)(Math.round(double1 * 1.0E8) / 1.0E8);
   }

   static {
      for (int var13 = 0; var13 < 65536; var13++) {
         ASIN_TABLE[var13] = (float)Math.asin(var13 / 32767.5 - 1.0);
      }

      for (int var14 = -1; var14 < 2; var14++) {
         ASIN_TABLE[(int)((var14 + 1.0) * 32767.5) & 65535] = (float)Math.asin(var14);
      }
   }
}
