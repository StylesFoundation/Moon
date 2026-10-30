package net.optifine;

import net.minecraft.world.World;

public class LightMapPack {
   private final LightMap lightMap;
   private final LightMap lightMapRain;
   private final LightMap lightMapThunder;
   private int[] colorBuffer1 = new int[0];
   private int[] colorBuffer2 = new int[0];

   public LightMapPack(LightMap lightMap1, LightMap lightMap2, LightMap lightMap3) {
      if (lightMap2 != null || lightMap3 != null) {
         if (lightMap2 == null) {
            lightMap2 = lightMap1;
         }

         if (lightMap3 == null) {
            lightMap3 = lightMap2;
         }
      }

      this.lightMap = lightMap1;
      this.lightMapRain = lightMap2;
      this.lightMapThunder = lightMap3;
   }

   public boolean updateLightmap(World world, float float2, int[] arr, boolean boolean4, float float5) {
      if (this.lightMapRain == null && this.lightMapThunder == null) {
         return this.lightMap.updateLightmap(world, float2, arr, boolean4);
      }

      int var9 = world.provider.getDimensionId();
      if (var9 != 1 && var9 != -1) {
         float var10 = world.getRainStrength(float5);
         float var11 = world.getThunderStrength(float5);
         boolean var13 = var10 > 1.0E-4F;
         boolean var14 = var11 > 1.0E-4F;
         if (!var13 && !var14) {
            return this.lightMap.updateLightmap(world, float2, arr, boolean4);
         }

         if (var10 > 0.0F) {
            var11 /= var10;
         }

         float var15 = 1.0F - var10;
         float var16 = var10 - var11;
         if (this.colorBuffer1.length != arr.length) {
            this.colorBuffer1 = new int[arr.length];
            this.colorBuffer2 = new int[arr.length];
         }

         int var17 = 0;
         int[][] var18 = new int[][]{arr, this.colorBuffer1, this.colorBuffer2};
         float[] var19 = new float[3];
         if (var15 > 1.0E-4F && this.lightMap.updateLightmap(world, float2, var18[var17], boolean4)) {
            var19[var17] = var15;
            var17++;
         }

         if (var16 > 1.0E-4F && this.lightMapRain != null && this.lightMapRain.updateLightmap(world, float2, var18[var17], boolean4)) {
            var19[var17] = var16;
            var17++;
         }

         if (var11 > 1.0E-4F && this.lightMapThunder != null && this.lightMapThunder.updateLightmap(world, float2, var18[var17], boolean4)) {
            var19[var17] = var11;
            var17++;
         }

         return var17 == 2
            ? this.blend(var18[0], var19[0], var18[1], var19[1])
            : var17 != 3 || this.blend(var18[0], var19[0], var18[1], var19[1], var18[2], var19[2]);
      } else {
         return this.lightMap.updateLightmap(world, float2, arr, boolean4);
      }
   }

   private boolean blend(int[] arr1, float float2, int[] arr3, float float4) {
      if (arr3.length != arr1.length) {
         return false;
      }

      for (int var8 = 0; var8 < arr1.length; var8++) {
         int var9 = arr1[var8];
         int var10 = var9 >> 16 & 0xFF;
         int var11 = var9 >> 8 & 0xFF;
         int var12 = var9 & 0xFF;
         int var13 = arr3[var8];
         int var14 = var13 >> 16 & 0xFF;
         int var15 = var13 >> 8 & 0xFF;
         int var16 = var13 & 0xFF;
         int var17 = (int)(var10 * float2 + var14 * float4);
         int var18 = (int)(var11 * float2 + var15 * float4);
         int var19 = (int)(var12 * float2 + var16 * float4);
         arr1[var8] = 0xFF000000 | var17 << 16 | var18 << 8 | var19;
      }

      return true;
   }

   private boolean blend(int[] arr1, float float2, int[] arr3, float float4, int[] arr5, float float6) {
      if (arr3.length == arr1.length && arr5.length == arr1.length) {
         for (int var10 = 0; var10 < arr1.length; var10++) {
            int var11 = arr1[var10];
            int var12 = var11 >> 16 & 0xFF;
            int var13 = var11 >> 8 & 0xFF;
            int var14 = var11 & 0xFF;
            int var15 = arr3[var10];
            int var16 = var15 >> 16 & 0xFF;
            int var17 = var15 >> 8 & 0xFF;
            int var18 = var15 & 0xFF;
            int var19 = arr5[var10];
            int var20 = var19 >> 16 & 0xFF;
            int var21 = var19 >> 8 & 0xFF;
            int var22 = var19 & 0xFF;
            int var23 = (int)(var12 * float2 + var16 * float4 + var20 * float6);
            int var24 = (int)(var13 * float2 + var17 * float4 + var21 * float6);
            int var25 = (int)(var14 * float2 + var18 * float4 + var22 * float6);
            arr1[var10] = 0xFF000000 | var23 << 16 | var24 << 8 | var25;
         }

         return true;
      } else {
         return false;
      }
   }
}
