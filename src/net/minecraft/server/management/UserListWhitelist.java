package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class UserListWhitelist extends UserList<GameProfile, UserListWhitelistEntry> {
   public UserListWhitelist(File file) {
      super(file);
   }

   @Override
   protected UserListEntry<GameProfile> createEntry(JsonObject jsonObject) {
      return new UserListWhitelistEntry(jsonObject);
   }

   @Override
   public String[] getKeys() {
      String[] var4 = new String[this.getValues().size()];
      int var5 = 0;

      for (UserListWhitelistEntry var7 : this.getValues().values()) {
         var4[var5++] = var7.getValue().getName();
      }

      return var4;
   }

   protected String getObjectKey(GameProfile gameProfile) {
      return gameProfile.getId().toString();
   }

   public GameProfile getBannedProfile(String string) {
      for (UserListWhitelistEntry var6 : this.getValues().values()) {
         if (string.equalsIgnoreCase(var6.getValue().getName())) {
            return var6.getValue();
         }
      }

      return null;
   }
}
