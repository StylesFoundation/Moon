package com.moon.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moon.Moon;
import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.storage.dataset.Friend;
import com.moon.storage.dataset.FriendStore;
import com.moon.util.client.ChatUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class FriendStorage {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final Map<String, Friend> friendsByName = new HashMap<>();
   private final Map<EntityPlayer, Friend> cacheByPlayer = new HashMap<>();

   public void load(File directory) {
      File friendsDir = new File(directory, "friends");
      if (!friendsDir.exists()) {
         friendsDir.mkdirs();
      }

      File friendsFile = new File(friendsDir, "friends.json");
      if (!friendsFile.exists()) {
         Moon.getInstance().getLogger().info("No friends file was detected, skipping");
      } else {
         try {
            for (Friend friend : ((FriendStore)GSON.fromJson(new FileReader(friendsFile), FriendStore.class)).getFriends()) {
               this.friendsByName.put(friend.getName(), friend);
            }
         } catch (FileNotFoundException e) {
            Moon.getInstance().getLogger().error("Failed to load friends", new Object[]{e});
         }
      }
   }

   public void save(File directory) {
      File friendsDir = new File(directory, "friends");
      if (!friendsDir.exists()) {
         friendsDir.mkdirs();
      }

      File friendsFile = new File(friendsDir, "friends.json");
      String json = GSON.toJson(new FriendStore(this.friendsByName.values().toArray(new Friend[0])));

      try {
         Files.writeString(friendsFile.toPath(), json, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
      } catch (IOException e) {
         Moon.getInstance().getLogger().error("Failed to save friends", new Object[]{e});
      }
   }

   public void addFriend(String name, String alias) {
      if (this.friendsByName.containsKey(name)) {
         ChatUtil.sendMessage("<red>A friend with the given name already exists!");
      } else {
         Friend friend = new Friend(name);
         if (!alias.isEmpty()) {
            friend.setAlias(alias);
         }

         this.friendsByName.put(name, friend);
         this.cacheByPlayer.clear();
      }
   }

   public void removeFriend(String name) {
      if (!this.friendsByName.containsKey(name)) {
         ChatUtil.sendMessage("<red>The provided friend does not exist!");
      } else {
         this.friendsByName.remove(name);
         this.cacheByPlayer.clear();
      }
   }

   @Subscribe
   public void onTick(TickEvent event) {
      Minecraft mc = Minecraft.getMinecraft();
      if (event.getSequence() == TickEvent.Sequence.POST && mc.thePlayer != null && mc.thePlayer.ticksExisted <= 1) {
         this.cacheByPlayer.clear();
      }
   }

   public Friend getFriend(EntityPlayer player) {
      if (this.cacheByPlayer.containsKey(player)) {
         return this.cacheByPlayer.get(player);
      }

      for (Friend friend : this.friendsByName.values()) {
         if (player.getName().equals(friend.getName())) {
            this.cacheByPlayer.put(player, friend);
            return friend;
         }
      }

      this.cacheByPlayer.put(player, null);
      return null;
   }

   public boolean isFriend(EntityPlayer player) {
      if (this.cacheByPlayer.containsKey(player)) {
         return this.cacheByPlayer.get(player) != null;
      }

      for (Friend friend : this.friendsByName.values()) {
         if (player.getName().equals(friend.getName())) {
            this.cacheByPlayer.put(player, friend);
            return true;
         }
      }

      this.cacheByPlayer.put(player, null);
      return false;
   }

   public Map<String, Friend> getFriendsByName() {
      return this.friendsByName;
   }

   public Map<EntityPlayer, Friend> getCacheByPlayer() {
      return this.cacheByPlayer;
   }
}
