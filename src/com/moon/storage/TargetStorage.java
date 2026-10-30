package com.moon.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moon.Moon;
import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.storage.dataset.Target;
import com.moon.storage.dataset.TargetStore;
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

public class TargetStorage {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final Map<String, Target> targetsByName = new HashMap<>();
   private final Map<EntityPlayer, Target> cacheByPlayer = new HashMap<>();

   public void load(File directory) {
      File targetsDir = new File(directory, "targets");
      if (!targetsDir.exists()) {
         targetsDir.mkdirs();
      }

      File targetsFile = new File(targetsDir, "targets.json");
      if (!targetsFile.exists()) {
         Moon.getInstance().getLogger().info("No targets file was detected, skipping");
      } else {
         try {
            for (Target target : ((TargetStore)GSON.fromJson(new FileReader(targetsFile), TargetStore.class)).getTargets()) {
               this.targetsByName.put(target.getName(), target);
            }
         } catch (FileNotFoundException e) {
            Moon.getInstance().getLogger().error("Failed to load targets", new Object[]{e});
         }
      }
   }

   public void save(File directory) {
      File targetsDir = new File(directory, "targets");
      if (!targetsDir.exists()) {
         targetsDir.mkdirs();
      }

      File targetsFile = new File(targetsDir, "targets.json");
      String json = GSON.toJson(new TargetStore(this.targetsByName.values().toArray(new Target[0])));

      try {
         Files.writeString(targetsFile.toPath(), json, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
      } catch (IOException e) {
         Moon.getInstance().getLogger().error("Failed to save targets", new Object[]{e});
      }
   }

   public void addTarget(String name) {
      if (this.targetsByName.containsKey(name)) {
         ChatUtil.sendMessage("<red>A target with the given name already exists!");
      } else {
         Target target = new Target(name);
         this.targetsByName.put(name, target);
         this.cacheByPlayer.clear();
      }
   }

   public void removeTarget(String name) {
      if (!this.targetsByName.containsKey(name)) {
         ChatUtil.sendMessage("<red>The provided target does not exist!");
      } else {
         this.targetsByName.remove(name);
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

   public Target getTarget(EntityPlayer player) {
      if (this.cacheByPlayer.containsKey(player)) {
         return this.cacheByPlayer.get(player);
      }

      for (Target target : this.targetsByName.values()) {
         if (player.getName().equals(target.getName())) {
            this.cacheByPlayer.put(player, target);
            return target;
         }
      }

      this.cacheByPlayer.put(player, null);
      return null;
   }

   public boolean isTarget(EntityPlayer player) {
      if (this.cacheByPlayer.containsKey(player)) {
         return this.cacheByPlayer.get(player) != null;
      }

      for (Target target : this.targetsByName.values()) {
         if (player.getName().equals(target.getName())) {
            this.cacheByPlayer.put(player, target);
            return true;
         }
      }

      this.cacheByPlayer.put(player, null);
      return false;
   }

   public Map<String, Target> getTargetsByName() {
      return this.targetsByName;
   }

   public Map<EntityPlayer, Target> getCacheByPlayer() {
      return this.cacheByPlayer;
   }
}
