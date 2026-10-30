package com.moon.scripting;

import com.google.gson.annotations.SerializedName;

public class ScriptEntry {
   @SerializedName("name")
   private String name;
   @SerializedName("scriptPath")
   private String scriptPath;

   public String getName() {
      return this.name;
   }

   public String getScriptPath() {
      return this.scriptPath;
   }

   public ScriptEntry setName(String name) {
      this.name = name;
      return this;
   }

   public ScriptEntry setScriptPath(String scriptPath) {
      this.scriptPath = scriptPath;
      return this;
   }

   public ScriptEntry() {
   }

   public ScriptEntry(String name, String scriptPath) {
      this.name = name;
      this.scriptPath = scriptPath;
   }
}
