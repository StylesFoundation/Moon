package com.moon.util.discord;

public class DiscordRpcConfigBuilder {
   private long applicationId;
   private DiscordRpcText state;
   private DiscordRpcText description;
   private DiscordRpcImage bigImage;
   private DiscordRpcImage smallImage;
   private boolean autoRefresh;
   private int refreshInterval;

   DiscordRpcConfigBuilder() {
   }

   public DiscordRpcConfigBuilder applicationId(long applicationId) {
      this.applicationId = applicationId;
      return this;
   }

   public DiscordRpcConfigBuilder state(DiscordRpcText state) {
      this.state = state;
      return this;
   }

   public DiscordRpcConfigBuilder description(DiscordRpcText description) {
      this.description = description;
      return this;
   }

   public DiscordRpcConfigBuilder bigImage(DiscordRpcImage bigImage) {
      this.bigImage = bigImage;
      return this;
   }

   public DiscordRpcConfigBuilder smallImage(DiscordRpcImage smallImage) {
      this.smallImage = smallImage;
      return this;
   }

   public DiscordRpcConfigBuilder autoRefresh(boolean autoRefresh) {
      this.autoRefresh = autoRefresh;
      return this;
   }

   public DiscordRpcConfigBuilder refreshInterval(int refreshInterval) {
      this.refreshInterval = refreshInterval;
      return this;
   }

   public DiscordRpcConfig build() {
      return new DiscordRpcConfig(this.applicationId, this.state, this.description, this.bigImage, this.smallImage, this.autoRefresh, this.refreshInterval);
   }

   @Override
   public String toString() {
      int refreshInterval = this.refreshInterval;
      boolean autoRefresh = this.autoRefresh;
      DiscordRpcImage smallImage = this.smallImage;
      DiscordRpcImage bigImage = this.bigImage;
      DiscordRpcText description = this.description;
      DiscordRpcText state = this.state;
      long applicationId = this.applicationId;
      return "DiscordRpcConfig.DiscordRpcConfigBuilder(applicationId="
         + applicationId
         + ", state="
         + state
         + ", description="
         + description
         + ", bigImage="
         + bigImage
         + ", smallImage="
         + smallImage
         + ", autoRefresh="
         + autoRefresh
         + ", refreshInterval="
         + refreshInterval
         + ")";
   }
}
