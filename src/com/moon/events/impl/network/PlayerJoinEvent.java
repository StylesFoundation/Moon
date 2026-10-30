package com.moon.events.impl.network;

import com.mojang.authlib.GameProfile;
import com.moon.events.Event;

public class PlayerJoinEvent extends Event {
   private GameProfile gameProfile;
   private long ping;

   public GameProfile getGameProfile() {
      return this.gameProfile;
   }

   public long getPing() {
      return this.ping;
   }

   public PlayerJoinEvent(GameProfile gameProfile, long ping) {
      this.gameProfile = gameProfile;
      this.ping = ping;
   }
}
