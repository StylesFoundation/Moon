package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class UserListWhitelistEntry extends UserListEntry<GameProfile> {
   public UserListWhitelistEntry(GameProfile gameProfile) {
      super(gameProfile);
   }

   public UserListWhitelistEntry(JsonObject jsonObject) {
      super(gameProfileFromJsonObject(jsonObject), jsonObject);
   }

   @Override
   protected void onSerialization(JsonObject jsonObject) {
      if (this.getValue() != null) {
         jsonObject.addProperty("uuid", this.getValue().getId() == null ? "" : this.getValue().getId().toString());
         jsonObject.addProperty("name", this.getValue().getName());
         super.onSerialization(jsonObject);
      }
   }

   private static GameProfile gameProfileFromJsonObject(JsonObject jsonObject) {
      if (jsonObject.has("uuid") && jsonObject.has("name")) {
         String var4 = jsonObject.get("uuid").getAsString();

         UUID var5;
         try {
            var5 = UUID.fromString(var4);
         } catch (Throwable var6) {
            return null;
         }

         return new GameProfile(var5, jsonObject.get("name").getAsString());
      } else {
         return null;
      }
   }
}
