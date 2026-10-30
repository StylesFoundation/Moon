package com.moon.util.game;

import com.moon.util.math.RotationUtil;
import com.moon.util.math.Vector2f;
import net.minecraft.util.MathHelper;

public class GcdUtil {
   public static Vector2f applyGcd(Vector2f current, Vector2f target) {
      GcdOffset[] candidateOffsets = getCandidateOffsets(new Vector2f(MathHelper.wrapAngleTo180_float(target.getX() - current.getX()), target.getY() - current.getY()));
      return pickClosestOffset(current, target, candidateOffsets);
   }

   private static Vector2f pickClosestOffset(Vector2f current, Vector2f target, GcdOffset[] offsets) {
      float sensitivityMultiplier = RotationUtil.getSensitivityMultiplier();
      float minDistance = Float.MAX_VALUE;
      Vector2f closestRotation = current;

      for (GcdOffset offset : offsets) {
         double deltaYaw = offset.x() * sensitivityMultiplier * 0.15F;
         double deltaPitch = offset.y() * sensitivityMultiplier * 0.15F;
         Vector2f rotation = new Vector2f((float)(current.getX() + deltaYaw), (float)(current.getY() + deltaPitch));
         rotation.setY(MathHelper.clamp_float(rotation.getY(), -90.0F, 90.0F));
         float diffX = rotation.getX() - target.getX();
         float diffY = rotation.getY() - target.getY();
         float distance = (float)Math.sqrt(diffX * diffX + diffY * diffY);
         if (distance < minDistance) {
            closestRotation = rotation;
            minDistance = distance;
         }
      }

      return closestRotation;
   }

   private static GcdOffset[] getCandidateOffsets(Vector2f delta) {
      float sensitivity = RotationUtil.getSensitivityMultiplier() * 0.15F;
      float targetX = delta.getX() / sensitivity;
      float targetY = delta.getY() / sensitivity;
      return new GcdOffset[]{
         new GcdOffset(Math.floor(targetX), Math.floor(targetY)),
         new GcdOffset(Math.ceil(targetX), Math.floor(targetY)),
         new GcdOffset(Math.ceil(targetX), Math.ceil(targetY)),
         new GcdOffset(Math.floor(targetX), Math.ceil(targetY))
      };
   }
}
