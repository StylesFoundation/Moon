package com.moon.util.discord;

public class DiscordRpcConfig {
   private final long applicationId;
   private final DiscordRpcText state;
   private final DiscordRpcText description;
   private DiscordRpcImage bigImage;
   private DiscordRpcImage smallImage;
   private boolean autoRefresh;
   private int refreshInterval;

   DiscordRpcConfig(
      long applicationId,
      DiscordRpcText state,
      DiscordRpcText description,
      DiscordRpcImage bigImage,
      DiscordRpcImage smallImage,
      boolean autoRefresh,
      int refreshInterval
   ) {
      this.applicationId = applicationId;
      this.state = state;
      this.description = description;
      this.bigImage = bigImage;
      this.smallImage = smallImage;
      this.autoRefresh = autoRefresh;
      this.refreshInterval = refreshInterval;
   }

   public static DiscordRpcConfigBuilder builder() {
      return new DiscordRpcConfigBuilder();
   }

   public long getApplicationId() {
      return this.applicationId;
   }

   public DiscordRpcText getState() {
      return this.state;
   }

   public DiscordRpcText getDescription() {
      return this.description;
   }

   public DiscordRpcImage getBigImage() {
      return this.bigImage;
   }

   public DiscordRpcImage getSmallImage() {
      return this.smallImage;
   }

   public boolean isAutoRefresh() {
      return this.autoRefresh;
   }

   public int getRefreshInterval() {
      return this.refreshInterval;
   }

   public static int defaultRefreshInterval() {
      return 0;
   }
}
