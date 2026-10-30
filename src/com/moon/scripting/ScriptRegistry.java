package com.moon.scripting;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ScriptRegistry {
   @SerializedName("scripts")
   private List<ScriptEntry> scripts;

   public List<ScriptEntry> getScripts() {
      return this.scripts;
   }

   public ScriptRegistry setScripts(List<ScriptEntry> scripts) {
      this.scripts = scripts;
      return this;
   }

   public ScriptRegistry() {
   }

   public ScriptRegistry(List<ScriptEntry> scripts) {
      this.scripts = scripts;
   }
}
