package com.moon.util.microsoft.response;

import com.moon.ui.account.MicrosoftAuthData;
import java.util.UUID;

public class MinecraftProfile {
   private final UUID uuid;
   private final String name;
   private final MicrosoftAuthData auth;

   public UUID getUuid() {
      return this.uuid;
   }

   public String getName() {
      return this.name;
   }

   public MicrosoftAuthData getAuth() {
      return this.auth;
   }

   public MinecraftProfile(UUID uUID, String string, MicrosoftAuthData cW) {
      this.uuid = uUID;
      this.name = string;
      this.auth = cW;
   }
}
