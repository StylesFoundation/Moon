package com.moon.events.impl.player;

import com.moon.events.Event;

public class HeldItemEvent extends Event {
   private int slot;

   public int getSlot() {
      return this.slot;
   }

   public HeldItemEvent setSlot(int slot) {
      this.slot = slot;
      return this;
   }

   public HeldItemEvent(int slot) {
      this.slot = slot;
   }
}
