package net.minecraft.util;

public class IntegerCache {
   private static final Integer[] CACHE = new Integer[65535];

   public static Integer getInteger(int integer) {
      return integer >= 0 && integer < CACHE.length ? CACHE[integer] : integer;
   }

   static {
      int var8 = 0;

      for (int var9 = CACHE.length; var8 < var9; var8++) {
         CACHE[var8] = var8;
      }
   }
}
