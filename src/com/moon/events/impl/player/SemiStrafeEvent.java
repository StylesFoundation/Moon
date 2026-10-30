package com.moon.events.impl.player;

import com.moon.events.Event;

public class SemiStrafeEvent extends Event {
   private float forward;
   private float strafe;
   private float yaw;

   public float getForward() {
      return this.forward;
   }

   public float getStrafe() {
      return this.strafe;
   }

   public float getYaw() {
      return this.yaw;
   }

   public SemiStrafeEvent setForward(float forward) {
      this.forward = forward;
      return this;
   }

   public SemiStrafeEvent setStrafe(float strafe) {
      this.strafe = strafe;
      return this;
   }

   public SemiStrafeEvent setYaw(float yaw) {
      this.yaw = yaw;
      return this;
   }

   public SemiStrafeEvent(float forward, float strafe, float yaw) {
      this.forward = forward;
      this.strafe = strafe;
      this.yaw = yaw;
   }
}
