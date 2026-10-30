package com.moon.events.impl.render;

import com.moon.events.EventCancellable;
import net.minecraft.entity.EntityLivingBase;

public class RenderEntityArmorEvent extends EventCancellable {
   private final EntityLivingBase entity;

   public EntityLivingBase getEntity() {
      return this.entity;
   }

   public RenderEntityArmorEvent(EntityLivingBase entity) {
      this.entity = entity;
   }
}
