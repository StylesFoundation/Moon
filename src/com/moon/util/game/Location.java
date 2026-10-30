package com.moon.util.game;

import net.minecraft.world.World;

public class Location implements Cloneable {
   private World world;
   private double x;
   private double y;
   private double z;
   private float pitch;
   private float yaw;

   public Location(World world, double x, double y, double z) {
      this(world, x, y, z, 0.0F, 0.0F);
   }

   public Location(World world, double x, double y, double z, float yaw, float pitch) {
      this.world = world;
      this.x = x;
      this.y = y;
      this.z = z;
      this.pitch = pitch;
      this.yaw = yaw;
   }

   public World getWorld() {
      return this.world;
   }

   public void setWorld(World world) {
      this.world = world;
   }

   public double getX() {
      return this.x;
   }

   public void setX(double x) {
      this.x = x;
   }

   public void addX(double value) {
      this.x += value;
   }

   public void subtractX(double value) {
      this.x -= value;
   }

   public double getY() {
      return this.y;
   }

   public void setY(double y) {
      this.y = y;
   }

   public void addY(double value) {
      this.y += value;
   }

   public void subtractY(double value) {
      this.y -= value;
   }

   public double getZ() {
      return this.z;
   }

   public void setZ(double z) {
      this.z = z;
   }

   public void addZ(double value) {
      this.z += value;
   }

   public void subtractZ(double value) {
      this.z -= value;
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public void setPitch(float pitch) {
      this.pitch = pitch;
   }

   public Location add(Location location) {
      if (location != null && location.getWorld() == this.getWorld()) {
         this.x = this.x + location.x;
         this.y = this.y + location.y;
         this.z = this.z + location.z;
         return this;
      } else {
         throw new IllegalArgumentException("Cannot add Locations of differing worlds");
      }
   }

   public double distance2D(Location location) {
      return Math.sqrt(Math.pow(this.x - location.x, 2.0) + Math.pow(this.z - location.z, 2.0));
   }

   public double distance(Location location) {
      return Math.sqrt(Math.pow(this.x - location.x, 2.0) + Math.pow(this.y - location.y, 2.0) + Math.pow(this.z - location.z, 2.0));
   }

   public Location add(double x, double y, double z) {
      this.x += x;
      this.y += y;
      this.z += z;
      return this;
   }

   public Location subtract(Location location) {
      if (location != null && location.getWorld() == this.getWorld()) {
         this.x = this.x - location.x;
         this.y = this.y - location.y;
         this.z = this.z - location.z;
         return this;
      } else {
         throw new IllegalArgumentException("Cannot add Locations of differing worlds");
      }
   }

   public Location subtract(double x, double y, double z) {
      this.x -= x;
      this.y -= y;
      this.z -= z;
      return this;
   }

   public Location multiply(double multiplier) {
      this.x *= multiplier;
      this.y *= multiplier;
      this.z *= multiplier;
      return this;
   }

   public Location zero() {
      this.x = 0.0;
      this.y = 0.0;
      this.z = 0.0;
      return this;
   }

   @Override
   public boolean equals(Object other) {
      if (other == null) {
         return false;
      }

      if (this.getClass() != other.getClass()) {
         return false;
      }

      Location otherLocation = (Location)other;
      if (this.world == otherLocation.world || this.world != null && this.world.equals(otherLocation.world)) {
         if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(otherLocation.x)) {
            return false;
         } else if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(otherLocation.y)) {
            return false;
         } else if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(otherLocation.z)) {
            return false;
         } else {
            return Float.floatToIntBits(this.pitch) != Float.floatToIntBits(otherLocation.pitch)
               ? false
               : Float.floatToIntBits(this.yaw) == Float.floatToIntBits(otherLocation.yaw);
         }
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      float yaw = this.yaw;
      float pitch = this.pitch;
      double z = this.z;
      double y = this.y;
      double x = this.x;
      World world = this.world;
      return "Location{world=" + world + ",x=" + x + ",y=" + y + ",z=" + z + ",pitch=" + pitch + ",yaw=" + yaw + "}";
   }

   public Location clone() {
      try {
         return (Location)super.clone();
      } catch (CloneNotSupportedException e) {
         throw new Error(e);
      }
   }
}
