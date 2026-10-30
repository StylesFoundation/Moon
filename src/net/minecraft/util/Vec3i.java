package net.minecraft.util;

import com.google.common.base.MoreObjects;

public class Vec3i implements Comparable<Vec3i> {
   public static final Vec3i NULL_VECTOR = new Vec3i(0, 0, 0);
   private final int x;
   private final int y;
   private final int z;

   public Vec3i(int integer1, int integer2, int integer3) {
      this.x = integer1;
      this.y = integer2;
      this.z = integer3;
   }

   public Vec3i(double double1, double double2, double double3) {
      this(MathHelper.floor_double(double1), MathHelper.floor_double(double2), MathHelper.floor_double(double3));
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         return !(object instanceof Vec3i var5) ? false : this.getX() == var5.getX() && this.getY() == var5.getY() && this.getZ() == var5.getZ();
      }
   }

   @Override
   public int hashCode() {
      return (this.getY() + this.getZ() * 31) * 31 + this.getX();
   }

   public int compareTo(Vec3i vec3i) {
      return this.getY() == vec3i.getY() ? (this.getZ() == vec3i.getZ() ? this.getX() - vec3i.getX() : this.getZ() - vec3i.getZ()) : this.getY() - vec3i.getY();
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getZ() {
      return this.z;
   }

   public Vec3i crossProduct(Vec3i vec3i) {
      return new Vec3i(
         this.getY() * vec3i.getZ() - this.getZ() * vec3i.getY(),
         this.getZ() * vec3i.getX() - this.getX() * vec3i.getZ(),
         this.getX() * vec3i.getY() - this.getY() * vec3i.getX()
      );
   }

   public double distanceSq(double double1, double double2, double double3) {
      double var7 = this.getX() - double1;
      double var9 = this.getY() - double2;
      double var11 = this.getZ() - double3;
      return var7 * var7 + var9 * var9 + var11 * var11;
   }

   public double distanceSqToCenter(double double1, double double2, double double3) {
      double var7 = this.getX() + 0.5 - double1;
      double var9 = this.getY() + 0.5 - double2;
      double var11 = this.getZ() + 0.5 - double3;
      return var7 * var7 + var9 * var9 + var11 * var11;
   }

   public double distanceSq(Vec3i vec3i) {
      return this.distanceSq(vec3i.getX(), vec3i.getY(), vec3i.getZ());
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
   }
}
