package com.moon.module.impl.visual.hud.base;

import com.google.gson.annotations.SerializedName;

public class HudElementPosition {
   @SerializedName("x")
   private float x;
   @SerializedName("y")
   private float y;

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof HudElementPosition otherPosition)) {
         return false;
      } else if (!otherPosition.canEqual(this)) {
         return false;
      } else {
         return Float.compare(this.getX(), otherPosition.getX()) != 0 ? false : Float.compare(this.getY(), otherPosition.getY()) == 0;
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof HudElementPosition;
   }

   @Override
   public int hashCode() {
      return (59 + Float.floatToIntBits(this.getX())) * 59 + Float.floatToIntBits(this.getY());
   }

   @Override
   public String toString() {
      return "DraggableComponentData(x=" + this.getX() + ", y=" + this.getY() + ")";
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public HudElementPosition setX(float value) {
      this.x = value;
      return this;
   }

   public HudElementPosition setY(float value) {
      this.y = value;
      return this;
   }

   public HudElementPosition(float x, float y) {
      this.x = x;
      this.y = y;
   }

   public HudElementPosition() {
   }
}
