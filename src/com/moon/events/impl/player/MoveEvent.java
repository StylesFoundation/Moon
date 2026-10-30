package com.moon.events.impl.player;

import com.moon.events.Event;
import com.moon.util.game.MovementUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class MoveEvent extends Event {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private double x;
   private double y;
   private double z;
   private float forward;
   private float strafe;
   private float yaw;

   public void setSpeed(double speed) {
      this.x = -MathHelper.sin(MovementUtil.getMovementYaw(this.yaw, this.forward, this.strafe)) * speed;
      this.z = MathHelper.cos(MovementUtil.getMovementYaw(this.yaw, this.forward, this.strafe)) * speed;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public float getForward() {
      return this.forward;
   }

   public float getStrafe() {
      return this.strafe;
   }

   public float getYaw() {
      return this.yaw;
   }

   public MoveEvent setX(double x) {
      this.x = x;
      return this;
   }

   public MoveEvent setY(double y) {
      this.y = y;
      return this;
   }

   public MoveEvent setZ(double z) {
      this.z = z;
      return this;
   }

   public MoveEvent setForward(float forward) {
      this.forward = forward;
      return this;
   }

   public MoveEvent setStrafe(float strafe) {
      this.strafe = strafe;
      return this;
   }

   public MoveEvent setYaw(float yaw) {
      this.yaw = yaw;
      return this;
   }

   public MoveEvent(double x, double y, double z, float forward, float strafe, float yaw) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.forward = forward;
      this.strafe = strafe;
      this.yaw = yaw;
   }
}
