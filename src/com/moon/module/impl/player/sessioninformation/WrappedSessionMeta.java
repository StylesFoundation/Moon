package com.moon.module.impl.player.sessioninformation;

import java.util.function.Supplier;

public class WrappedSessionMeta {
   private final Supplier<String> message;
   private final String icon;
   private final float offset;

   WrappedSessionMeta(Supplier<String> message, String icon, float offset) {
      this.message = message;
      this.icon = icon;
      this.offset = offset;
   }

   public static WrappedSessionMetaBuilder builder() {
      return new WrappedSessionMetaBuilder();
   }

   public Supplier<String> getMessage() {
      return this.message;
   }

   public String getIcon() {
      return this.icon;
   }

   public float getOffset() {
      return this.offset;
   }
}
