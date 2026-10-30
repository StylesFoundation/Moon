package net.optifine.util;

public class NumUtils {
   public static float Method970(float float1, float float2, float float3) {
      return float1 < float2 ? float2 : Math.min(float1, float3);
   }

   public static int mod(int integer1, int integer2) {
      int var5 = integer1 % integer2;
      if (var5 < 0) {
         var5 += integer2;
      }

      return var5;
   }
}
