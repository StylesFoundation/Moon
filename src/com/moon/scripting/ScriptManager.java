package com.moon.scripting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moon.Moon;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ScriptManager {
   private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
   private final ScriptLoader scriptLoader = new ScriptLoader();
   private final Map<String, Script> scripts = new HashMap<>();
   private File scriptsDirectory;
   private File registryFile;

   public void init(File baseDirectory, boolean reload) {
      this.scriptsDirectory = new File(baseDirectory, "scripts");
      this.registryFile = new File(baseDirectory, "loaded.json");
      if (!this.scriptsDirectory.exists()) {
         this.scriptsDirectory.mkdirs();
      }

      for (File scriptFile : Objects.requireNonNull(this.scriptsDirectory.listFiles())) {
         String fileName = scriptFile.getName();
         if (fileName.endsWith(".lua")) {
            String scriptName = fileName.split("\\.")[0];

            try {
               if (!this.scripts.containsKey(scriptName)) {
                  byte[] bytes = new FileInputStream(scriptFile).readAllBytes();
                  Script script = new Script(scriptName);
                  script.setSource(new String(bytes));
                  this.scripts.put(scriptName, script);
               }
            } catch (Exception e) {
               Moon.getInstance().getLogger().error(e);
            }
         }
      }

      if (this.registryFile.exists() && reload) {
         try {
            for (ScriptEntry entry : ((ScriptRegistry)gson.fromJson(new FileReader(this.registryFile), ScriptRegistry.class)).getScripts()) {
               Script script = this.getScript(entry.getName());
               if (script != null) {
                  Moon.getInstance().getLogger().info("Loading script named " + entry.getName() + " as it was enabled in the previous instance");
                  this.scriptLoader.load(script);
                  Moon.getInstance().getLogger().info("Loaded script named " + entry.getName() + " as it was enabled in the previous instance");
               }
            }
         } catch (Exception e) {
            Moon.getInstance().getLogger().error(e);
         }
      }
   }

   public void saveRegistry() {
      ScriptRegistry registry = new ScriptRegistry();
      ArrayList entries = new ArrayList();

      for (Script script : this.scripts.values()) {
         if (script.isLoaded()) {
            String scriptName = script.getName();
            entries.add(new ScriptEntry(scriptName, scriptName + ".lua"));
         }
      }

      registry.setScripts(entries);

      try {
         Files.writeString(
            this.registryFile.toPath(), gson.toJson(registry), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING
         );
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void reloadSource(Script script) {
      File scriptFile = new File(this.scriptsDirectory, script.getName() + ".lua");
      if (scriptFile.exists()) {
         try {
            byte[] bytes = new FileInputStream(scriptFile).readAllBytes();
            script.setSource(new String(bytes));
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   public void unload(String name) {
      Script script = this.findScript(name);
      if (script != null) {
         this.scripts.remove(name);
      }
   }

   public Script findScript(String name) {
      this.init(Moon.getInstance().getRootDirectory(), false);
      return this.scripts.get(name);
   }

   public Script getScript(String name) {
      return this.scripts.get(name);
   }

   public ScriptLoader getScriptLoader() {
      return this.scriptLoader;
   }

   public Map<String, Script> getScripts() {
      return this.scripts;
   }

   public File getScriptsDirectory() {
      return this.scriptsDirectory;
   }

   public File getRegistryFile() {
      return this.registryFile;
   }
}
