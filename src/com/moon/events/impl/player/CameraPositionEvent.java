package com.moon.events.impl.player;

import com.moon.events.Event;

public class CameraPositionEvent extends Event {
   private double x;
   private double y;
   private double z;

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public CameraPositionEvent setX(double x) {
      this.x = x;
      return this;
   }

   public CameraPositionEvent setY(double y) {
      this.y = y;
      return this;
   }

   public CameraPositionEvent setZ(double z) {
      this.z = z;
      return this;
   }

   public CameraPositionEvent(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }
}
