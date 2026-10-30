package com.moon.module.impl.visual.hud.pitevent;

public class PitEvent {
   private String name;
   private long timestamp;
   private String type;

   public String getName() {
      return this.name;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public String getType() {
      return this.type;
   }

   public PitEvent setName(String name) {
      this.name = name;
      return this;
   }

   public PitEvent setTimestamp(long timestamp) {
      this.timestamp = timestamp;
      return this;
   }

   public PitEvent setType(String type) {
      this.type = type;
      return this;
   }
}
