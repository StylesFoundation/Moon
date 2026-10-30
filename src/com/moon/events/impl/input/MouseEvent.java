package com.moon.events.impl.input;

import com.moon.events.Event;

public class MouseEvent extends Event {
   private final int button;
   private final boolean pressed;

   public int getButton() {
      return this.button;
   }

   public boolean isPressed() {
      return this.pressed;
   }

   public MouseEvent(int button, boolean pressed) {
      this.button = button;
      this.pressed = pressed;
   }
}
