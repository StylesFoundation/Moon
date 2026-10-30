package com.moon.handlers;

import com.moon.Moon;
import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.network.ConnectServerEvent;
import com.moon.events.subscription.Subscribe;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class ServerSyncHandler {
   private final ExecutorService executorService = Executors.newSingleThreadExecutor();
   private static final Minecraft minecraft = Minecraft.getMinecraft();
   private String lastUsername = "";
   private String lastServerIp = "";
   private long lastSyncTime;

   @Subscribe
   public void onTick(ConnectServerEvent event) {
      if (!event.isLocalServer()) {
      }
   }

   @Subscribe
   public void onConnectServerEvent(TickEvent event) {
      long currentTime = System.currentTimeMillis();
      if (currentTime - this.lastSyncTime > 1000L) {
         this.lastSyncTime = currentTime;
         this.executorService.submit(() -> {
            ServerData serverData = minecraft.getCurrentServerData();
            if (minecraft.thePlayer != null && serverData != null) {
            }

            String currentServerIp = minecraft.getCurrentServerData() != null ? minecraft.getCurrentServerData().serverIP : "Singleplayer";
            if (!minecraft.Method1016().getUsername().equals(this.lastUsername) || !this.lastServerIp.equals(currentServerIp)) {
               this.lastUsername = minecraft.Method1016().getUsername();
               this.lastServerIp = currentServerIp;
            }
         });
      }
   }

   public void resetSyncState() {
      this.lastServerIp = null;
      this.lastUsername = null;
   }
}
