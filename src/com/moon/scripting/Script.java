package com.moon.scripting;

import com.moon.module.Module;
import java.util.ArrayList;
import java.util.List;

public class Script {
   private final List<Module> modules = new ArrayList<>();
   private final String name;
   private String source;
   private boolean loaded;

   public List<Module> getModules() {
      return this.modules;
   }

   public String getName() {
      return this.name;
   }

   public String getSource() {
      return this.source;
   }

   public boolean isLoaded() {
      return this.loaded;
   }

   public Script setSource(String source) {
      this.source = source;
      return this;
   }

   public Script setLoaded(boolean loaded) {
      this.loaded = loaded;
      return this;
   }

   public Script(String name) {
      this.name = name;
   }
}
