package com.moon.events.impl.module;

import com.moon.events.Event;
import com.moon.module.Module;

public class ModuleSuffixChangedEvent extends Event {
   private final String suffix;
   private final Module module;

   public ModuleSuffixChangedEvent(String suffix, Module module) {
      this.suffix = suffix;
      this.module = module;
   }
}
