package com.moon.util.discord;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class GameStateText implements DiscordRpcText {
   private static final Minecraft minecraft = Minecraft.getMinecraft();

   @Override
   public String getText() {
      String state = "In menu";
      ServerData serverData = minecraft.getCurrentServerData();
      if (!minecraft.isGamePaused()) {
         if (minecraft.isSingleplayer()) {
            state = "In single-player";
         } else if (minecraft.thePlayer != null && minecraft.theWorld != null && serverData != null) {
            state = "Playing on " + serverData.serverIP;
         }
      }

      return state;
   }
}
