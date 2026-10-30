package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class UserListOpsEntry extends UserListEntry<GameProfile> {
   private final int permissionLevel;
   private final boolean bypassesPlayerLimit;

   public UserListOpsEntry(GameProfile gameProfile, int integer, boolean boolean3) {
      super(gameProfile);
      this.permissionLevel = integer;
      this.bypassesPlayerLimit = boolean3;
   }

   public UserListOpsEntry(JsonObject jsonObject) {
      super(constructProfile(jsonObject), jsonObject);
      this.permissionLevel = jsonObject.has("level") ? jsonObject.get("level").getAsInt() : 0;
      this.bypassesPlayerLimit = jsonObject.has("bypassesPlayerLimit") && jsonObject.get("bypassesPlayerLimit").getAsBoolean();
   }

   public int getPermissionLevel() {
      return this.permissionLevel;
   }

   public boolean bypassesPlayerLimit() {
      return this.bypassesPlayerLimit;
   }

   @Override
   protected void onSerialization(JsonObject jsonObject) {
      if (this.getValue() != null) {
         jsonObject.addProperty("uuid", this.getValue().getId() == null ? "" : this.getValue().getId().toString());
         jsonObject.addProperty("name", this.getValue().getName());
         super.onSerialization(jsonObject);
         jsonObject.addProperty("level", this.permissionLevel);
         jsonObject.addProperty("bypassesPlayerLimit", this.bypassesPlayerLimit);
      }
   }

   private static GameProfile constructProfile(JsonObject jsonObject) {
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
