package com.moon.events;

public class EventCancellable extends Event {
   private boolean cancelled;

   public boolean isCancelled() {
      return this.cancelled;
   }

   public EventCancellable setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
      return this;
   }
}
