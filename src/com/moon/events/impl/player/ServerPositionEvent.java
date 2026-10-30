package com.moon.events.impl.player;

import com.moon.events.Event;

public class ServerPositionEvent extends Event {
   private boolean position;
   private boolean rotation;

   public boolean hasPosition() {
      return this.position;
   }

   public boolean hasRotation() {
      return this.rotation;
   }

   public ServerPositionEvent ignorePosition(boolean position) {
      this.position = position;
      return this;
   }

   public ServerPositionEvent ignoreRotation(boolean rotation) {
      this.rotation = rotation;
      return this;
   }
}
