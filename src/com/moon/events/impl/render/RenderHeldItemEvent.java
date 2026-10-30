package com.moon.events.impl.render;

import com.moon.events.Event;

public class RenderHeldItemEvent extends Event {
   private int slot;

   public RenderHeldItemEvent setSlot(int slot) {
      this.slot = slot;
      return this;
   }

   public int getSlot() {
      return this.slot;
   }

   public RenderHeldItemEvent(int slot) {
      this.slot = slot;
   }
}
