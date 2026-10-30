package com.moon.events.impl.render;

import com.moon.events.EventCancellable;
import net.minecraft.entity.Entity;

public class RenderEntityShadowEvent extends EventCancellable {
   private final Entity entity;

   public Entity getEntity() {
      return this.entity;
   }

   public RenderEntityShadowEvent(Entity entity) {
      this.entity = entity;
   }
}
