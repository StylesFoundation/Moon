package net.minecraft.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Type;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonUtils;

public class ServerStatusResponse {
   private IChatComponent serverMotd;
   private ServerStatusResponse.PlayerCountData playerCount;
   private ServerStatusResponse.MinecraftProtocolVersionIdentifier protocolVersion;
   private String favicon;

   public IChatComponent getServerDescription() {
      return this.serverMotd;
   }

   public void setServerDescription(IChatComponent iChatComponent) {
      this.serverMotd = iChatComponent;
   }

   public ServerStatusResponse.PlayerCountData getPlayerCountData() {
      return this.playerCount;
   }

   public void setPlayerCountData(ServerStatusResponse.PlayerCountData playerCountData) {
      this.playerCount = playerCountData;
   }

   public ServerStatusResponse.MinecraftProtocolVersionIdentifier getProtocolVersionInfo() {
      return this.protocolVersion;
   }

   public void setProtocolVersionInfo(ServerStatusResponse.MinecraftProtocolVersionIdentifier minecraftProtocolVersionIdentifier) {
      this.protocolVersion = minecraftProtocolVersionIdentifier;
   }

   public void setFavicon(String string) {
      this.favicon = string;
   }

   public String getFavicon() {
      return this.favicon;
   }

   public static class MinecraftProtocolVersionIdentifier {
      private final String name;
      private final int protocol;

      public MinecraftProtocolVersionIdentifier(String string, int integer) {
         this.name = string;
         this.protocol = integer;
      }

      public String getName() {
         return this.name;
      }

      public int getProtocol() {
         return this.protocol;
      }

      public static class Serializer
         implements JsonDeserializer<ServerStatusResponse.MinecraftProtocolVersionIdentifier>,
         JsonSerializer<ServerStatusResponse.MinecraftProtocolVersionIdentifier> {
         public ServerStatusResponse.MinecraftProtocolVersionIdentifier deserialize(
            JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext
         ) throws JsonParseException {
            JsonObject var4 = JsonUtils.Method746(jsonElement, "version");
            return new ServerStatusResponse.MinecraftProtocolVersionIdentifier(JsonUtils.Method741(var4, "name"), JsonUtils.Method745(var4, "protocol"));
         }

         public JsonElement serialize(
            ServerStatusResponse.MinecraftProtocolVersionIdentifier minecraftProtocolVersionIdentifier,
            Type type,
            JsonSerializationContext jsonSerializationContext
         ) {
            JsonObject var4 = new JsonObject();
            var4.addProperty("name", minecraftProtocolVersionIdentifier.getName());
            var4.addProperty("protocol", minecraftProtocolVersionIdentifier.getProtocol());
            return var4;
         }
      }
   }

   public static class PlayerCountData {
      private final int maxPlayers;
      private final int onlinePlayerCount;
      private GameProfile[] players;

      public PlayerCountData(int integer1, int integer2) {
         this.maxPlayers = integer1;
         this.onlinePlayerCount = integer2;
      }

      public int getMaxPlayers() {
         return this.maxPlayers;
      }

      public int getOnlinePlayerCount() {
         return this.onlinePlayerCount;
      }

      public GameProfile[] getPlayers() {
         return this.players;
      }

      public void setPlayers(GameProfile[] arr) {
         this.players = arr;
      }
   }

   public static class Serializer implements JsonDeserializer<ServerStatusResponse>, JsonSerializer<ServerStatusResponse> {
      public ServerStatusResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject var7 = JsonUtils.Method746(jsonElement, "status");
         ServerStatusResponse var8 = new ServerStatusResponse();
         if (var7.has("description")) {
            var8.setServerDescription((IChatComponent)jsonDeserializationContext.deserialize(var7.get("description"), IChatComponent.class));
         }

         if (var7.has("players")) {
            var8.setPlayerCountData(
               (ServerStatusResponse.PlayerCountData)jsonDeserializationContext.deserialize(var7.get("players"), ServerStatusResponse.PlayerCountData.class)
            );
         }

         if (var7.has("version")) {
            var8.setProtocolVersionInfo(
               (ServerStatusResponse.MinecraftProtocolVersionIdentifier)jsonDeserializationContext.deserialize(
                  var7.get("version"), ServerStatusResponse.MinecraftProtocolVersionIdentifier.class
               )
            );
         }

         if (var7.has("favicon")) {
            var8.setFavicon(JsonUtils.Method741(var7, "favicon"));
         }

         return var8;
      }

      public JsonElement serialize(ServerStatusResponse serverStatusResponse, Type type, JsonSerializationContext jsonSerializationContext) {
         JsonObject var7 = new JsonObject();
         if (serverStatusResponse.getServerDescription() != null) {
            var7.add("description", jsonSerializationContext.serialize(serverStatusResponse.getServerDescription()));
         }

         if (serverStatusResponse.getPlayerCountData() != null) {
            var7.add("players", jsonSerializationContext.serialize(serverStatusResponse.getPlayerCountData()));
         }

         if (serverStatusResponse.getProtocolVersionInfo() != null) {
            var7.add("version", jsonSerializationContext.serialize(serverStatusResponse.getProtocolVersionInfo()));
         }

         if (serverStatusResponse.getFavicon() != null) {
            var7.addProperty("favicon", serverStatusResponse.getFavicon());
         }

         return var7;
      }
   }
}
