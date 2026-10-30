package com.moon.module.impl.visual.esp.indicator;

import net.minecraft.entity.Entity;

public interface EspIndicator {
   boolean applies(Entity entity);

   String getText(Entity entity);
}
