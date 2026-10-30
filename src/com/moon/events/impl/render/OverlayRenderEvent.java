package com.moon.events.impl.render;

import com.moon.events.EventCancellable;

public class OverlayRenderEvent extends EventCancellable {
   private final OverlayRenderEvent.Sequence sequence;

   public OverlayRenderEvent(OverlayRenderEvent.Sequence sequence) {
      this.sequence = sequence;
   }

   public OverlayRenderEvent.Sequence getSequence() {
      return this.sequence;
   }

   public enum Sequence {
      PRE,
      POST;
   }
}
