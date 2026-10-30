package com.moon.module.impl.visual.esp.indicator.impl;

import com.moon.module.impl.visual.ESP;
import com.moon.module.impl.visual.esp.indicator.EspIndicator;
import com.moon.module.impl.visual.esp.indicator.EspIndicatorRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class BlockingIndicator implements EspIndicator {
   final EspIndicatorRenderer indicatorRenderer;

   public BlockingIndicator(EspIndicatorRenderer indicatorRenderer) {
      this.indicatorRenderer = indicatorRenderer;
   }

   @Override
   public boolean applies(Entity entity) {
      return entity instanceof EntityPlayer player
         && player.isBlocking()
         && this.indicatorRenderer.getEsp().getIndicatorTypes().hasChecked(ESP.IndicatorType.BLOCK);
   }

   @Override
   public String getText(Entity entity) {
      return "§aBLCK";
   }
}
