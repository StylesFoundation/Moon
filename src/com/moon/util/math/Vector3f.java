package com.moon.util.math;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public record Vector3f(float x, float y, float z) {
   private static float b = (float) (Math.PI / 180.0);
   public static Vector3f ZERO = new Vector3f(0.0F, 0.0F, 0.0F);
   public static Vector3f UNIT_X = new Vector3f(1.0F, 0.0F, 0.0F);
   public static Vector3f UNIT_Y = new Vector3f(0.0F, 1.0F, 0.0F);
   public static Vector3f UNIT_Z = new Vector3f(0.0F, 0.0F, 1.0F);

   public static Vector3f of(double x, double y, double z) {
      return new Vector3f((float)x, (float)y, (float)z);
   }

   public static Vector3f of(Vec3 vec) {
      return of(vec.xCoord, vec.yCoord, vec.zCoord);
   }

   public static Vector3f of(BlockPos blockPos) {
      return new Vector3f(blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F);
   }

   public float dot(Vector3f vector) {
      return this.x * vector.x + this.y * vector.y + this.z * vector.z;
   }

   public Vector3f add(Vector3f vector) {
      return new Vector3f(this.x + vector.x, this.y + vector.y, this.z + vector.z);
   }

   public Vector3f add(float x, float y, float z) {
      return new Vector3f(this.x + x, this.y + y, this.z + z);
   }

   public Vector3f sub(Vector3f vector) {
      return new Vector3f(this.x - vector.x, this.y - vector.y, this.z - vector.z);
   }

   public Vector3f scale(float scale) {
      return new Vector3f(this.x * scale, this.y * scale, this.z * scale);
   }

   public float length() {
      return MathHelper.sqrt_float(this.lengthSquared());
   }

   public float lengthSquared() {
      return this.x * this.x + this.y * this.y + this.z * this.z;
   }

   public Vector3f normalize() {
      float lenSq = this.lengthSquared();
      return lenSq < 1.0E-32F ? ZERO : this.scale(MathHelper.sqrt_float(lenSq));
   }

   public AxisAlignedBB toAABB(float width, float height) {
      float halfWidth = width / 2.0F;
      return new AxisAlignedBB(this.x - halfWidth, this.y, this.z - halfWidth, this.x + halfWidth, this.y + height, this.z + halfWidth);
   }

   public Vector3f copy() {
      return new Vector3f(this.x, this.y, this.z);
   }

   public static Vector3f directionFromRotation(float yaw, float pitch) {
      float cosYaw = MathHelper.cos(-yaw * b - (float) Math.PI);
      float sinYaw = MathHelper.sin(-yaw * b - (float) Math.PI);
      float cosPitch = -MathHelper.cos(-pitch * b);
      float sinPitch = MathHelper.sin(-pitch * b);
      return new Vector3f(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
   }

   public float Method519() {
      return this.x;
   }

   public float Method520() {
      return this.y;
   }

   public float Method521() {
      return this.z;
   }
}
