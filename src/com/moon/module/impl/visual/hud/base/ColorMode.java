package com.moon.module.impl.visual.hud.base;

import com.moon.module.LabelEnum;

public enum ColorMode implements LabelEnum {
   SYNC("Sync with Modules"),
   STATIC("Static"),
   GRADIENT("Gradient"),
   PULSING("Pulsing");

   private final String label;

   @Override
   public String label() {
      return this.label;
   }

   ColorMode(String label) {
      this.label = label;
   }
}
