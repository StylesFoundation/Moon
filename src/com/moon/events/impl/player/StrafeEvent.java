package com.moon.events.impl.player;

import com.moon.events.Event;

public class StrafeEvent extends Event {
   private float yaw;
   private float forward;
   private float strafe;

   public float getYaw() {
      return this.yaw;
   }

   public float getForward() {
      return this.forward;
   }

   public float getStrafe() {
      return this.strafe;
   }

   public StrafeEvent setYaw(float yaw) {
      this.yaw = yaw;
      return this;
   }

   public StrafeEvent setForward(float forward) {
      this.forward = forward;
      return this;
   }

   public StrafeEvent setStrafe(float strafe) {
      this.strafe = strafe;
      return this;
   }

   public StrafeEvent(float yaw, float forward, float strafe) {
      this.yaw = yaw;
      this.forward = forward;
      this.strafe = strafe;
   }
}
