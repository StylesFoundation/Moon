package com.moon.connection.services;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.moon.connection.Gateway;
import com.moon.connection.protobuf.sync.ServerboundUpdateGameSync;
import com.moon.connection.protobuf.sync.SynchronizedUser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class UserSyncService {
   private final Map<String, SynchronizedUser> synchronizedUsers = new HashMap<>();
   private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
   private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.legacy('§');
   private final Cache<String, String> renderCache = CacheBuilder.newBuilder().expireAfterAccess(1L, TimeUnit.MINUTES).build();
   private final Gateway gateway;

   public void sendGameSync(String gameName, String gameServer) {
      this.gateway.getClient().send(ServerboundUpdateGameSync.newBuilder().setGameName(gameName).setGameServer(gameServer).build());
   }

   public void updateSynchronizedUsers(List<SynchronizedUser> users) {
      this.synchronizedUsers.clear();
      users.forEach(user -> this.synchronizedUsers.put(user.getGameName(), user));
   }

   public String renderMessage(String message) {
      String[] words = message.split(" ");
      List<String> parts = new ArrayList<>();

      for (String word : words) {
         String cleanWord = word.replaceAll("§[0-9]", "").replaceAll("§[a-z]", "");
         if (this.synchronizedUsers.containsKey(cleanWord)) {
            SynchronizedUser user = this.synchronizedUsers.get(cleanWord);
            String formatted = String.format("<%s>%s</%s> <gray>(%s)</gray>", user.getColor(), user.getName(), user.getColor(), user.getGameName());
            String rendered = this.renderCache.getIfPresent(formatted);
            if (rendered == null) {
               rendered = LEGACY_SERIALIZER.serialize(MINI_MESSAGE.deserialize(formatted));
               this.renderCache.put(formatted, rendered);
            }

            parts.add(rendered);
         } else {
            parts.add(word);
         }
      }

      return String.join(" ", parts);
   }

   public UserSyncService(Gateway gateway) {
      this.gateway = gateway;
   }

   public Map<String, SynchronizedUser> getSynchronizedUsers() {
      return this.synchronizedUsers;
   }
}
