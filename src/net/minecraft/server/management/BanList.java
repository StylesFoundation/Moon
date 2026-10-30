package net.minecraft.server.management;

import com.google.gson.JsonObject;
import java.io.File;
import java.net.SocketAddress;

public class BanList extends UserList<String, IPBanEntry> {
   public BanList(File file) {
      super(file);
   }

   @Override
   protected UserListEntry<String> createEntry(JsonObject jsonObject) {
      return new IPBanEntry(jsonObject);
   }

   public boolean isBanned(SocketAddress socketAddress) {
      String var2 = this.addressToString(socketAddress);
      return this.hasEntry(var2);
   }

   public IPBanEntry getBanEntry(SocketAddress socketAddress) {
      String var2 = this.addressToString(socketAddress);
      return this.getEntry(var2);
   }

   private String addressToString(SocketAddress socketAddress) {
      String var5 = socketAddress.toString();
      if (var5.contains("/")) {
         var5 = var5.substring(var5.indexOf(47) + 1);
      }

      if (var5.contains(":")) {
         var5 = var5.substring(0, var5.indexOf(58));
      }

      return var5;
   }
}
