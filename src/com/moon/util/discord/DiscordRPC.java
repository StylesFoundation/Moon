package com.moon.util.discord;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class DiscordRPC {
   private static final TaggedLogger logger = Logger.tag("rpc");
   private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
   private final DiscordRpcConfig config;
   private boolean initialized;
   private boolean running;
   private long startTimestamp;

   public void display() {
      if (!this.running) {
         logger.info("Connecting to discord...");
         DiscordEventHandlers handlers = new DiscordEventHandlers.Builder()
            .setReadyEventHandler(
               user -> {
                  String userId = user.userId;
                  String discriminator = user.discriminator;
                  String username = user.username;
                  logger.info("Discord RPC connected for " + username + "#" + discriminator + " (" + userId + ")");
                  if (this.config.isAutoRefresh()) {
                     this.executor
                        .scheduleAtFixedRate(this::updatePresence, this.config.getRefreshInterval(), this.config.getRefreshInterval(), TimeUnit.MILLISECONDS);
                  }
               }
            )
            .setDisconnectedEventHandler((errorCode, message) -> logger.info("Discord RPC disconnected " + errorCode + " (" + message + ")"))
            .setErroredEventHandler((errorCode, message) -> logger.error("Discord RPC error " + errorCode + " (" + message + ")"))
            .build();
         if (!this.initialized) {
            net.arikia.dev.drpc.DiscordRPC.discordInitialize(String.valueOf(this.config.getApplicationId()), handlers, true);
            this.executor.scheduleAtFixedRate(() -> {
               logger.debug("Polling...");

               try {
                  net.arikia.dev.drpc.DiscordRPC.discordRunCallbacks();
               } catch (Exception e) {
                  logger.error(e);
               }
            }, 50L, 50L, TimeUnit.MILLISECONDS);
            this.initialized = true;
         }

         this.running = true;
         this.startTimestamp = System.currentTimeMillis();
      }
   }

   public void updatePresence() {
      if (this.running) {
         try {
            logger.debug("Updating rich presence...");
            DiscordRpcImage bigImage = this.config.getBigImage();
            DiscordRpcImage smallImage = this.config.getSmallImage();
            DiscordRichPresence.Builder presenceBuilder = new DiscordRichPresence.Builder(this.config.getState().getText())
               .setDetails(this.config.getDescription().getText())
               .setStartTimestamps(this.startTimestamp);
            if (bigImage != null) {
               presenceBuilder = presenceBuilder.setBigImage(bigImage.getKey(), bigImage.getTooltip().get());
            }

            if (smallImage != null) {
               presenceBuilder = presenceBuilder.setSmallImage(smallImage.getKey(), smallImage.getTooltip().get());
            }

            net.arikia.dev.drpc.DiscordRPC.discordUpdatePresence(presenceBuilder.build());
         } catch (Exception e) {
            logger.error(e);
         }
      }
   }

   public void shutdown() {
      if (this.running) {
         logger.info("Stopping discord rpc...");
         net.arikia.dev.drpc.DiscordRPC.discordClearPresence();
         this.running = false;
      }
   }

   private DiscordRPC(DiscordRpcConfig config) {
      this.config = config;
   }

   public static DiscordRPC of(DiscordRpcConfig config) {
      return new DiscordRPC(config);
   }
}
