package net.optifine.util;

public class CacheLocalByte {
   private int maxX = 18;
   private int maxY = 128;
   private int maxZ = 18;
   private int offsetX = 0;
   private int offsetY = 0;
   private int offsetZ = 0;
   private byte[][][] cache = null;
   private byte[] lastZs = null;
   private int lastDz = 0;

   public CacheLocalByte(int integer1, int integer2, int integer3) {
      this.maxX = integer1;
      this.maxY = integer2;
      this.maxZ = integer3;
      this.cache = new byte[integer1][integer2][integer3];
      this.resetCache();
   }

   public void resetCache() {
      for (int var4 = 0; var4 < this.maxX; var4++) {
         byte[][] var5 = this.cache[var4];

         for (int var6 = 0; var6 < this.maxY; var6++) {
            byte[] var7 = var5[var6];

            for (int var8 = 0; var8 < this.maxZ; var8++) {
               var7[var8] = -1;
            }
         }
      }
   }

   public void setOffset(int integer1, int integer2, int integer3) {
      this.offsetX = integer1;
      this.offsetY = integer2;
      this.offsetZ = integer3;
      this.resetCache();
   }

   public byte get(int integer1, int integer2, int integer3) {
      try {
         this.lastZs = this.cache[integer1 - this.offsetX][integer2 - this.offsetY];
         this.lastDz = integer3 - this.offsetZ;
         return this.lastZs[this.lastDz];
      } catch (ArrayIndexOutOfBoundsException var4) {
         var4.printStackTrace();
         return -1;
      }
   }

   public void setLast(byte byte1) {
      try {
         this.lastZs[this.lastDz] = byte1;
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }
}
