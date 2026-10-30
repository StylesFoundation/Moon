package net.minecraft.util;

public class Vec3 {
   public double xCoord;
   public double yCoord;
   public double zCoord;

   public Vec3(double double1, double double2, double double3) {
      if (double1 == -0.0) {
         double1 = 0.0;
      }

      if (double2 == -0.0) {
         double2 = 0.0;
      }

      if (double3 == -0.0) {
         double3 = 0.0;
      }

      this.xCoord = double1;
      this.yCoord = double2;
      this.zCoord = double3;
   }

   public Vec3(Vec3i vec3i) {
      this(vec3i.getX(), vec3i.getY(), vec3i.getZ());
   }

   public Vec3 subtractReverse(Vec3 vec3) {
      return new Vec3(vec3.xCoord - this.xCoord, vec3.yCoord - this.yCoord, vec3.zCoord - this.zCoord);
   }

   public Vec3 normalize() {
      double var3 = MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
      return var3 < 1.0E-4 ? new Vec3(0.0, 0.0, 0.0) : new Vec3(this.xCoord / var3, this.yCoord / var3, this.zCoord / var3);
   }

   public double dotProduct(Vec3 vec3) {
      return this.xCoord * vec3.xCoord + this.yCoord * vec3.yCoord + this.zCoord * vec3.zCoord;
   }

   public Vec3 crossProduct(Vec3 vec3) {
      return new Vec3(
         this.yCoord * vec3.zCoord - this.zCoord * vec3.yCoord,
         this.zCoord * vec3.xCoord - this.xCoord * vec3.zCoord,
         this.xCoord * vec3.yCoord - this.yCoord * vec3.xCoord
      );
   }

   public Vec3 subtract(Vec3 vec3) {
      return this.subtract(vec3.xCoord, vec3.yCoord, vec3.zCoord);
   }

   public Vec3 subtract(double double1, double double2, double double3) {
      return this.addVector(-double1, -double2, -double3);
   }

   public Vec3 add(Vec3 vec3) {
      return this.addVector(vec3.xCoord, vec3.yCoord, vec3.zCoord);
   }

   public Vec3 addVector(double double1, double double2, double double3) {
      return new Vec3(this.xCoord + double1, this.yCoord + double2, this.zCoord + double3);
   }

   public double distanceTo(Vec3 vec3) {
      double var2 = vec3.xCoord - this.xCoord;
      double var4 = vec3.yCoord - this.yCoord;
      double var6 = vec3.zCoord - this.zCoord;
      return MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
   }

   public double squareDistanceTo(Vec3 vec3) {
      double var2 = vec3.xCoord - this.xCoord;
      double var4 = vec3.yCoord - this.yCoord;
      double var6 = vec3.zCoord - this.zCoord;
      return var2 * var2 + var4 * var4 + var6 * var6;
   }

   public double lengthVector() {
      return MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
   }

   public Vec3 getIntermediateWithXValue(Vec3 vec3, double double2) {
      double var7 = vec3.xCoord - this.xCoord;
      double var9 = vec3.yCoord - this.yCoord;
      double var11 = vec3.zCoord - this.zCoord;
      if (var7 * var7 < 1.0E-7F) {
         return null;
      }

      double var13 = (double2 - this.xCoord) / var7;
      return var13 >= 0.0 && var13 <= 1.0 ? new Vec3(this.xCoord + var7 * var13, this.yCoord + var9 * var13, this.zCoord + var11 * var13) : null;
   }

   public Vec3 getIntermediateWithYValue(Vec3 vec3, double double2) {
      double var7 = vec3.xCoord - this.xCoord;
      double var9 = vec3.yCoord - this.yCoord;
      double var11 = vec3.zCoord - this.zCoord;
      if (var9 * var9 < 1.0E-7F) {
         return null;
      }

      double var13 = (double2 - this.yCoord) / var9;
      return var13 >= 0.0 && var13 <= 1.0 ? new Vec3(this.xCoord + var7 * var13, this.yCoord + var9 * var13, this.zCoord + var11 * var13) : null;
   }

   public Vec3 getIntermediateWithZValue(Vec3 vec3, double double2) {
      double var7 = vec3.xCoord - this.xCoord;
      double var9 = vec3.yCoord - this.yCoord;
      double var11 = vec3.zCoord - this.zCoord;
      if (var11 * var11 < 1.0E-7F) {
         return null;
      }

      double var13 = (double2 - this.zCoord) / var11;
      return var13 >= 0.0 && var13 <= 1.0 ? new Vec3(this.xCoord + var7 * var13, this.yCoord + var9 * var13, this.zCoord + var11 * var13) : null;
   }

   @Override
   public String toString() {
      return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
   }

   public Vec3 rotatePitch(float float1) {
      float var2 = MathHelper.cos(float1);
      float var3 = MathHelper.sin(float1);
      double var4 = this.yCoord * var2 + this.zCoord * var3;
      double var6 = this.zCoord * var2 - this.yCoord * var3;
      return new Vec3(this.xCoord, var4, var6);
   }

   public Vec3 rotateYaw(float float1) {
      float var2 = MathHelper.cos(float1);
      float var3 = MathHelper.sin(float1);
      double var4 = this.xCoord * var2 + this.zCoord * var3;
      double var6 = this.zCoord * var2 - this.xCoord * var3;
      return new Vec3(var4, this.yCoord, var6);
   }

   public Vec3 Method759(float float1) {
      return new Vec3(this.xCoord * float1, this.yCoord * float1, this.zCoord * float1);
   }
}
