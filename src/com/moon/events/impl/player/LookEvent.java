package com.moon.events.impl.player;

import com.moon.events.Event;

public class LookEvent extends Event {
   private float yaw;
   private float pitch;

   public float getYaw() {
      return this.yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public LookEvent setYaw(float yaw) {
      this.yaw = yaw;
      return this;
   }

   public LookEvent setPitch(float pitch) {
      this.pitch = pitch;
      return this;
   }

   public LookEvent(float yaw, float pitch) {
      this.yaw = yaw;
      this.pitch = pitch;
   }
}
