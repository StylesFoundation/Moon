package com.moon.events.impl.render;

import com.moon.events.Event;

public class EffectRenderEvent extends Event {
   private final boolean gaussian;

   public boolean isGaussian() {
      return this.gaussian;
   }

   public EffectRenderEvent(boolean gaussian) {
      this.gaussian = gaussian;
   }
}
