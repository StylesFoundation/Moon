package com.moon.connection;

import com.aayushatharva.brotli4j.Brotli4jLoader;
import com.moon.connection.cipher.GatewayCipher;
import com.moon.connection.client.GatewayClient;
import com.moon.connection.handlers.impl.ClientboundAuthenticationResponseHandler;
import com.moon.connection.handlers.impl.ClientboundChatBroadcastHandler;
import com.moon.connection.handlers.impl.ClientboundChatErrorHandler;
import com.moon.connection.handlers.impl.ClientboundUpdateUserSyncHandler;
import com.moon.connection.packet.PacketManager;
import com.moon.connection.services.GatewayServices;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class Gateway {
   private final TaggedLogger logger = Logger.tag("gateway");
   private final ExecutorService executor = Executors.newSingleThreadExecutor();
   private final GatewayServices services = new GatewayServices(this);
   private final GatewayClientConfig config;
   private GatewayClient client;
   private PacketManager packetManager;
   private GatewayCipher cipher;

   public void start() {
      Brotli4jLoader.ensureAvailability();
      this.packetManager = new PacketManager();
      this.packetManager.register(new ClientboundAuthenticationResponseHandler(this));
      this.packetManager.register(new ClientboundChatErrorHandler(this));
      this.packetManager.register(new ClientboundChatBroadcastHandler(this));
      this.packetManager.register(new ClientboundUpdateUserSyncHandler(this));
      this.connect();
   }

   public void connect() {
      this.client = new GatewayClient(this, this.config.getConnectionUrl());
      this.client.connect();
   }

   public void queueReconnect() {
      this.executor.submit(() -> {
         this.logger.info("Disconnected from socket, reconnecting in 5 seconds...");
         if (this.client != null) {
            this.client.close();
         }

         try {
            Thread.sleep(5000L);
            this.connect();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      });
   }

   public TaggedLogger getLogger() {
      return this.logger;
   }

   public ExecutorService getExecutor() {
      return this.executor;
   }

   public GatewayServices getServices() {
      return this.services;
   }

   public GatewayClientConfig getConfig() {
      return this.config;
   }

   public GatewayClient getClient() {
      return this.client;
   }

   public PacketManager getPacketManager() {
      return this.packetManager;
   }

   public GatewayCipher getCipher() {
      return this.cipher;
   }

   private Gateway(GatewayClientConfig config) {
      this.config = config;
   }

   public static Gateway create(GatewayClientConfig config) {
      return new Gateway(config);
   }

   public Gateway cipher(GatewayCipher cipher) {
      this.cipher = cipher;
      return this;
   }
}
