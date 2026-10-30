package net.optifine.shaders.uniform;

import java.util.HashMap;
import java.util.Map;
import net.optifine.util.CounterInt;
import net.optifine.util.SmoothFloat;

public class Smoother {
   private static final Map<Integer, SmoothFloat> mapSmoothValues = new HashMap<>();
   private static final CounterInt counterIds = new CounterInt(1);

   public static float getSmoothValue(int integer, float float2, float float3, float float4) {
      synchronized (mapSmoothValues) {
         Integer var8 = integer;
         SmoothFloat var9 = mapSmoothValues.get(var8);
         if (var9 == null) {
            var9 = new SmoothFloat(float2, float3, float4);
            mapSmoothValues.put(var8, var9);
         }

         return var9.getSmoothValue(float2, float3, float4);
      }
   }

   public static int getNextId() {
      synchronized (counterIds) {
         return counterIds.nextValue();
      }
   }

   public static void resetValues() {
      synchronized (mapSmoothValues) {
         mapSmoothValues.clear();
      }
   }
}
