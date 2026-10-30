package com.moon.events.impl.render;

import com.moon.events.Event;

public class FirstPersonItemRenderEvent extends Event {
   private boolean using;

   public boolean isUsing() {
      return this.using;
   }

   public FirstPersonItemRenderEvent setUsing(boolean using) {
      this.using = using;
      return this;
   }
}
