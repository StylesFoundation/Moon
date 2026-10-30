package com.moon.events.impl.module;

import com.moon.events.Event;
import com.moon.module.Module;

public class ModuleToggleEvent extends Event {
   private final boolean state;
   private final Module module;

   public ModuleToggleEvent(boolean state, Module module) {
      this.state = state;
      this.module = module;
   }

   public boolean isEnabled() {
      return this.state;
   }

   public Module getModule() {
      return this.module;
   }
}
