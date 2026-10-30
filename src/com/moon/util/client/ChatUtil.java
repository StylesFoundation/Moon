package com.moon.util.client;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class ChatUtil {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
   private static final GsonComponentSerializer GSON_SERIALIZER = GsonComponentSerializer.colorDownsamplingGson();

   public static void sendMessage(String message) {
      String formattedMessage = "<aqua><bold>Moon Client</bold><aqua> <gray><bold>»</bold> <white>" + message;
      Minecraft.getMinecraft().Method1015(() -> {
         if (mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent((String)GSON_SERIALIZER.serialize(MINI_MESSAGE.deserialize(formattedMessage))));
         }
      });
   }

   public static void sendIrcMessage(String message) {
      String formattedMessage = "<aqua><bold>IRC</bold><aqua> <gray><bold>»</bold> <white>" + message;
      Minecraft.getMinecraft().Method1015(() -> {
         if (mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent((String)GSON_SERIALIZER.serialize(MINI_MESSAGE.deserialize(formattedMessage))));
         }
      });
   }

   public static void sendLegacyMessage(String message) {
      String formattedMessage = "§b§lMoon Client §7§l» §f" + message;
      Minecraft.getMinecraft().Method1015(() -> {
         if (mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(new ChatComponentText(formattedMessage));
         }
      });
   }
}
