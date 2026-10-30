package com.moon.util.math;

import java.util.Objects;

public class Position {
   public double x;
   public double y;
   public double z;

   public Position setX(double x) {
      this.x = x;
      return this;
   }

   public Position setY(double y) {
      this.y = y;
      return this;
   }

   public Position setZ(double z) {
      this.z = z;
      return this;
   }

   public Position set(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
      return this;
   }

   public Position add(double x, double y, double z) {
      this.x += x;
      this.y += y;
      this.z += z;
      return this;
   }

   public Position multiply(double x, double y, double z) {
      this.x *= x;
      this.y *= y;
      this.z *= z;
      return this;
   }

   public Position scaleY(double scale) {
      this.y *= scale;
      return this;
   }

   public Position scaleXZ(double x, double z) {
      this.x *= x;
      this.z *= z;
      return this;
   }

   public double distanceTo(Position position) {
      return Math.sqrt(this.distanceSquaredTo(position));
   }

   public double length() {
      return Math.sqrt(this.lengthSquared());
   }

   public double lengthSquaredXZ() {
      return this.x * this.x + this.z * this.z;
   }

   public double magnitude() {
      return Math.sqrt(this.lengthSquared());
   }

   public double lengthSquared() {
      return this.x * this.x + this.y * this.y + this.z * this.z;
   }

   public double distanceSquaredTo(Position position) {
      double dxSq = (this.x - position.x) * (this.x - position.x);
      double dySq = (this.y - position.y) * (this.y - position.y);
      double dzSq = (this.z - position.z) * (this.z - position.z);
      return dxSq + dySq + dzSq;
   }

   public void setFromOffset(Position position, double offsetX, double offsetY, double offsetZ) {
      this.x = position.x + offsetX;
      this.y = position.y + offsetY;
      this.z = position.z + offsetZ;
   }

   public Position divide(Position position) {
      this.x = this.x / position.x;
      this.z = this.z / position.z;
      this.y = this.y / position.y;
      return this;
   }

   public Position normalize() {
      double length = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
      this.x /= length;
      this.y /= length;
      this.z /= length;
      return this;
   }

   @Override
   public String toString() {
      double posZ = this.z;
      double posY = this.y;
      double posX = this.x;
      return "x: " + posX + ", y: " + posY + ", z: " + posZ;
   }

   public String toFormattedString() {
      return String.format("x: %.5f y: %.5f z: %.5f", this.x, this.y, this.z);
   }

   public Position subtract(Position position) {
      return new Position(this.x - position.x, this.y - position.y, this.z - position.z);
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         Position other = (Position)object;
         return Double.compare(other.x, this.x) == 0 && Double.compare(other.y, this.y) == 0 && Double.compare(other.z, this.z) == 0;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.x, this.y, this.z);
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public Position() {
   }

   public Position(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public static int getPrecision() {
      return 18;
   }
}
