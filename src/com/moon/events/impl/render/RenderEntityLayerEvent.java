package com.moon.events.impl.render;

import com.moon.events.EventCancellable;
import net.minecraft.entity.Entity;

public class RenderEntityLayerEvent extends EventCancellable {
   private final Entity entity;

   public Entity getEntity() {
      return this.entity;
   }

   public RenderEntityLayerEvent(Entity entity) {
      this.entity = entity;
   }
}
