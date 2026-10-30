package com.moon.module;

import com.google.common.collect.ClassToInstanceMap;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {
   private final ModuleRegistry INSTANCE = new ModuleRegistry();

   public void registerModule(Module module) {
      this.INSTANCE.registerModule(module);
   }

   public void unregisterModule(Module module) {
      this.INSTANCE.unregisterModule(module);
   }

   public Module getModuleByName(String name) {
      return this.INSTANCE.getModuleByName(name.toLowerCase());
   }

   public <T extends Module> T getModule(Class<? extends Module> moduleClass) {
      return (T) this.INSTANCE.getModuleByClass(moduleClass);
   }

   public List<Module> getModulesByCategory(ModuleCategory category) {
      return this.INSTANCE.getModules().stream().filter(module -> module.getCategory() == category).collect(Collectors.toList());
   }

   public ModuleRegistry getRegistry() {
      return this.INSTANCE;
   }

   public java.util.Collection<Module> getModules() {
      return this.INSTANCE.getModules();
   }

   public ClassToInstanceMap<Module> getModuleClassMap() {
      return this.INSTANCE.getModuleClassMap();
   }
}
