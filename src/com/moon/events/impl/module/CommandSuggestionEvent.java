package com.moon.events.impl.module;

import com.moon.events.Event;

public class CommandSuggestionEvent extends Event {
   private final String text;
   private String suggestion;
   private boolean accepted;

   public String getText() {
      return this.text;
   }

   public String getSuggestion() {
      return this.suggestion;
   }

   public boolean isAccepted() {
      return this.accepted;
   }

   public CommandSuggestionEvent setSuggestion(String suggestion) {
      this.suggestion = suggestion;
      return this;
   }

   public CommandSuggestionEvent setAccepted(boolean accepted) {
      this.accepted = accepted;
      return this;
   }

   public CommandSuggestionEvent(String text, String suggestion, boolean accepted) {
      this.text = text;
      this.suggestion = suggestion;
      this.accepted = accepted;
   }
}
