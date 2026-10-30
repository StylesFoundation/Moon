package com.moon.module.impl.visual;

import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.ColorSetting;

public class Glint extends Module {
   public final ColorSetting color = ColorSetting.builder("color").defaults("Color", -1).build();

   public Glint(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }
}
