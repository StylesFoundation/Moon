package net.minecraft.util;

import java.util.Random;
import java.util.UUID;
import net.optifine.util.MathUtils;

public class MathHelper {
   public static final float SQRT_2 = sqrt_float(2.0F);
   public static final float PI = MathUtils.roundToFloat(Math.PI);
   public static final float PI2 = MathUtils.roundToFloat(Math.PI * 2);
   public static final float PId2 = MathUtils.roundToFloat(Math.PI / 2);
   private static final float radToIndex = MathUtils.roundToFloat(651.8986469044033);
   public static final float deg2Rad = MathUtils.roundToFloat(Math.PI / 180.0);
   private static final float[] SIN_TABLE_FAST = new float[4096];
   public static boolean fastMath = false;
   private static final float[] SIN_TABLE = new float[65536];
   private static final int[] multiplyDeBruijnBitPosition;
   private static final double field_181163_d;
   private static final double[] field_181164_e;
   private static final double[] field_181165_f;
   private static final int n;
   private static final int o;
   private static final int p;
   private static final float r;
   private static final float t;
   private static final float[] u;
   private static final float[] v;

   public static float sin(float float1) {
      return fastMath ? SIN_TABLE_FAST[(int)(float1 * radToIndex) & 4095] : SIN_TABLE[(int)(float1 * 10430.378F) & 65535];
   }

   public static float cos(float float1) {
      return fastMath ? SIN_TABLE_FAST[(int)(float1 * radToIndex + 1024.0F) & 4095] : SIN_TABLE[(int)(float1 * 10430.378F + 16384.0F) & 65535];
   }

   public static float sqrt_float(float float1) {
      return (float)Math.sqrt(float1);
   }

   public static float sqrt_double(double double1) {
      return (float)Math.sqrt(double1);
   }

   public static int floor_float(float float1) {
      int var4 = (int)float1;
      return float1 < var4 ? var4 - 1 : var4;
   }

   public static int truncateDoubleToInt(double double1) {
      return (int)(double1 + 1024.0) - 1024;
   }

   public static int floor_double(double double1) {
      int var5 = (int)double1;
      return double1 < var5 ? var5 - 1 : var5;
   }

   public static long floor_double_long(double double1) {
      long var4 = (long)double1;
      return double1 < var4 ? var4 - 1L : var4;
   }

   public static int func_154353_e(double double1) {
      return (int)(double1 >= 0.0 ? double1 : -double1 + 1.0);
   }

   public static float abs(float float1) {
      return float1 >= 0.0F ? float1 : -float1;
   }

   public static int abs_int(int integer) {
      return integer >= 0 ? integer : -integer;
   }

   public static int ceiling_float_int(float float1) {
      int var3 = (int)float1;
      return float1 > var3 ? var3 + 1 : var3;
   }

   public static int ceiling_double_int(double double1) {
      int var4 = (int)double1;
      return double1 > var4 ? var4 + 1 : var4;
   }

   public static int clamp_int(int integer1, int integer2, int integer3) {
      return integer1 < integer2 ? integer2 : Math.min(integer1, integer3);
   }

   public static float clamp_float(float float1, float float2, float float3) {
      return float1 < float2 ? float2 : Math.min(float1, float3);
   }

   public static double clamp_double(double double1, double double2, double double3) {
      return double1 < double2 ? double2 : Math.min(double1, double3);
   }

   public static double denormalizeClamp(double double1, double double2, double double3) {
      return double3 < 0.0 ? double1 : (double3 > 1.0 ? double2 : double1 + (double2 - double1) * double3);
   }

   public static double abs_max(double double1, double double2) {
      if (double1 < 0.0) {
         double1 = -double1;
      }

      if (double2 < 0.0) {
         double2 = -double2;
      }

      return Math.max(double1, double2);
   }

   public static int bucketInt(int integer1, int integer2) {
      return integer1 < 0 ? -((-integer1 - 1) / integer2) - 1 : integer1 / integer2;
   }

   public static int getRandomIntegerInRange(Random random, int integer2, int integer3) {
      return integer2 >= integer3 ? integer2 : random.nextInt(integer3 - integer2 + 1) + integer2;
   }

   public static float randomFloatClamp(Random random, float float2, float float3) {
      return float2 >= float3 ? float2 : random.nextFloat() * (float3 - float2) + float2;
   }

   public static double getRandomDoubleInRange(Random random, double double2, double double3) {
      return double2 >= double3 ? double2 : random.nextDouble() * (double3 - double2) + double2;
   }

   public static double average(long[] arr) {
      long var4 = 0L;

      for (long var9 : arr) {
         var4 += var9;
      }

      return (double)var4 / arr.length;
   }

   public static boolean epsilonEquals(float float1, float float2) {
      return abs(float2 - float1) < 1.0E-5F;
   }

   public static int normalizeAngle(int integer1, int integer2) {
      return (integer1 % integer2 + integer2) % integer2;
   }

   public static float wrapAngleTo180_float(float float1) {
      float1 %= 360.0F;
      if (float1 >= 180.0F) {
         float1 -= 360.0F;
      }

      if (float1 < -180.0F) {
         float1 += 360.0F;
      }

      return float1;
   }

   public static double wrapAngleTo180_double(double double1) {
      double1 %= 360.0;
      if (double1 >= 180.0) {
         double1 -= 360.0;
      }

      if (double1 < -180.0) {
         double1 += 360.0;
      }

      return double1;
   }

   public static int parseIntWithDefault(String string, int integer) {
      try {
         return Integer.parseInt(string);
      } catch (Throwable var2) {
         return integer;
      }
   }

   public static int parseIntWithDefaultAndMax(String string, int integer2, int integer3) {
      return Math.max(integer3, parseIntWithDefault(string, integer2));
   }

   public static double parseDoubleWithDefault(String string, double double2) {
      try {
         return Double.parseDouble(string);
      } catch (Throwable var3) {
         return double2;
      }
   }

   public static double parseDoubleWithDefaultAndMax(String string, double double2, double double3) {
      return Math.max(double3, parseDoubleWithDefault(string, double2));
   }

   public static int roundUpToPowerOfTwo(int integer) {
      int var3 = integer - 1;
      var3 |= var3 >> 1;
      var3 |= var3 >> 2;
      var3 |= var3 >> 4;
      var3 |= var3 >> 8;
      return (var3 | var3 >> 16) + 1;
   }

   private static boolean isPowerOfTwo(int integer) {
      return integer != 0 && (integer & integer - 1) == 0;
   }

   private static int calculateLogBaseTwoDeBruijn(int integer) {
      integer = isPowerOfTwo(integer) ? integer : roundUpToPowerOfTwo(integer);
      return multiplyDeBruijnBitPosition[(int)(integer * 125613361L >> 27) & 31];
   }

   public static int calculateLogBaseTwo(int integer) {
      return calculateLogBaseTwoDeBruijn(integer) - (isPowerOfTwo(integer) ? 0 : 1);
   }

   public static int roundUp(int integer1, int integer2) {
      if (integer2 == 0) {
         return 0;
      }

      if (integer1 == 0) {
         return integer2;
      }

      if (integer1 < 0) {
         integer2 *= -1;
      }

      int var5 = integer1 % integer2;
      return var5 == 0 ? integer1 : integer1 + integer2 - var5;
   }

   public static int func_180183_b(float float1, float float2, float float3) {
      return func_180181_b(floor_float(float1 * 255.0F), floor_float(float2 * 255.0F), floor_float(float3 * 255.0F));
   }

   public static int func_180181_b(int integer1, int integer2, int integer3) {
      return ((integer1 << 8) + integer2 << 8) + integer3;
   }

   public static int func_180188_d(int integer1, int integer2) {
      int var4 = (integer1 & 0xFF0000) >> 16;
      int var5 = (integer2 & 0xFF0000) >> 16;
      int var6 = (integer1 & 0xFF00) >> 8;
      int var7 = (integer2 & 0xFF00) >> 8;
      int var8 = integer1 & 0xFF;
      int var9 = integer2 & 0xFF;
      int var10 = (int)((float)var4 * var5 / 255.0F);
      int var11 = (int)((float)var6 * var7 / 255.0F);
      int var12 = (int)((float)var8 * var9 / 255.0F);
      return integer1 & 0xFF000000 | var10 << 16 | var11 << 8 | var12;
   }

   public static double func_181162_h(double double1) {
      return double1 - Math.floor(double1);
   }

   public static long getPositionRandom(Vec3i vec3i) {
      return getCoordinateRandom(vec3i.getX(), vec3i.getY(), vec3i.getZ());
   }

   public static long getCoordinateRandom(int integer1, int integer2, int integer3) {
      long var5 = integer1 * 3129871L ^ integer3 * 116129781L ^ integer2;
      return var5 * var5 * 42317861L + var5 * 11L;
   }

   public static UUID getRandomUuid(Random random) {
      long var3 = random.nextLong() & -61441L | 16384L;
      long var5 = random.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
      return new UUID(var3, var5);
   }

   public static double func_181160_c(double double1, double double2, double double3) {
      return (double1 - double2) / (double3 - double2);
   }

   public static float Method565(float float1, float float2) {
      return (float)atan2(float1, float2);
   }

   public static double atan2(double double1, double double2) {
      double var7 = double2 * double2 + double1 * double1;
      if (Double.isNaN(var7)) {
         return Double.NaN;
      }

      boolean var9 = double1 < 0.0;
      if (var9) {
         double1 = -double1;
      }

      boolean var10 = double2 < 0.0;
      if (var10) {
         double2 = -double2;
      }

      boolean var11 = double1 > double2;
      if (var11) {
         double var12 = double2;
         double2 = double1;
         double1 = var12;
      }

      double var31 = func_181161_i(var7);
      double2 *= var31;
      double1 *= var31;
      double var14 = field_181163_d + double1;
      int var16 = (int)Double.doubleToRawLongBits(var14);
      double var17 = field_181164_e[var16];
      double var19 = field_181165_f[var16];
      double var21 = var14 - field_181163_d;
      double var23 = double1 * var19 - double2 * var21;
      double var25 = (6.0 + var23 * var23) * var23 * 0.16666666666666666;
      double var27 = var17 + var25;
      if (var11) {
         var27 = (Math.PI / 2) - var27;
      }

      if (var10) {
         var27 = Math.PI - var27;
      }

      if (var9) {
         var27 = -var27;
      }

      return var27;
   }

   public static double func_181161_i(double double1) {
      double var4 = 0.5 * double1;
      long var6 = Double.doubleToRawLongBits(double1);
      double1 = Double.longBitsToDouble(6910469410427058090L - (var6 >> 1));
      return double1 * (1.5 - var4 * double1 * double1);
   }

   public static int hsvToRGB(float float1, float float2, float float3) {
      int var6 = (int)(float1 * 6.0F) % 6;
      float var7 = float1 * 6.0F - var6;
      float var8 = float3 * (1.0F - float2);
      float var9 = float3 * (1.0F - var7 * float2);
      float var10 = float3 * (1.0F - (1.0F - var7) * float2);
      float var11;
      float var12;
      float var13;
      switch (var6) {
         case 0:
            var11 = float3;
            var12 = var10;
            var13 = var8;
            break;
         case 1:
            var11 = var9;
            var12 = float3;
            var13 = var8;
            break;
         case 2:
            var11 = var8;
            var12 = float3;
            var13 = var10;
            break;
         case 3:
            var11 = var8;
            var12 = var9;
            var13 = float3;
            break;
         case 4:
            var11 = var10;
            var12 = var8;
            var13 = float3;
            break;
         case 5:
            var11 = float3;
            var12 = var8;
            var13 = var9;
            break;
         default:
            throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + float1 + ", " + float2 + ", " + float3);
      }

      int var14 = clamp_int((int)(var11 * 255.0F), 0, 255);
      int var15 = clamp_int((int)(var12 * 255.0F), 0, 255);
      int var16 = clamp_int((int)(var13 * 255.0F), 0, 255);
      return var14 << 16 | var15 << 8 | var16;
   }

   static {
      for (int var24 = 0; var24 < 65536; var24++) {
         SIN_TABLE[var24] = (float)Math.sin(var24 * Math.PI * 2.0 / 65536.0);
      }

      for (int var29 = 0; var29 < SIN_TABLE_FAST.length; var29++) {
         SIN_TABLE_FAST[var29] = MathUtils.roundToFloat(Math.sin(var29 * Math.PI * 2.0 / 4096.0));
      }

      multiplyDeBruijnBitPosition = new int[]{
         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
      };
      field_181163_d = 1.7592186E13F;
      field_181164_e = new double[257];
      field_181165_f = new double[257];

      for (int var30 = 0; var30 < 257; var30++) {
         double var27 = Math.asin(var30 / 256.0);
         field_181165_f[var30] = Math.cos(var27);
         field_181164_e[var30] = var27;
      }

      n = 12;
      o = ~(-1 << n);
      p = o + 1;
      r = p / (float) (Math.PI * 2);
      t = p / 360.0F;
      u = new float[p];
      v = new float[p];

      for (int var31 = 0; var31 < p; var31++) {
         u[var31] = (float)Math.sin((var31 + 0.5F) / p * (float) (Math.PI * 2));
         v[var31] = (float)Math.cos((var31 + 0.5F) / p * (float) (Math.PI * 2));
      }

      for (int var32 = 0; var32 < 360; var32 += 90) {
         u[(int)(var32 * t) & o] = (float)Math.sin(var32 * Math.PI / 180.0);
         v[(int)(var32 * t) & o] = (float)Math.cos(var32 * Math.PI / 180.0);
      }
   }
}
