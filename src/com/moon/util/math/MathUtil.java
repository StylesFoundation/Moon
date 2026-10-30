package com.moon.util.math;

import java.util.Random;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class MathUtil {
   public static float TWO_PI = (float) (Math.PI * 2);
   public static float GOLDEN_RATIO = (float)((1.0 + Math.sqrt(5.0)) / 2.0);
   public static float SQRT_TWO = (float)Math.sqrt(2.0);
   public static float TO_RADIANS = (float) (Math.PI / 180.0);
   public static float TO_DEGREES = 180.0F / (float)Math.PI;
   private static final Random RANDOM = new Random();

   public static float length(float... values) {
      return MathHelper.sqrt_float(lengthSquared(values));
   }

   public static float length(double... values) {
      return MathHelper.sqrt_float(lengthSquared(values));
   }

   public static float lengthSquared(float... values) {
      float sum = 0.0F;

      for (float value : values) {
         sum += value * value;
      }

      return sum;
   }

   public static float lengthSquared(double... values) {
      float sum = 0.0F;

      for (double value : values) {
         sum = (float)(sum + value * value);
      }

      return sum;
   }

   public static float[] rotationsBetween(Vector3f from, Vector3f to) {
      Vector3f diff = to.sub(from);
      float distXZ = MathHelper.sqrt_float(diff.Method519() * diff.Method519() + diff.Method521() * diff.Method521());
      float yaw = MathHelper.Method565(diff.Method521(), diff.Method519()) * TO_DEGREES - 90.0F;
      float pitch = -MathHelper.Method565(diff.Method520(), distXZ) * TO_DEGREES;
      return new float[]{yaw, pitch};
   }

   public static Vec3 getLookVector(float yaw, float pitch) {
      float cosYaw = MathHelper.cos(-yaw * RotationUtil.TO_RADIANS - (float) Math.PI);
      float sinYaw = MathHelper.sin(-yaw * RotationUtil.TO_RADIANS - (float) Math.PI);
      float cosPitch = -MathHelper.cos(-pitch * RotationUtil.TO_RADIANS);
      float sinPitch = MathHelper.sin(-pitch * RotationUtil.TO_RADIANS);
      return new Vec3(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
   }

   public static double round(double value, int decimals) {
      double scale = Math.pow(10.0, decimals);
      return Math.round(value * scale) / scale;
   }

   public static float roundToHalf(float value) {
      return Math.round(value * 2.0F) / 2.0F;
   }

   public static float random(float min, float max) {
      return (max - min) * RANDOM.nextFloat() + min;
   }

   public static double random(double min, double max) {
      return (max - min) * RANDOM.nextDouble() + min;
   }

   public static int randomInt(int min, int max) {
      return RANDOM.nextInt(max - min + 1) + min;
   }
}
