package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class UserListOps extends UserList<GameProfile, UserListOpsEntry> {
   public UserListOps(File file) {
      super(file);
   }

   @Override
   protected UserListEntry<GameProfile> createEntry(JsonObject jsonObject) {
      return new UserListOpsEntry(jsonObject);
   }

   @Override
   public String[] getKeys() {
      String[] var4 = new String[this.getValues().size()];
      int var5 = 0;

      for (UserListOpsEntry var7 : this.getValues().values()) {
         var4[var5++] = var7.getValue().getName();
      }

      return var4;
   }

   public boolean bypassesPlayerLimit(GameProfile gameProfile) {
      UserListOpsEntry var5 = this.getEntry(gameProfile);
      return var5 != null && var5.bypassesPlayerLimit();
   }

   protected String getObjectKey(GameProfile gameProfile) {
      return gameProfile.getId().toString();
   }

   public GameProfile getGameProfileFromName(String string) {
      for (UserListOpsEntry var6 : this.getValues().values()) {
         if (string.equalsIgnoreCase(var6.getValue().getName())) {
            return var6.getValue();
         }
      }

      return null;
   }
}
