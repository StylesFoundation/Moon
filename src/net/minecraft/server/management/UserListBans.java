package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class UserListBans extends UserList<GameProfile, UserListBansEntry> {
   public UserListBans(File file) {
      super(file);
   }

   @Override
   protected UserListEntry<GameProfile> createEntry(JsonObject jsonObject) {
      return new UserListBansEntry(jsonObject);
   }

   public boolean isBanned(GameProfile gameProfile) {
      return this.hasEntry(gameProfile);
   }

   @Override
   public String[] getKeys() {
      String[] var4 = new String[this.getValues().size()];
      int var5 = 0;

      for (UserListBansEntry var7 : this.getValues().values()) {
         var4[var5++] = var7.getValue().getName();
      }

      return var4;
   }

   protected String getObjectKey(GameProfile gameProfile) {
      return gameProfile.getId().toString();
   }

   public GameProfile isUsernameBanned(String string) {
      for (UserListBansEntry var6 : this.getValues().values()) {
         if (string.equalsIgnoreCase(var6.getValue().getName())) {
            return var6.getValue();
         }
      }

      return null;
   }
}
