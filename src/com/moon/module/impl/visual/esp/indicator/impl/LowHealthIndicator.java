package com.moon.module.impl.visual.esp.indicator.impl;

import com.moon.module.impl.visual.ESP;
import com.moon.module.impl.visual.esp.indicator.EspIndicator;
import com.moon.module.impl.visual.esp.indicator.EspIndicatorRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class LowHealthIndicator implements EspIndicator {
   final EspIndicatorRenderer indicatorRenderer;

   public LowHealthIndicator(EspIndicatorRenderer indicatorRenderer) {
      this.indicatorRenderer = indicatorRenderer;
   }

   @Override
   public boolean applies(Entity entity) {
      return entity instanceof EntityPlayer player
         && player.getHealth() / player.getMaxHealth() < 0.25
         && this.indicatorRenderer.getEsp().getIndicatorTypes().hasChecked(ESP.IndicatorType.LOW_HP);
   }

   @Override
   public String getText(Entity entity) {
      return "§4LOW";
   }
}
