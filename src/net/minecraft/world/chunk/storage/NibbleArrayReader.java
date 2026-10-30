package net.minecraft.world.chunk.storage;

public class NibbleArrayReader {
   public final byte[] data;
   private final int depthBits;
   private final int depthBitsPlusFour;

   public NibbleArrayReader(byte[] arr, int integer) {
      this.data = arr;
      this.depthBits = integer;
      this.depthBitsPlusFour = integer + 4;
   }

   public int get(int integer1, int integer2, int integer3) {
      int var7 = integer1 << this.depthBitsPlusFour | integer3 << this.depthBits | integer2;
      int var8 = var7 >> 1;
      int var9 = var7 & 1;
      return var9 == 0 ? this.data[var8] & 15 : this.data[var8] >> 4 & 15;
   }
}
