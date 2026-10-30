package net.optifine.util;

public class SmoothFloat {
   private float valueLast;
   private float timeFadeUpSec;
   private float timeFadeDownSec;
   private long timeLastMs;

   public SmoothFloat(float float1, float float2) {
      this(float1, float2, float2);
   }

   public SmoothFloat(float float1, float float2, float float3) {
      this.valueLast = float1;
      this.timeFadeUpSec = float2;
      this.timeFadeDownSec = float3;
      this.timeLastMs = System.currentTimeMillis();
   }

   public float getValueLast() {
      return this.valueLast;
   }

   public float getTimeFadeUpSec() {
      return this.timeFadeUpSec;
   }

   public float getTimeFadeDownSec() {
      return this.timeFadeDownSec;
   }

   public long getTimeLastMs() {
      return this.timeLastMs;
   }

   public float getSmoothValue(float float1, float float2, float float3) {
      this.timeFadeUpSec = float2;
      this.timeFadeDownSec = float3;
      return this.getSmoothValue(float1);
   }

   public float getSmoothValue(float float1) {
      long var5 = System.currentTimeMillis();
      float var7 = this.valueLast;
      long var8 = this.timeLastMs;
      float var10 = (float)(var5 - var8) / 1000.0F;
      float var11 = float1 >= var7 ? this.timeFadeUpSec : this.timeFadeDownSec;
      float var12 = getSmoothValue(var7, float1, var10, var11);
      this.valueLast = var12;
      this.timeLastMs = var5;
      return var12;
   }

   public static float getSmoothValue(float float1, float float2, float float3, float float4) {
      if (float3 <= 0.0F) {
         return float1;
      }

      float var7 = float2 - float1;
      float var8;
      if (float4 > 0.0F && float3 < float4 && Math.abs(var7) > 1.0E-6F) {
         float var9 = float4 / float3;
         float var13 = 4.61F - 1.0F / (0.13F + var9 / 10.0F);
         float var14 = NumUtils.Method970(float3 / float4 * var13, 0.0F, 1.0F);
         var8 = float1 + var7 * var14;
      } else {
         var8 = float2;
      }

      return var8;
   }
}
