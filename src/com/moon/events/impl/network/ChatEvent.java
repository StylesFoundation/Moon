package com.moon.events.impl.network;

import com.moon.events.EventCancellable;

public class ChatEvent extends EventCancellable {
   private String message;

   public String getMessage() {
      return this.message;
   }

   public ChatEvent setMessage(String message) {
      this.message = message;
      return this;
   }

   public ChatEvent(String message) {
      this.message = message;
   }
}
