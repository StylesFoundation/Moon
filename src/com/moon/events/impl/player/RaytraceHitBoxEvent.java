package com.moon.events.impl.player;

import com.moon.events.Event;

public class RaytraceHitBoxEvent extends Event {
   private float size;

   public float getSize() {
      return this.size;
   }

   public RaytraceHitBoxEvent setSize(float size) {
      this.size = size;
      return this;
   }

   public RaytraceHitBoxEvent(float size) {
      this.size = size;
   }
}
