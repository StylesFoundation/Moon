package com.moon.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moon.Moon;
import com.moon.commons.io.AtomicFiles;
import com.moon.module.Module;
import com.moon.module.ModuleRegistry;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import org.lwjgl.opengl.Display;

public class ModuleStorage {
   private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final Path modulesFile;

   public ModuleStorage(Path directory) {
      try {
         if (Files.notExists(directory)) {
            Files.createDirectory(directory);
         }
      } catch (IOException e) {
         Moon.getInstance().getLogger().error("Could not create storage file, do you have enough permissions on the host pc?", new Object[]{e});
         e.printStackTrace();
      }

      this.modulesFile = directory.resolve("modules.json");
   }

   public void save(Collection<Module> modules) {
      JsonElement[] elements = modules.stream().map(Module::serialize).toArray(JsonElement[]::new);
      if (elements.length == 0) {
         return;
      }

      String json = this.GSON.toJson(elements);

      try {
         AtomicFiles.writeString(this.modulesFile, json);
      } catch (IOException e) {
         Moon.getInstance().getLogger().error("Failed to save the module storage to the disk, user configurations purged", new Object[]{e});
         e.printStackTrace();
      }
   }

   public boolean load(ModuleRegistry moduleRegistry) {
      if (!Files.exists(this.modulesFile)) {
         Moon.getInstance().getLogger().info("No module storage found on disk, skipping load of user configuration");
         return false;
      }

      JsonElement jsonElement;
      try {
         jsonElement = JsonParser.parseString(Files.readString(this.modulesFile));
      } catch (IOException | RuntimeException e) {
         Moon.getInstance().getLogger().error("Unable to parse module storage file", new Object[]{e});
         e.printStackTrace();
         return false;
      }

      if (jsonElement.isJsonArray()) {
         for (JsonElement element : jsonElement.getAsJsonArray()) {
            if (element.isJsonObject() && element.getAsJsonObject().has("name")) {
               JsonObject moduleObject = element.getAsJsonObject();
               Module module = moduleRegistry.getModuleByName(moduleObject.get("name").getAsString().toLowerCase());
               if (module != null) {
                  try {
                     module.deserialize(moduleObject, true);
                  } catch (Exception e) {
                     Moon.getInstance().getLogger().error("Unable to load " + module.getDisplayName() + " module!");
                  }
               }
            }
         }
      }

      return true;
   }
}
