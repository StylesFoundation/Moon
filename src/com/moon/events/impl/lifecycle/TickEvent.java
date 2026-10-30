package com.moon.events.impl.lifecycle;

import com.moon.events.Event;

public class TickEvent extends Event {
   private final TickEvent.Sequence sequence;
   private int pass;

   public TickEvent(TickEvent.Sequence sequence) {
      this.sequence = sequence;
   }

   public void tick() {
      this.pass++;
   }

   public boolean decay() {
      return this.pass-- > 0;
   }

   public TickEvent.Sequence getSequence() {
      return this.sequence;
   }

   public int getTickPassed() {
      return this.pass;
   }

   public enum Sequence {
      PRE,
      POST;
   }
}
