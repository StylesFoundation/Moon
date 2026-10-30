package com.moon.events.impl.render;

import com.moon.events.Event;

public class WorldRenderEvent extends Event {
   private final WorldRenderEvent.Sequence sequence;

   public WorldRenderEvent.Sequence getSequence() {
      return this.sequence;
   }

   public WorldRenderEvent(WorldRenderEvent.Sequence sequence) {
      this.sequence = sequence;
   }

   public enum Sequence {
      PRE,
      POST;
   }
}
