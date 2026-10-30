package net.optifine.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.src.Config;

public class Blender {
   public static int parseBlend(String string) {
      if (string == null) {
         return 1;
      }

      string = string.toLowerCase().trim();
      if (string.equals("alpha")) {
         return 0;
      }

      if (string.equals("add")) {
         return 1;
      }

      if (string.equals("subtract")) {
         return 2;
      }

      if (string.equals("multiply")) {
         return 3;
      }

      if (string.equals("dodge")) {
         return 4;
      }

      if (string.equals("burn")) {
         return 5;
      }

      if (string.equals("screen")) {
         return 6;
      }

      if (string.equals("overlay")) {
         return 7;
      }

      if (string.equals("replace")) {
         return 8;
      }

      Config.Method252("Unknown blend: " + string);
      return 1;
   }

   public static void setupBlend(int integer, float float2) {
      switch (integer) {
         case 0:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, float2);
            break;
         case 1:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 1);
            GlStateManager.color(1.0F, 1.0F, 1.0F, float2);
            break;
         case 2:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(775, 0);
            GlStateManager.color(float2, float2, float2, 1.0F);
            break;
         case 3:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(774, 771);
            GlStateManager.color(float2, float2, float2, float2);
            break;
         case 4:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(1, 1);
            GlStateManager.color(float2, float2, float2, 1.0F);
            break;
         case 5:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(0, 769);
            GlStateManager.color(float2, float2, float2, 1.0F);
            break;
         case 6:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(1, 769);
            GlStateManager.color(float2, float2, float2, 1.0F);
            break;
         case 7:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(774, 768);
            GlStateManager.color(float2, float2, float2, 1.0F);
            break;
         case 8:
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, float2);
      }

      GlStateManager.enableTexture2D();
   }

   public static void clearBlend(float float1) {
      GlStateManager.disableAlpha();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 1);
      GlStateManager.color(1.0F, 1.0F, 1.0F, float1);
   }
}
