package com.moon.events.impl.module;

import com.moon.events.Event;
import com.moon.module.ModuleCategory;

public class CategoryHideEvent extends Event {
   private final ModuleCategory category;
   private final boolean state;

   public CategoryHideEvent(ModuleCategory category, boolean state) {
      this.category = category;
      this.state = state;
   }

   public ModuleCategory getCategory() {
      return this.category;
   }

   public boolean getState() {
      return this.state;
   }
}
