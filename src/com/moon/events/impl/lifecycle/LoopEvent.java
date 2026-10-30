package com.moon.events.impl.lifecycle;

import com.moon.events.Event;

public class LoopEvent extends Event {
   private final LoopEvent.Sequence sequence;

   public LoopEvent.Sequence getSequence() {
      return this.sequence;
   }

   public LoopEvent(LoopEvent.Sequence sequence) {
      this.sequence = sequence;
   }

   public enum Sequence {
      PRE,
      POST;

   }
}
