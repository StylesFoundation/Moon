package com.moon.ui.clickgui.modern.tooltip;

import com.moon.ui.clickgui.modern.component.ModernModuleButton;

public class ModuleTooltip extends Tooltip {
   private final ModernModuleButton moduleButton;

   public ModuleTooltip(ModernModuleButton moduleButton) {
      super(moduleButton.getModule().getDescription());
      this.moduleButton = moduleButton;
   }

   @Override
   public boolean isVisible() {
      return this.moduleButton.isHovered();
   }
}
