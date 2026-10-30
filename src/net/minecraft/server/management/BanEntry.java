package net.minecraft.server.management;

import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BanEntry<T> extends UserListEntry<T> {
   public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   protected final Date banStartDate;
   protected final String bannedBy;
   protected final Date banEndDate;
   protected final String reason;

   public BanEntry(T object, Date date2, String string3, Date date4, String string5) {
      super((T)object);
      this.banStartDate = date2 == null ? new Date() : date2;
      this.bannedBy = string3 == null ? "(Unknown)" : string3;
      this.banEndDate = date4;
      this.reason = string5 == null ? "Banned by an operator." : string5;
   }

   protected BanEntry(T object, JsonObject jsonObject) {
      super((T)object, jsonObject);

      Date var6;
      try {
         var6 = jsonObject.has("created") ? dateFormat.parse(jsonObject.get("created").getAsString()) : new Date();
      } catch (ParseException var9) {
         var6 = new Date();
      }

      this.banStartDate = var6;
      this.bannedBy = jsonObject.has("source") ? jsonObject.get("source").getAsString() : "(Unknown)";

      Date var7;
      try {
         var7 = jsonObject.has("expires") ? dateFormat.parse(jsonObject.get("expires").getAsString()) : null;
      } catch (ParseException var8) {
         var7 = null;
      }

      this.banEndDate = var7;
      this.reason = jsonObject.has("reason") ? jsonObject.get("reason").getAsString() : "Banned by an operator.";
   }

   public Date getBanEndDate() {
      return this.banEndDate;
   }

   public String getBanReason() {
      return this.reason;
   }

   @Override
   boolean hasBanExpired() {
      return this.banEndDate != null && this.banEndDate.before(new Date());
   }

   @Override
   protected void onSerialization(JsonObject jsonObject) {
      jsonObject.addProperty("created", dateFormat.format(this.banStartDate));
      jsonObject.addProperty("source", this.bannedBy);
      jsonObject.addProperty("expires", this.banEndDate == null ? "forever" : dateFormat.format(this.banEndDate));
      jsonObject.addProperty("reason", this.reason);
   }
}
