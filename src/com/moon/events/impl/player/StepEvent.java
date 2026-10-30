package com.moon.events.impl.player;

import com.moon.events.EventCancellable;
import net.minecraft.entity.Entity;

public class StepEvent extends EventCancellable {
   private final Entity entity;
   private float height;
   private boolean pre;

   public Entity getEntity() {
      return this.entity;
   }

   public float getHeight() {
      return this.height;
   }

   public boolean isPre() {
      return this.pre;
   }

   public StepEvent setHeight(float height) {
      this.height = height;
      return this;
   }

   public StepEvent setPre(boolean pre) {
      this.pre = pre;
      return this;
   }

   public StepEvent(Entity entity, float height, boolean pre) {
      this.entity = entity;
      this.height = height;
      this.pre = pre;
   }
}
