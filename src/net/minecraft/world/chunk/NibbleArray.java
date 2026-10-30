package net.minecraft.world.chunk;

public class NibbleArray {
   private final byte[] data;

   public NibbleArray() {
      this.data = new byte[2048];
   }

   public NibbleArray(byte[] arr) {
      this.data = arr;
      if (arr.length != 2048) {
         throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + arr.length);
      }
   }

   public int get(int integer1, int integer2, int integer3) {
      return this.getFromIndex(this.getCoordinateIndex(integer1, integer2, integer3));
   }

   public void set(int integer1, int integer2, int integer3, int integer4) {
      this.setIndex(this.getCoordinateIndex(integer1, integer2, integer3), integer4);
   }

   private int getCoordinateIndex(int integer1, int integer2, int integer3) {
      return integer2 << 8 | integer3 << 4 | integer1;
   }

   public int getFromIndex(int integer) {
      int var5 = this.getNibbleIndex(integer);
      return this.isLowerNibble(integer) ? this.data[var5] & 15 : this.data[var5] >> 4 & 15;
   }

   public void setIndex(int integer1, int integer2) {
      int var6 = this.getNibbleIndex(integer1);
      if (this.isLowerNibble(integer1)) {
         this.data[var6] = (byte)(this.data[var6] & 240 | integer2 & 15);
      } else {
         this.data[var6] = (byte)(this.data[var6] & 15 | (integer2 & 15) << 4);
      }
   }

   private boolean isLowerNibble(int integer) {
      return (integer & 1) == 0;
   }

   private int getNibbleIndex(int integer) {
      return integer >> 1;
   }

   public byte[] getData() {
      return this.data;
   }
}
