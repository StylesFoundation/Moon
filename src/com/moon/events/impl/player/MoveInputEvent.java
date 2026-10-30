package com.moon.events.impl.player;

import com.moon.events.Event;

public class MoveInputEvent extends Event {
   private float forward;

   public float getForward() {
      return this.forward;
   }

   public MoveInputEvent setForward(float forward) {
      this.forward = forward;
      return this;
   }

   public MoveInputEvent(float moveForward) {
      this.forward = moveForward;
   }
}
