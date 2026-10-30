package net.minecraft.server.management;

import com.google.gson.JsonObject;

public class UserListEntry<T> {
   private final T value;

   public UserListEntry(T object) {
      this.value = (T)object;
   }

   protected UserListEntry(T object, JsonObject jsonObject) {
      this.value = (T)object;
   }

   T getValue() {
      return this.value;
   }

   boolean hasBanExpired() {
      return false;
   }

   protected void onSerialization(JsonObject jsonObject) {
   }
}
