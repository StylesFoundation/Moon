package net.optifine.util;

import java.util.HashMap;
import java.util.Map;

public class TimedEvent {
   private static final Map<String, Long> mapEventTimes = new HashMap<>();

   public static boolean isActive(String string, long long2) {
      synchronized (mapEventTimes) {
         long var7 = System.currentTimeMillis();
         long var10 = mapEventTimes.computeIfAbsent(string, k -> var7);
         if (var7 < var10 + long2) {
            return false;
         }

         mapEventTimes.put(string, var7);
         return true;
      }
   }
}
