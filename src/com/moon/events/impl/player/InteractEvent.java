package com.moon.events.impl.player;

import com.moon.events.EventCancellable;
import net.minecraft.entity.Entity;

public class InteractEvent extends EventCancellable {
   private final Entity entity;

   public Entity getEntity() {
      return this.entity;
   }

   public InteractEvent(Entity entity) {
      this.entity = entity;
   }
}
