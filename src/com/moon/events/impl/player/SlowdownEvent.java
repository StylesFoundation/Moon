package com.moon.events.impl.player;

import com.moon.events.EventCancellable;

public class SlowdownEvent extends EventCancellable {
   private final SlowdownEvent.Type type;

   public SlowdownEvent.Type getType() {
      return this.type;
   }

   public SlowdownEvent(SlowdownEvent.Type type) {
      this.type = type;
   }

   public enum Type {
      SNEAKING,
      SPRINTING,
      ITEM,
      BLOCKS;
   }
}
