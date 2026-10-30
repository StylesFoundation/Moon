package com.moon.module.impl.visual.hud.modulelist;

import com.moon.module.impl.visual.HUD;
import com.moon.module.setting.ChangeConsumer;

public class ModuleListElementRemoveCallback implements ChangeConsumer<Integer> {
   private final HUD hud;
   private final ModuleListElement element;

   public void accept(Integer oldValue, Integer newValue) {
      this.hud.clearModuleList(this.element);
   }

   public ModuleListElementRemoveCallback(HUD hud, ModuleListElement element) {
      this.hud = hud;
      this.element = element;
   }
}
