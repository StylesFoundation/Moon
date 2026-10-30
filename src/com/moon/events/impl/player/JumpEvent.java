package com.moon.events.impl.player;

import com.moon.events.Event;

public class JumpEvent extends Event {
   private float yaw;

   public float getYaw() {
      return this.yaw;
   }

   public JumpEvent setYaw(float yaw) {
      this.yaw = yaw;
      return this;
   }

   public JumpEvent(float yaw) {
      this.yaw = yaw;
   }
}
