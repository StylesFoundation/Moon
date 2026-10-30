package com.moon.events.impl.player;

import com.moon.events.Event;

public class DamageBlockEvent extends Event {
   int slot;

   public DamageBlockEvent setSlot(int slot) {
      this.slot = slot;
      return this;
   }

   public int getSlot() {
      return this.slot;
   }

   public DamageBlockEvent(int slot) {
      this.slot = slot;
   }
}
