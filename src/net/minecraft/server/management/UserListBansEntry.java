package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.Date;
import java.util.UUID;

public class UserListBansEntry extends BanEntry<GameProfile> {
   public UserListBansEntry(GameProfile gameProfile) {
      this(gameProfile, null, null, null, null);
   }

   public UserListBansEntry(GameProfile gameProfile, Date date2, String string3, Date date4, String string5) {
      super(gameProfile, date4, string3, date4, string5);
   }

   public UserListBansEntry(JsonObject jsonObject) {
      super(toGameProfile(jsonObject), jsonObject);
   }

   @Override
   protected void onSerialization(JsonObject jsonObject) {
      if (this.getValue() != null) {
         jsonObject.addProperty("uuid", this.getValue().getId() == null ? "" : this.getValue().getId().toString());
         jsonObject.addProperty("name", this.getValue().getName());
         super.onSerialization(jsonObject);
      }
   }

   private static GameProfile toGameProfile(JsonObject jsonObject) {
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
