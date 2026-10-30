package com.moon.events.impl.player;

import com.moon.events.EventCancellable;

public class SlotChangeEvent extends EventCancellable {
   private int slot;

   public SlotChangeEvent setSlot(int slot) {
      this.slot = slot;
      return this;
   }

   public int getSlot() {
      return this.slot;
   }

   public SlotChangeEvent(int slot) {
      this.slot = slot;
   }
}
