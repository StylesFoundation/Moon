package com.moon.module.impl.visual.hud.base;

import com.moon.module.impl.visual.HUD;
import java.util.function.Supplier;

public class IndividualColorVisibilitySupplier implements Supplier<Boolean> {
   private final HUD hud;

   public Boolean get() {
      return this.hud.getArrayListEnabled().getValue() && this.hud.getTextColor().getValue() == HUD.TextColorMode.INDIVIDUAL;
   }

   public IndividualColorVisibilitySupplier(HUD hud) {
      this.hud = hud;
   }
}
