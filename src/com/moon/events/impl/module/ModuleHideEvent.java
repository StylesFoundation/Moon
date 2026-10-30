package com.moon.events.impl.module;

import com.moon.events.Event;
import com.moon.module.Module;

public class ModuleHideEvent extends Event {
   private final boolean hidden;
   private final boolean state;
   private final Module module;

   public ModuleHideEvent(boolean hidden, boolean state, Module module) {
      this.hidden = hidden;
      this.state = state;
      this.module = module;
   }

   public boolean isHidden() {
      return this.hidden;
   }

   public boolean isEnabled() {
      return this.state;
   }

   public Module getModule() {
      return this.module;
   }
}
