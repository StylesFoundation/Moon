package com.moon.config;

import com.moon.Moon;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConfigManager {
   private final Map<String, Config> configs = new HashMap<>();
   private File directory;

   public void init(File rootDirectory) {
      this.directory = new File(rootDirectory, "configs");
      if (!this.directory.exists()) {
         this.directory.mkdirs();
      }

      File[] files = this.directory.listFiles();
      Set<String> onDisk = new HashSet<>();
      if (files != null) {
         for (File file : files) {
            String fileName = file.getName();
            if (fileName.endsWith(".json")) {
               String configName = fileName.substring(0, fileName.length() - ".json".length());
               onDisk.add(configName);
               this.configs.computeIfAbsent(configName, name -> new Config(name, null));
            }
         }
      }

      this.configs.keySet().retainAll(onDisk);
   }

   public void save(String name) {
      Config config = this.getConfig(name);
      if (config == null) {
         config = new Config(name, null);
         this.configs.put(name, config);
      }

      config.save(this.directory);
      this.configs.put(name, config);
   }

   public void delete(String name) {
      Config config = this.getConfig(name);
      if (config != null) {
         config.delete(this.directory);
         this.configs.remove(name);
      }
   }

   public Config getConfig(String name) {
      this.init(Moon.getInstance().getRootDirectory());
      return this.configs.get(name);
   }

   public Map<String, Config> getConfigs() {
      return this.configs;
   }

   public File getDirectory() {
      return this.directory;
   }
}
