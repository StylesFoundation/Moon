package com.moon.events.impl.player;

import com.moon.events.Event;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;

public class PickEvent extends Event {
   private double range;
   @Nullable
   private Entity entity;

   public double getRange() {
      return this.range;
   }

   @Nullable
   public Entity getEntity() {
      return this.entity;
   }

   public PickEvent setRange(double range) {
      this.range = range;
      return this;
   }

   public PickEvent setEntity(@Nullable Entity entity) {
      this.entity = entity;
      return this;
   }

   public PickEvent(double range, @Nullable Entity entity) {
      this.range = range;
      this.entity = entity;
   }
}
