package com.moon.events.impl.input;

import com.moon.events.EventCancellable;

public class KeyStateEvent extends EventCancellable {
   private int key;
   private boolean state;

   public int getKey() {
      return this.key;
   }

   public boolean isPressed() {
      return this.state;
   }

   public KeyStateEvent setKey(int key) {
      this.key = key;
      return this;
   }

   public KeyStateEvent setState(boolean state) {
      this.state = state;
      return this;
   }

   public KeyStateEvent(int key, boolean state) {
      this.key = key;
      this.state = state;
   }
}
