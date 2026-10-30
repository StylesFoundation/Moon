package com.moon.module.impl.player.sessioninformation;

import java.util.function.Supplier;

public class WrappedSessionMetaBuilder {
   private Supplier<String> message;
   private String icon;
   private float offset;

   WrappedSessionMetaBuilder() {
   }

   public WrappedSessionMetaBuilder setMessage(Supplier<String> message) {
      this.message = message;
      return this;
   }

   public WrappedSessionMetaBuilder setIcon(String icon) {
      this.icon = icon;
      return this;
   }

   public WrappedSessionMetaBuilder setOffset(float offset) {
      this.offset = offset;
      return this;
   }

   public WrappedSessionMeta build() {
      return new WrappedSessionMeta(this.message, this.icon, this.offset);
   }

   @Override
   public String toString() {
      float offset = this.offset;
      String icon = this.icon;
      Supplier<String> message = this.message;
      return "WrappedSessionMeta.WrappedSessionMetaBuilder(message=" + message + ", icon=" + icon + ", offset=" + offset + ")";
   }
}
