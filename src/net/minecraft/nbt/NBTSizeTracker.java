package net.minecraft.nbt;

public class NBTSizeTracker {
   public static final NBTSizeTracker INFINITE = new NBTSizeTracker(0L) {
      @Override
      public void read(long long1) {
      }
   };
   private final long max;
   private long read;

   public NBTSizeTracker(long long1) {
      this.max = long1;
   }

   public void read(long long1) {
      this.read += long1 / 8L;
      if (this.read > this.max) {
         throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.read + "bytes where max allowed: " + this.max);
      }
   }
}
