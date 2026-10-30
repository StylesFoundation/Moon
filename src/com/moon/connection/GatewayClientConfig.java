package com.moon.connection;

import java.util.function.Consumer;

public class GatewayClientConfig {
   private String connectionUrl;
   private String secret;
   private Consumer<String> chatCallback;
   private Consumer<Void> reconnectionCallback;
   private Consumer<Runnable> synchronizer;

   GatewayClientConfig(String connectionUrl, String secret, Consumer<String> chatCallback, Consumer<Void> reconnectionCallback, Consumer<Runnable> synchronizer) {
      this.connectionUrl = connectionUrl;
      this.secret = secret;
      this.chatCallback = chatCallback;
      this.reconnectionCallback = reconnectionCallback;
      this.synchronizer = synchronizer;
   }

   public static GatewayClientConfigBuilder builder() {
      return new GatewayClientConfigBuilder();
   }

   public String getConnectionUrl() {
      return this.connectionUrl;
   }

   public String getSecret() {
      return this.secret;
   }

   public Consumer<String> getChatCallback() {
      return this.chatCallback;
   }

   public Consumer<Void> getReconnectionCallback() {
      return this.reconnectionCallback;
   }

   public Consumer<Runnable> getSynchronizer() {
      return this.synchronizer;
   }
}
