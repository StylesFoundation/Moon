package com.moon.handlers.culling;

import java.util.Arrays;

public class PackedOcclusionCache implements OcclusionCache {
   private final int size;
   private final byte[] data;
   private int flatIndex;
   private int byteIndex;
   private int bitOffset;

   public PackedOcclusionCache(int cacheRadius) {
      this.size = cacheRadius * 2;
      this.data = new byte[this.size * this.size * this.size / 4];
   }

   @Override
   public void clear() {
      Arrays.fill(this.data, (byte)0);
   }

   @Override
   public void markVisible(int x, int y, int z) {
      this.flatIndex = x + y * this.size + z * this.size * this.size;
      this.byteIndex = this.flatIndex / 4;
      this.bitOffset = this.flatIndex % 4 * 2;
      this.data[this.byteIndex] = (byte)(this.data[this.byteIndex] | 1 << this.bitOffset);
   }

   @Override
   public void markOpaque(int x, int y, int z) {
      this.flatIndex = x + y * this.size + z * this.size * this.size;
      this.byteIndex = this.flatIndex / 4;
      this.bitOffset = this.flatIndex % 4 * 2;
      this.data[this.byteIndex] = (byte)(this.data[this.byteIndex] | 1 << this.bitOffset + 1);
   }

   @Override
   public int getState(int x, int y, int z) {
      this.flatIndex = x + y * this.size + z * this.size * this.size;
      this.byteIndex = this.flatIndex / 4;
      this.bitOffset = this.flatIndex % 4 * 2;
      return this.data[this.byteIndex] >> this.bitOffset & 3;
   }

   @Override
   public void repeatVisible() {
      this.data[this.byteIndex] = (byte)(this.data[this.byteIndex] | 1 << this.bitOffset);
   }

   @Override
   public void repeatOpaque() {
      this.data[this.byteIndex] = (byte)(this.data[this.byteIndex] | 1 << this.bitOffset + 1);
   }
}
