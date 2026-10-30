package com.moon.util.discord;

import java.util.function.Supplier;

public class DiscordRpcImage {
   private final String key;
   private final Supplier<String> tooltip;

   public String getKey() {
      return this.key;
   }

   public Supplier<String> getTooltip() {
      return this.tooltip;
   }

   private DiscordRpcImage(String key, Supplier<String> tooltip) {
      this.key = key;
      this.tooltip = tooltip;
   }

   public static DiscordRpcImage of(String key, Supplier<String> tooltip) {
      return new DiscordRpcImage(key, tooltip);
   }
}
