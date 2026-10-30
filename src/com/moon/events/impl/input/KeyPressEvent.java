package com.moon.events.impl.input;

import com.moon.events.Event;

public class KeyPressEvent extends Event {
   private final int key;
   private final boolean state;

   public int getKey() {
      return this.key;
   }

   public boolean isPressed() {
      return this.state;
   }

   public KeyPressEvent(int key, boolean state) {
      this.key = key;
      this.state = state;
   }
}
