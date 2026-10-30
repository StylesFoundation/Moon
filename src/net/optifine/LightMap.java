package net.optifine;

import net.minecraft.src.Config;
import net.minecraft.world.World;

public class LightMap {
   private CustomColormap lightMapRgb = null;
   private final float[][] sunRgbs = new float[16][3];
   private final float[][] torchRgbs = new float[16][3];

   public LightMap(CustomColormap customColormap) {
      this.lightMapRgb = customColormap;
   }

   public CustomColormap getColormap() {
      return this.lightMapRgb;
   }

   public boolean updateLightmap(World world, float float2, int[] arr, boolean boolean4) {
      if (this.lightMapRgb == null) {
         return false;
      }

      int var8 = this.lightMapRgb.getHeight();
      if (boolean4 && var8 < 64) {
         return false;
      }

      int var9 = this.lightMapRgb.getWidth();
      if (var9 < 16) {
         warn("Invalid lightmap width: " + var9);
         this.lightMapRgb = null;
         return false;
      }

      int var10 = 0;
      if (boolean4) {
         var10 = var9 * 16 * 2;
      }

      float var11 = 1.1666666F * (world.getSunBrightness(1.0F) - 0.2F);
      if (world.getLastLightningBolt() > 0) {
         var11 = 1.0F;
      }

      var11 = Config.Method258(var11);
      float var12 = var11 * (var9 - 1);
      float var13 = Config.Method258(float2 + 0.5F) * (var9 - 1);
      float var14 = Config.Method258(Config.getGameSettings().gammaSetting);
      boolean var15 = var14 > 1.0E-4F;
      float[][] var16 = this.lightMapRgb.getColorsRgb();
      this.getLightMapColumn(var16, var12, var10, var9, this.sunRgbs);
      this.getLightMapColumn(var16, var13, var10 + 16 * var9, var9, this.torchRgbs);
      float[] var17 = new float[3];

      for (int var18 = 0; var18 < 16; var18++) {
         for (int var19 = 0; var19 < 16; var19++) {
            for (int var20 = 0; var20 < 3; var20++) {
               float var21 = Config.Method258(this.sunRgbs[var18][var20] + this.torchRgbs[var19][var20]);
               if (var15) {
                  float var22 = 1.0F - var21;
                  var22 = 1.0F - var22 * var22 * var22 * var22;
                  var21 = var14 * var22 + (1.0F - var14) * var21;
               }

               var17[var20] = var21;
            }

            int var24 = (int)(var17[0] * 255.0F);
            int var25 = (int)(var17[1] * 255.0F);
            int var27 = (int)(var17[2] * 255.0F);
            arr[var18 * 16 + var19] = 0xFF000000 | var24 << 16 | var25 << 8 | var27;
         }
      }

      return true;
   }

   private void getLightMapColumn(float[][] arr1, float float2, int integer3, int integer4, float[][] arr5) {
      int var9 = (int)Math.floor(float2);
      int var10 = (int)Math.ceil(float2);
      if (var9 == var10) {
         for (int var11 = 0; var11 < 16; var11++) {
            float[] var12 = arr1[integer3 + var11 * integer4 + var9];
            float[] var13 = arr5[var11];
            System.arraycopy(var12, 0, var13, 0, 3);
         }
      } else {
         float var18 = 1.0F - (float2 - var9);
         float var19 = 1.0F - (var10 - float2);

         for (int var20 = 0; var20 < 16; var20++) {
            float[] var14 = arr1[integer3 + var20 * integer4 + var9];
            float[] var15 = arr1[integer3 + var20 * integer4 + var10];
            float[] var16 = arr5[var20];

            for (int var17 = 0; var17 < 3; var17++) {
               var16[var17] = var14[var17] * var18 + var15[var17] * var19;
            }
         }
      }
   }

   private static void dbg(String string) {
      Config.Method251("CustomColors: " + string);
   }

   private static void warn(String string) {
      Config.Method252("CustomColors: " + string);
   }
}
