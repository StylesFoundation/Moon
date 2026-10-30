package com.moon.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.moon.Moon;
import com.moon.commons.io.AtomicFiles;
import com.moon.module.Module;
import com.moon.module.ModuleRegistry;
import com.moon.util.client.ChatUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Config {
   private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
   @SerializedName("name")
   private String name;
   @SerializedName("modules")
   private JsonArray modules;

   public void save(File directory) {
      File file = new File(directory, this.name + ".json");
      ModuleRegistry registry = Moon.getInstance().getModuleManager().getRegistry();
      JsonArray modulesArray = new JsonArray();

      for (Module module : registry.getModules()) {
         modulesArray.add(module.serialize());
      }

      this.modules = modulesArray;
      String json = gson.toJson(this);

      try {
         AtomicFiles.writeString(file.toPath(), json);
      } catch (IOException e) {
         ChatUtil.sendMessage("<red>Failed to save the configuration " + this.name + " (" + e.getMessage() + ")");
      }
   }

   public void load(File directory) {
      File file = new File(directory, this.name + ".json");
      if (!Files.exists(file.toPath())) {
         ChatUtil.sendMessage("<red>Failed to load configuration " + this.name + " as it could not be found on the disk.");
      } else {
         Config config;
         try {
            config = (Config)gson.fromJson(Files.readString(file.toPath()), Config.class);
         } catch (IOException | RuntimeException e) {
            ChatUtil.sendMessage("<red>Unable to read configuration file " + this.name + " (" + e.getMessage() + ")");
            return;
         }

         if (config == null || config.getModules() == null) {
            ChatUtil.sendMessage("<red>Configuration file " + this.name + " is empty or corrupted.");
            return;
         }

         this.modules = config.getModules();
         ModuleRegistry registry = Moon.getInstance().getModuleManager().getRegistry();

         for (Module module : Moon.getInstance().getModuleManager().getRegistry().getModules()) {
            module.setEnabled(false);
         }

         if (this.modules.isJsonArray()) {
            for (JsonElement element : this.modules.getAsJsonArray()) {
               if (element.isJsonObject() && element.getAsJsonObject().has("name")) {
                  JsonObject jsonObject = element.getAsJsonObject();
                  Module module = registry.getModuleByName(jsonObject.get("name").getAsString().toLowerCase());
                  if (module != null) {
                     try {
                        module.deserialize(jsonObject, false);
                     } catch (RuntimeException e) {
                        Moon.getInstance().getLogger().error("Unable to load " + module.getDisplayName() + " module from config " + this.name);
                     }
                  }
               }
            }
         }
      }
   }

   public void delete(File directory) {
      File file = new File(directory, this.name + ".json");
      if (!Files.exists(file.toPath())) {
         ChatUtil.sendMessage("<red>Failed to delete configuration " + this.name + " as it could not be found on the disk.");
      } else {
         try {
            Files.delete(file.toPath());
         } catch (IOException e) {
            ChatUtil.sendMessage("<red>Failed to delete configuration " + this.name + " (" + e.getMessage() + ")");
         }
      }
   }

   public String getName() {
      return this.name;
   }

   public JsonArray getModules() {
      return this.modules;
   }

   public Config name(String name) {
      this.name = name;
      return this;
   }

   public Config modules(JsonArray modules) {
      this.modules = modules;
      return this;
   }

   public Config(String name, JsonArray modules) {
      this.name = name;
      this.modules = modules;
   }
}
