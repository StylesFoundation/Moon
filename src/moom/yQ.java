package moom;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Type;
import java.util.UUID;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.util.JsonUtils;

public class yQ implements JsonDeserializer<ServerStatusResponse.PlayerCountData>, JsonSerializer<ServerStatusResponse.PlayerCountData> {
   public ServerStatusResponse.PlayerCountData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
      JsonObject var7 = JsonUtils.Method746(jsonElement, "players");
      ServerStatusResponse.PlayerCountData var8 = new ServerStatusResponse.PlayerCountData(
         JsonUtils.Method745(var7, "max"), JsonUtils.Method745(var7, "online")
      );
      if (JsonUtils.isJsonArray(var7, "sample")) {
         JsonArray var9 = JsonUtils.Method749(var7, "sample");
         if (var9.size() > 0) {
            GameProfile[] var10 = new GameProfile[var9.size()];

            for (int var11 = 0; var11 < var10.length; var11++) {
               JsonObject var12 = JsonUtils.Method746(var9.get(var11), "player[" + var11 + "]");
               String var13 = JsonUtils.Method741(var12, "id");
               var10[var11] = new GameProfile(UUID.fromString(var13), JsonUtils.Method741(var12, "name"));
            }

            var8.setPlayers(var10);
         }
      }

      return var8;
   }

   public JsonElement serialize(ServerStatusResponse.PlayerCountData playerCountData, Type type, JsonSerializationContext jsonSerializationContext) {
      JsonObject var7 = new JsonObject();
      var7.addProperty("max", playerCountData.getMaxPlayers());
      var7.addProperty("online", playerCountData.getOnlinePlayerCount());
      if (playerCountData.getPlayers() != null && playerCountData.getPlayers().length > 0) {
         JsonArray var8 = new JsonArray();

         for (int var9 = 0; var9 < playerCountData.getPlayers().length; var9++) {
            JsonObject var10 = new JsonObject();
            UUID var11 = playerCountData.getPlayers()[var9].getId();
            var10.addProperty("id", var11 == null ? "" : var11.toString());
            var10.addProperty("name", playerCountData.getPlayers()[var9].getName());
            var8.add(var10);
         }

         var7.add("sample", var8);
      }

      return var7;
   }
}
