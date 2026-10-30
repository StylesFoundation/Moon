package com.moon.ui.clickgui.modern.tooltip;

import com.moon.ui.clickgui.modern.setting.ModernSettingComponent;

public class SettingTooltip extends Tooltip {
   private final ModernSettingComponent settingComponent;

   public SettingTooltip(ModernSettingComponent settingComponent) {
      super(settingComponent.getSetting().getDescription());
      this.settingComponent = settingComponent;
   }

   @Override
   public boolean isVisible() {
      return this.settingComponent.isTooltipHovered();
   }
}
