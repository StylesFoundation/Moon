package net.minecraft.world.gen.layer;

import com.google.common.collect.Lists;
import java.util.List;

public class IntCache {
   private static int intCacheSize = 256;
   private static final List<int[]> freeSmallArrays = Lists.newArrayList();
   private static final List<int[]> inUseSmallArrays = Lists.newArrayList();
   private static final List<int[]> freeLargeArrays = Lists.newArrayList();
   private static final List<int[]> inUseLargeArrays = Lists.newArrayList();

   public static synchronized int[] getIntCache(int integer) {
      if (integer <= 256) {
         if (freeSmallArrays.isEmpty()) {
            int[] var8 = new int[256];
            inUseSmallArrays.add(var8);
            return var8;
         } else {
            int[] var7 = freeSmallArrays.remove(freeSmallArrays.size() - 1);
            inUseSmallArrays.add(var7);
            return var7;
         }
      } else if (integer > intCacheSize) {
         intCacheSize = integer;
         freeLargeArrays.clear();
         inUseLargeArrays.clear();
         int[] var6 = new int[intCacheSize];
         inUseLargeArrays.add(var6);
         return var6;
      } else if (freeLargeArrays.isEmpty()) {
         int[] var5 = new int[intCacheSize];
         inUseLargeArrays.add(var5);
         return var5;
      } else {
         int[] var4 = freeLargeArrays.remove(freeLargeArrays.size() - 1);
         inUseLargeArrays.add(var4);
         return var4;
      }
   }

   public static synchronized void resetIntCache() {
      if (!freeLargeArrays.isEmpty()) {
         freeLargeArrays.remove(freeLargeArrays.size() - 1);
      }

      if (!freeSmallArrays.isEmpty()) {
         freeSmallArrays.remove(freeSmallArrays.size() - 1);
      }

      freeLargeArrays.addAll(inUseLargeArrays);
      freeSmallArrays.addAll(inUseSmallArrays);
      inUseLargeArrays.clear();
      inUseSmallArrays.clear();
   }

   public static synchronized String getCacheSizes() {
      return "cache: "
         + freeLargeArrays.size()
         + ", tcache: "
         + freeSmallArrays.size()
         + ", allocated: "
         + inUseLargeArrays.size()
         + ", tallocated: "
         + inUseSmallArrays.size();
   }
}
