package com.moon.util.math;

import com.moon.Moon;
import com.moon.module.impl.combat.AntiBot;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtil {
   private static final Minecraft mc = Minecraft.getMinecraft();
   public static float TO_RADIANS = (float) (Math.PI / 180.0);
   public static float TO_DEGREES = 180.0F / (float)Math.PI;
   private AntiBot antiBot = Moon.getInstance().getModuleManager().getModule(AntiBot.class);

   public static double getYawDifference(EntityLivingBase entity) {
      if (entity != null) {
         Vec3 diff = entity.getPositionVector()
            .addVector(0.0, entity.getEyeHeight() / 2.0F, 0.0)
            .subtract(Minecraft.getMinecraft().thePlayer.getPositionVector().addVector(0.0, Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0));
         double yawDiff = Math.abs(Minecraft.getMinecraft().thePlayer.rotationYaw - (Math.toDegrees(Math.atan2(diff.zCoord, diff.xCoord)) - 90.0)) % 360.0;
         return yawDiff > 180.0 ? 360.0 - yawDiff : yawDiff;
      } else {
         return 0.0;
      }
   }

   public static double getYawDifferenceClone(EntityLivingBase entity) {
      if (entity != null) {
         Vec3 diff = entity.getPositionVector()
            .addVector(0.0, entity.getEyeHeight() / 2.0F, 0.0)
            .subtract(Minecraft.getMinecraft().thePlayer.getPositionVector().addVector(0.0, Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0));
         double yawDiff = Math.abs(Minecraft.getMinecraft().thePlayer.rotationYaw - (Math.toDegrees(Math.atan2(diff.zCoord, diff.xCoord)) - 90.0)) % 360.0;
         return yawDiff > 180.0 ? 360.0 - yawDiff : yawDiff;
      } else {
         return 0.0;
      }
   }

   public static double getYawDifferenceServer(EntityLivingBase entity) {
      if (entity != null) {
         Vec3 diff = entity.getPositionVector()
            .addVector(0.0, entity.getEyeHeight() / 2.0F, 0.0)
            .subtract(Minecraft.getMinecraft().thePlayer.getPositionVector().addVector(0.0, Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0));
         double yawDiff = Math.abs(Minecraft.getMinecraft().thePlayer.cw - (Math.toDegrees(Math.atan2(diff.zCoord, diff.xCoord)) - 90.0)) % 360.0;
         return yawDiff > 180.0 ? 360.0 - yawDiff : yawDiff;
      } else {
         return 0.0;
      }
   }

   public static float clampAngle(float current, float target, float maxDelta) {
      float delta = MathHelper.wrapAngleTo180_float(target - current);
      if (delta > maxDelta) {
         delta = maxDelta;
      }

      if (delta < -maxDelta) {
         delta = -maxDelta;
      }

      return current + delta;
   }

   public static float[] getMovementRotations(float currentYaw, float targetYaw) {
      float forward = mc.thePlayer.movementInput.moveForward > 0.0F ? 1.0F : (mc.thePlayer.movementInput.moveForward < 0.0F ? -1.0F : 0.0F);
      float strafe = mc.thePlayer.movementInput.moveStrafe > 0.0F ? 1.0F : (mc.thePlayer.movementInput.moveStrafe < 0.0F ? -1.0F : 0.0F);
      double moveX = strafe * Math.cos(Math.toRadians(currentYaw)) - forward * Math.sin(Math.toRadians(currentYaw));
      double moveZ = forward * Math.cos(Math.toRadians(currentYaw)) + strafe * Math.sin(Math.toRadians(currentYaw));
      double[] bestMovement = findBestMovement(targetYaw, moveX, moveZ);
      return new float[]{(float)bestMovement[1], (float)bestMovement[2]};
   }

   private static double[] findBestMovement(float yaw, double motionX, double motionZ) {
      double[] bestMovement = null;

      for (int forward = -1; forward <= 1; forward++) {
         for (int strafe = -1; strafe <= 1; strafe++) {
            double simX = strafe * Math.cos(Math.toRadians(yaw)) - forward * Math.sin(Math.toRadians(yaw));
            double simZ = forward * Math.cos(Math.toRadians(yaw)) + strafe * Math.sin(Math.toRadians(yaw));
            double diffX = simX - motionX;
            double diffZ = simZ - motionZ;
            double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
            if (bestMovement == null || bestMovement[0] > dist) {
               bestMovement = new double[]{dist, forward, strafe};
            }
         }
      }

      return bestMovement;
   }

   public static float getMovementYaw() {
      float sin = MathHelper.sin(mc.thePlayer.rotationYaw * TO_RADIANS);
      float cos = MathHelper.cos(mc.thePlayer.rotationYaw * TO_RADIANS);
      float moveX = mc.thePlayer.moveStrafing * cos - mc.thePlayer.moveForward * sin;
      float moveZ = mc.thePlayer.moveForward * cos + mc.thePlayer.moveStrafing * sin;
      return MathHelper.Method565(moveZ, moveX) * TO_DEGREES - 90.0F;
   }

   public static float getSensitivityMultiplier() {
      float sensitivity = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
      return sensitivity * sensitivity * sensitivity * 8.0F;
   }

   public double getYawDifferenceInstance(EntityLivingBase entity) {
      if (entity != null) {
         Vec3 diff = entity.getPositionVector()
            .addVector(0.0, entity.getEyeHeight() / 2.0F, 0.0)
            .subtract(Minecraft.getMinecraft().thePlayer.getPositionVector().addVector(0.0, Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0));
         double yawDiff = Math.abs(Minecraft.getMinecraft().thePlayer.rotationYaw - (Math.toDegrees(Math.atan2(diff.zCoord, diff.xCoord)) - 90.0)) % 360.0;
         return yawDiff > 180.0 ? 360.0 - yawDiff : yawDiff;
      } else {
         return 0.0;
      }
   }
}
