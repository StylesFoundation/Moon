package net.minecraft.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Map;
import java.util.UUID;

public class Session {
   private final String username;
   private final String playerID;
   private final String token;
   private final Session.Type sessionType;

   public Session(String string1, String string2, String string3, String string4) {
      this.username = string1;
      this.playerID = string2;
      this.token = string3;
      this.sessionType = Session.Type.setSessionType(string4);
   }

   public String getSessionID() {
      return "token:" + this.token + ":" + this.playerID;
   }

   public String getPlayerID() {
      return this.playerID;
   }

   public String getUsername() {
      return this.username;
   }

   public String getToken() {
      return this.token;
   }

   public GameProfile getProfile() {
      try {
         UUID var1 = UUIDTypeAdapter.fromString(this.getPlayerID());
         return new GameProfile(var1, this.getUsername());
      } catch (IllegalArgumentException var2) {
         return new GameProfile(null, this.getUsername());
      }
   }

   public Session.Type getSessionType() {
      return this.sessionType;
   }

   public enum Type {
      LEGACY("legacy"),
      MOJANG("mojang");

      private static final Map<String, Session.Type> SESSION_TYPES = Maps.newHashMap();
      private final String sessionType;

      Type(String string3) {
         this.sessionType = string3;
      }

      public static Session.Type setSessionType(String string) {
         return SESSION_TYPES.get(string.toLowerCase());
      }

      private static Session.Type[] Method990() {
         return new Session.Type[]{LEGACY, MOJANG};
      }

      static {
         for (Session.Type var3 : values()) {
            SESSION_TYPES.put(var3.sessionType, var3);
         }
      }
   }
}
