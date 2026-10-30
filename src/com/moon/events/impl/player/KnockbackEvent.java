package com.moon.events.impl.player;

import com.moon.events.EventCancellable;
import com.moon.util.math.Position;

public class KnockbackEvent extends EventCancellable {
   private Position position;
   private boolean explosion;
   private boolean ignoreY;
   private boolean ignoreZ;

   public Position getPosition() {
      return this.position;
   }

   public boolean isExplosion() {
      return this.explosion;
   }

   public boolean isIgnoreY() {
      return this.ignoreY;
   }

   public boolean isIgnoreZ() {
      return this.ignoreZ;
   }

   public KnockbackEvent(Position position, boolean explosion, boolean ignoreY, boolean ignoreZ) {
      this.position = position;
      this.explosion = explosion;
      this.ignoreY = ignoreY;
      this.ignoreZ = ignoreZ;
   }

   public KnockbackEvent ignoreY(boolean ignoreY) {
      this.ignoreY = ignoreY;
      return this;
   }

   public KnockbackEvent ignoreZ(boolean ignoreZ) {
      this.ignoreZ = ignoreZ;
      return this;
   }
}
