package com.moon.module;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.moon.scripting.ScriptModule;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModuleRegistry {
   private final Map<String, Module> moduleNameMap = new HashMap<>();
   private final ClassToInstanceMap<Module> moduleClassMap = MutableClassToInstanceMap.create();

   public void registerModule(Module module) {
      this.moduleNameMap.put(module.getName().toLowerCase(), module);
      if (!(module instanceof ScriptModule)) {
         this.moduleClassMap.put(module.getClass(), module);
      }
   }

   public Module getModuleByName(String name) {
      return this.moduleNameMap.get(name);
   }

   public <T extends Module> T getModuleByClass(Class<? extends T> moduleClass) {
      return (T)this.moduleClassMap.get(moduleClass);
   }

   public Module unregisterModule(Module module) {
      this.moduleNameMap.remove(module.getName().toLowerCase());
      this.moduleClassMap.remove(module.getClass());
      return module;
   }

   public Collection<Module> getModules() {
      return this.moduleNameMap.values();
   }

   public ClassToInstanceMap<Module> getModuleClassMap() {
      return this.moduleClassMap;
   }

   public Map<String, Module> getModuleNameMap() {
      return this.moduleNameMap;
   }
}
