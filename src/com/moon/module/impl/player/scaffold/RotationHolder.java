package com.moon.module.impl.player.scaffold;

import com.moon.util.game.GcdUtil;
import com.moon.util.math.Vector2f;
import net.minecraft.util.MathHelper;

public class RotationHolder {
   private final Vector2f previousRotation = new Vector2f(0.0F, 0.0F);
   private final Vector2f currentRotation = new Vector2f(0.0F, 0.0F);
   private final Vector2f targetRotation = new Vector2f(0.0F, 0.0F);

   public Vector2f getSmoothedRotation() {
      return this.currentRotation;
   }

   public Vector2f getRotation() {
      return this.currentRotation;
   }

   public Vector2f getTargetRotation() {
      return this.targetRotation;
   }

   public void stepRotation(float step, boolean fixWraparound) {
      this.previousRotation.set(this.currentRotation);
      this.currentRotation.setX(this.approachAngle(this.currentRotation.getX(), this.targetRotation.getX(), step));
      this.currentRotation.setY(this.approachAngle(this.currentRotation.getY(), this.targetRotation.getY(), step));
      if (fixWraparound) {
         this.fixWraparound();
      }
   }

   public void setRotation(float yaw, float pitch) {
      this.currentRotation.set(yaw, pitch);
   }

   public void setPitch(float pitch) {
      this.setTarget(this.targetRotation.getX(), pitch);
   }

   public void setTargetYaw(float yaw) {
      this.setTarget(yaw, this.targetRotation.getY());
   }

   public void setTarget(float yaw, float pitch) {
      this.targetRotation.set(yaw, pitch);
   }

   public void setImmediate(float yaw, float pitch) {
      this.targetRotation.set(yaw, pitch);
      this.previousRotation.set(yaw, pitch);
      this.currentRotation.set(yaw, pitch);
   }

   private void fixWraparound() {
      Vector2f fixedRotation = GcdUtil.applyGcd(this.previousRotation, this.currentRotation);
      this.currentRotation.set(fixedRotation);
   }

   private float approachAngle(float currentAngle, float targetAngle, float step) {
      float delta = MathHelper.wrapAngleTo180_float(targetAngle - currentAngle);
      if (delta > step) {
         delta = step;
      }

      if (delta < -step) {
         delta = -step;
      }

      return currentAngle + delta;
   }
}
