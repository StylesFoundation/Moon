package com.moon.events.impl.render;

import com.moon.events.EventCancellable;
import net.minecraft.entity.Entity;

public class RenderEntityEvent extends EventCancellable {
   private final Entity entity;
   private boolean riding;
   private boolean child;

   public Entity getEntity() {
      return this.entity;
   }

   public boolean isRiding() {
      return this.riding;
   }

   public boolean isChild() {
      return this.child;
   }

   public RenderEntityEvent setRiding(boolean riding) {
      this.riding = riding;
      return this;
   }

   public RenderEntityEvent setChild(boolean child) {
      this.child = child;
      return this;
   }

   public RenderEntityEvent(Entity entity, boolean riding, boolean child) {
      this.entity = entity;
      this.riding = riding;
      this.child = child;
   }
}
