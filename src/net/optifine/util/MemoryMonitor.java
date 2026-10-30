package net.optifine.util;

public class MemoryMonitor {
   private static long startTimeMs = System.currentTimeMillis();
   private static long startMemory = getMemoryUsed();
   private static long lastTimeMs = startTimeMs;
   private static long lastMemory = startMemory;
   private static boolean gcEvent = false;
   private static int memBytesSec = 0;

   public static void update() {
      long var3 = System.currentTimeMillis();
      long var5 = getMemoryUsed();
      gcEvent = var5 < lastMemory;
      if (gcEvent) {
         long var7 = lastTimeMs - startTimeMs;
         long var9 = lastMemory - startMemory;
         double var11 = var7 / 1000.0;
         int var13 = (int)(var9 / var11);
         if (var13 > 0) {
            memBytesSec = var13;
         }

         startTimeMs = var3;
         startMemory = var5;
      }

      lastTimeMs = var3;
      lastMemory = var5;
   }

   private static long getMemoryUsed() {
      Runtime var0 = Runtime.getRuntime();
      return var0.totalMemory() - var0.freeMemory();
   }

   public static long getStartTimeMs() {
      return startTimeMs;
   }

   public static long getStartMemoryMb() {
      return startMemory / 1048576L;
   }

   public static boolean isGcEvent() {
      return gcEvent;
   }

   public static long getAllocationRateMb() {
      return memBytesSec / 1048576L;
   }
}
