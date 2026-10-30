package com.moon.util.math;

import java.util.Objects;

public class Vector2f {
   private float x;
   private float y;

   @Override
   public boolean equals(Object object) {
      return !(object instanceof Vector2f other) ? false : this.x == other.x && this.y == other.y;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.x, this.y);
   }

   public Vector2f setX(float x) {
      this.x = x;
      return this;
   }

   public Vector2f setY(float y) {
      this.y = y;
      return this;
   }

   public Vector2f set(float x, float y) {
      this.x = x;
      this.y = y;
      return this;
   }

   public Vector2f offset(float x, float y) {
      this.x += x;
      this.y += y;
      return this;
   }

   public Vector2f set(Vector2f vector) {
      this.set(vector.getX(), vector.getY());
      return this;
   }

   public Vector2f clone() {
      return new Vector2f(this.x, this.y);
   }

   public double distance(Vector2f vector) {
      return Math.sqrt(this.distanceSquared(vector));
   }

   public double length() {
      return Math.sqrt(this.lengthSquared());
   }

   public double lengthSquared() {
      return this.x * this.x + this.y * this.y;
   }

   public double distanceSquared(Vector2f vector) {
      double dxSq = (this.x - vector.x) * (this.x - vector.x);
      double dySq = (this.y - vector.y) * (this.y - vector.y);
      return dxSq + dySq;
   }

   @Override
   public String toString() {
      return "x: " + this.x + ", y: " + this.y;
   }

   public String toFormattedString() {
      return String.format("x: %.5f y: %.5f", this.x, this.y);
   }

   public Vector2f subtract(Vector2f vector) {
      return new Vector2f(this.x - vector.x, this.y - vector.y);
   }

   public Vector2f difference(Vector2f vector) {
      return new Vector2f(this.x - vector.x, this.y - vector.y);
   }

   public Vector2f scale(int scale) {
      return new Vector2f(this.x * scale, this.y * scale);
   }

   public Vector2f scale(float scale) {
      return new Vector2f(this.x * scale, this.y * scale);
   }

   public Vector2f add(Vector2f vector) {
      return new Vector2f(this.x + vector.x, this.y + vector.y);
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public Vector2f() {
   }

   public Vector2f(float x, float y) {
      this.x = x;
      this.y = y;
   }
}
