package com.moon.connection;

import java.util.function.Consumer;

public class GatewayClientConfigBuilder {
   private String connectionUrl;
   private String secret;
   private Consumer<String> chatCallback;
   private Consumer<Void> reconnectionCallback;
   private Consumer<Runnable> synchronizer;

   GatewayClientConfigBuilder() {
   }

   public GatewayClientConfigBuilder connectionUrl(String connectionUrl) {
      this.connectionUrl = connectionUrl;
      return this;
   }

   public GatewayClientConfigBuilder secret(String secret) {
      this.secret = secret;
      return this;
   }

   public GatewayClientConfigBuilder chatCallback(Consumer<String> chatCallback) {
      this.chatCallback = chatCallback;
      return this;
   }

   public GatewayClientConfigBuilder reconnectionCallback(Consumer<Void> reconnectionCallback) {
      this.reconnectionCallback = reconnectionCallback;
      return this;
   }

   public GatewayClientConfigBuilder synchronizer(Consumer<Runnable> synchronizer) {
      this.synchronizer = synchronizer;
      return this;
   }

   public GatewayClientConfig build() {
      return new GatewayClientConfig(this.connectionUrl, this.secret, this.chatCallback, this.reconnectionCallback, this.synchronizer);
   }

   @Override
   public String toString() {
      Consumer synchronizer = this.synchronizer;
      Consumer reconnectionCallback = this.reconnectionCallback;
      Consumer chatCallback = this.chatCallback;
      String secret = this.secret;
      String connectionUrl = this.connectionUrl;
      return "GatewayClientConfig.GatewayClientConfigBuilder(connectionUrl="
         + connectionUrl
         + ", secret="
         + secret
         + ", chatCallback="
         + chatCallback
         + ", reconnectionCallback="
         + reconnectionCallback
         + ", synchronizer="
         + synchronizer
         + ")";
   }
}
