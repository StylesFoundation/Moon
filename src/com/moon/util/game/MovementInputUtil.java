package com.moon.util.game;

import com.moon.util.math.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public final class MovementInputUtil {
   private static final float[] DIAGONAL_FACTORS = new float[]{1.0F, MathUtil.SQRT_TWO};
   private static final float[] SPEED_FACTORS = new float[]{0.98F, 0.29400003F, 0.19600001F, 0.05880001F};
   private static final Minecraft mc = Minecraft.getMinecraft();

   private MovementInputUtil() {
   }

   public static float[] toWorldDirection(float yaw, float forward, float strafe, float speed) {
      float worldX = 0.0F;
      float worldZ = 0.0F;
      float lengthSq = strafe * strafe + forward * forward;
      if (lengthSq >= 1.0E-4F) {
         float length = MathHelper.sqrt_float(lengthSq);
         if (length < 1.0F) {
            length = 1.0F;
         }

         float factor = speed / length;
         strafe *= factor;
         forward *= factor;
         float sin = MathHelper.sin(yaw);
         float cos = MathHelper.cos(yaw);
         worldX = strafe * cos - forward * sin;
         worldZ = forward * cos + strafe * sin;
      }

      return new float[]{worldX, worldZ};
   }

   public static float[] inferInput(float yaw, float motionZ, float motionX, float friction, float speed) {
      float sin = MathHelper.sin(yaw);
      float cos = MathHelper.cos(yaw);
      float bestStrafe = 0.0F;
      float bestForward = 0.0F;
      float minDiff = Float.MAX_VALUE;

      for (float diagonalFactor : DIAGONAL_FACTORS) {
         float factor = Math.max(1.0F, speed * diagonalFactor) / friction;
         float strafe = factor * (motionX * cos - motionZ * sin);
         float forward = factor * (motionZ * cos + motionX * sin);
         float diff = Math.abs(Math.max(Math.abs(strafe), Math.abs(forward)) - speed);
         if (diff < minDiff) {
            minDiff = diff;
            bestStrafe = strafe;
            bestForward = forward;
         }
      }

      return new float[]{bestStrafe, bestForward};
   }
}
