package com.moon.util.game;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class MovementUtil {
   private static final Minecraft mc = Minecraft.getMinecraft();

   public static double applyGravityTicks(double velocity, int ticks) {
      double currentVelocity = velocity;

      for (int i = 0; i < ticks; i++) {
         currentVelocity = (currentVelocity - 0.08) * 0.98F;
      }

      return currentVelocity;
   }

   public static double getSpeed() {
      return Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);
   }

   public static void setSpeed(double speed) {
      mc.thePlayer.motionX = -Math.sin(getMovementYaw()) * speed;
      mc.thePlayer.motionZ = Math.cos(getMovementYaw()) * speed;
   }

   public static void setSpeed(double speed, float yaw) {
      mc.thePlayer.motionX = -Math.sin(yaw) * speed;
      mc.thePlayer.motionZ = Math.cos(yaw) * speed;
   }

   public static void setSpeed(double speed, float yaw, float forward, float strafe) {
      mc.thePlayer.motionX = -Math.sin(getMovementYaw(yaw, forward, strafe)) * speed;
      mc.thePlayer.motionZ = Math.cos(getMovementYaw(yaw, forward, strafe)) * speed;
   }

   public static float getMovementYaw() {
      if (mc.thePlayer == null) {
         return 0.0F;
      }

      float yaw = mc.thePlayer.rotationYaw;
      float moveForward = mc.thePlayer.moveForward;
      float moveStrafing = mc.thePlayer.moveStrafing;
      int strafeAngle = moveForward < 0.0F ? -45 : (moveForward == 0.0F ? 90 : 45);
      yaw += moveForward < 0.0F ? 180.0F : 0.0F;
      if (moveStrafing < 0.0F) {
         yaw += strafeAngle;
      }

      if (moveStrafing > 0.0F) {
         yaw -= strafeAngle;
      }

      return (float)(yaw * (Math.PI / 180.0));
   }

   public static float getMovementYaw(float yaw) {
      if (mc.thePlayer == null) {
         return 0.0F;
      }

      float moveForward = mc.thePlayer.moveForward;
      float moveStrafing = mc.thePlayer.moveStrafing;
      int strafeAngle = moveForward < 0.0F ? -45 : (moveForward == 0.0F ? 90 : 45);
      yaw += moveForward < 0.0F ? 180.0F : 0.0F;
      if (moveStrafing < 0.0F) {
         yaw += strafeAngle;
      }

      if (moveStrafing > 0.0F) {
         yaw -= strafeAngle;
      }

      return (float)(yaw * (Math.PI / 180.0));
   }

   public static float getMovementYaw(float yaw, float forward, float strafe) {
      if (mc.thePlayer == null) {
         return 0.0F;
      }

      int strafeAngle = forward < 0.0F ? -45 : (forward == 0.0F ? 90 : 45);
      yaw += forward < 0.0F ? 180.0F : 0.0F;
      if (strafe < 0.0F) {
         yaw += strafeAngle;
      }

      if (strafe > 0.0F) {
         yaw -= strafeAngle;
      }

      return (float)(yaw * (Math.PI / 180.0));
   }

   public static double getBaseSpeed() {
      double baseSpeed = mc.thePlayer.isSprinting() ? 0.2873 : 0.216;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         baseSpeed *= 1.0 + 0.2 * mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
      }

      return baseSpeed;
   }

   public static double getBaseSpeed(boolean sprinting) {
      double baseSpeed = !mc.thePlayer.isSprinting() && !sprinting ? 0.216 : 0.2873;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         baseSpeed *= 1.0 + 0.2 * mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
      }

      return baseSpeed;
   }

   public static double getBaseSpeed(double multiplier) {
      double baseSpeed = mc.thePlayer.isSprinting() ? 0.2873 : 0.216;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         baseSpeed *= 1.0 + multiplier * mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
      }

      return baseSpeed;
   }
}
