package net.minecraft.server.management;

import com.google.gson.JsonObject;
import java.util.Date;

public class IPBanEntry extends BanEntry<String> {
   public IPBanEntry(String string) {
      this(string, null, null, null, null);
   }

   public IPBanEntry(String string1, Date date2, String string3, Date date4, String string5) {
      super(string1, date2, string3, date4, string5);
   }

   public IPBanEntry(JsonObject jsonObject) {
      super(getIPFromJson(jsonObject), jsonObject);
   }

   private static String getIPFromJson(JsonObject jsonObject) {
      return jsonObject.has("ip") ? jsonObject.get("ip").getAsString() : null;
   }

   @Override
   protected void onSerialization(JsonObject jsonObject) {
      if (this.getValue() != null) {
         jsonObject.addProperty("ip", this.getValue());
         super.onSerialization(jsonObject);
      }
   }
}
