package net.minecraft.world.gen.structure;

import com.google.common.base.MoreObjects;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;

public class StructureBoundingBox {
   public int minX;
   public int minY;
   public int minZ;
   public int maxX;
   public int maxY;
   public int maxZ;

   public StructureBoundingBox() {
   }

   public StructureBoundingBox(int[] arr) {
      if (arr.length == 6) {
         this.minX = arr[0];
         this.minY = arr[1];
         this.minZ = arr[2];
         this.maxX = arr[3];
         this.maxY = arr[4];
         this.maxZ = arr[5];
      }
   }

   public static StructureBoundingBox getNewBoundingBox() {
      return new StructureBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
   }

   public static StructureBoundingBox getComponentToAddBoundingBox(
      int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8, int integer9, EnumFacing enumFacing
   ) {
      switch (enumFacing) {
         case NORTH:
            return new StructureBoundingBox(
               integer1 + integer4,
               integer2 + integer5,
               integer3 - integer9 + 1 + integer6,
               integer1 + integer7 - 1 + integer4,
               integer2 + integer8 - 1 + integer5,
               integer3 + integer6
            );
         case WEST:
            return new StructureBoundingBox(
               integer1 - integer9 + 1 + integer6,
               integer2 + integer5,
               integer3 + integer4,
               integer1 + integer6,
               integer2 + integer8 - 1 + integer5,
               integer3 + integer7 - 1 + integer4
            );
         case EAST:
            return new StructureBoundingBox(
               integer1 + integer6,
               integer2 + integer5,
               integer3 + integer4,
               integer1 + integer9 - 1 + integer6,
               integer2 + integer8 - 1 + integer5,
               integer3 + integer7 - 1 + integer4
            );
         default:
            return new StructureBoundingBox(
               integer1 + integer4,
               integer2 + integer5,
               integer3 + integer6,
               integer1 + integer7 - 1 + integer4,
               integer2 + integer8 - 1 + integer5,
               integer3 + integer9 - 1 + integer6
            );
      }
   }

   public static StructureBoundingBox func_175899_a(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      return new StructureBoundingBox(
         Math.min(integer1, integer4),
         Math.min(integer2, integer5),
         Math.min(integer3, integer6),
         Math.max(integer1, integer4),
         Math.max(integer2, integer5),
         Math.max(integer3, integer6)
      );
   }

   public StructureBoundingBox(StructureBoundingBox structureBoundingBox) {
      this.minX = structureBoundingBox.minX;
      this.minY = structureBoundingBox.minY;
      this.minZ = structureBoundingBox.minZ;
      this.maxX = structureBoundingBox.maxX;
      this.maxY = structureBoundingBox.maxY;
      this.maxZ = structureBoundingBox.maxZ;
   }

   public StructureBoundingBox(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      this.minX = integer1;
      this.minY = integer2;
      this.minZ = integer3;
      this.maxX = integer4;
      this.maxY = integer5;
      this.maxZ = integer6;
   }

   public StructureBoundingBox(Vec3i vec3i1, Vec3i vec3i2) {
      this.minX = Math.min(vec3i1.getX(), vec3i2.getX());
      this.minY = Math.min(vec3i1.getY(), vec3i2.getY());
      this.minZ = Math.min(vec3i1.getZ(), vec3i2.getZ());
      this.maxX = Math.max(vec3i1.getX(), vec3i2.getX());
      this.maxY = Math.max(vec3i1.getY(), vec3i2.getY());
      this.maxZ = Math.max(vec3i1.getZ(), vec3i2.getZ());
   }

   public StructureBoundingBox(int integer1, int integer2, int integer3, int integer4) {
      this.minX = integer1;
      this.minZ = integer2;
      this.maxX = integer3;
      this.maxZ = integer4;
      this.minY = 1;
      this.maxY = 512;
   }

   public boolean intersectsWith(StructureBoundingBox structureBoundingBox) {
      return this.maxX >= structureBoundingBox.minX
         && this.minX <= structureBoundingBox.maxX
         && this.maxZ >= structureBoundingBox.minZ
         && this.minZ <= structureBoundingBox.maxZ
         && this.maxY >= structureBoundingBox.minY
         && this.minY <= structureBoundingBox.maxY;
   }

   public boolean intersectsWith(int integer1, int integer2, int integer3, int integer4) {
      return this.maxX >= integer1 && this.minX <= integer3 && this.maxZ >= integer2 && this.minZ <= integer4;
   }

   public void expandTo(StructureBoundingBox structureBoundingBox) {
      this.minX = Math.min(this.minX, structureBoundingBox.minX);
      this.minY = Math.min(this.minY, structureBoundingBox.minY);
      this.minZ = Math.min(this.minZ, structureBoundingBox.minZ);
      this.maxX = Math.max(this.maxX, structureBoundingBox.maxX);
      this.maxY = Math.max(this.maxY, structureBoundingBox.maxY);
      this.maxZ = Math.max(this.maxZ, structureBoundingBox.maxZ);
   }

   public void offset(int integer1, int integer2, int integer3) {
      this.minX += integer1;
      this.minY += integer2;
      this.minZ += integer3;
      this.maxX += integer1;
      this.maxY += integer2;
      this.maxZ += integer3;
   }

   public boolean isVecInside(Vec3i vec3i) {
      return vec3i.getX() >= this.minX
         && vec3i.getX() <= this.maxX
         && vec3i.getZ() >= this.minZ
         && vec3i.getZ() <= this.maxZ
         && vec3i.getY() >= this.minY
         && vec3i.getY() <= this.maxY;
   }

   public Vec3i func_175896_b() {
      return new Vec3i(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
   }

   public int getXSize() {
      return this.maxX - this.minX + 1;
   }

   public int getYSize() {
      return this.maxY - this.minY + 1;
   }

   public int getZSize() {
      return this.maxZ - this.minZ + 1;
   }

   public Vec3i getCenter() {
      return new BlockPos(this.minX + (this.maxX - this.minX + 1) / 2, this.minY + (this.maxY - this.minY + 1) / 2, this.minZ + (this.maxZ - this.minZ + 1) / 2);
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
         .add("x0", this.minX)
         .add("y0", this.minY)
         .add("z0", this.minZ)
         .add("x1", this.maxX)
         .add("y1", this.maxY)
         .add("z1", this.maxZ)
         .toString();
   }

   public NBTTagIntArray toNBTTagIntArray() {
      return new NBTTagIntArray(new int[]{this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ});
   }
}
