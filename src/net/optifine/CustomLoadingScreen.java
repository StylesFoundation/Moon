package net.optifine;

import java.util.Properties;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;

public class CustomLoadingScreen {
   private final ResourceLocation locationTexture;
   private int scaleMode = 0;
   private int scale = 2;
   private final boolean center;

   public CustomLoadingScreen(ResourceLocation resourceLocation, int integer2, int integer3, boolean boolean4) {
      this.locationTexture = resourceLocation;
      this.scaleMode = integer2;
      this.scale = integer3;
      this.center = boolean4;
   }

   public static CustomLoadingScreen parseScreen(String string, int integer, Properties properties) {
      ResourceLocation var6 = new ResourceLocation(string);
      int var7 = parseScaleMode(getProperty("scaleMode", integer, properties));
      int var8 = var7 == 0 ? 2 : 1;
      int var9 = parseScale(getProperty("scale", integer, properties), var8);
      boolean var10 = Config.parseBoolean(getProperty("center", integer, properties), false);
      return new CustomLoadingScreen(var6, var7, var9, var10);
   }

   private static String getProperty(String string, int integer, Properties properties) {
      if (properties == null) {
         return null;
      }

      String var6 = properties.getProperty("dim" + integer + "." + string);
      if (var6 == null) {
         var6 = properties.getProperty(string);
      }

      return var6;
   }

   private static int parseScaleMode(String string) {
      if (string == null) {
         return 0;
      }

      string = string.toLowerCase().trim();
      if (string.equals("fixed")) {
         return 0;
      }

      if (string.equals("full")) {
         return 1;
      }

      if (string.equals("stretch")) {
         return 2;
      }

      CustomLoadingScreens.warn("Invalid scale mode: " + string);
      return 0;
   }

   private static int parseScale(String string, int integer) {
      if (string == null) {
         return integer;
      } else {
         string = string.trim();
         int var5 = Config.parseInt(string, -1);
         if (var5 < 1) {
            CustomLoadingScreens.warn("Invalid scale: " + string);
            return integer;
         } else {
            return var5;
         }
      }
   }

   public void drawBackground(int integer1, int integer2) {
      GlStateManager.disableLighting();
      GlStateManager.disableFog();
      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();
      Config.getTextureManager().bindTexture(this.locationTexture);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      double var8 = 16 * this.scale;
      double var10 = integer1 / var8;
      double var12 = integer2 / var8;
      double var14 = 0.0;
      double var16 = 0.0;
      if (this.center) {
         var14 = (var8 - integer1) / (var8 * 2.0);
         var16 = (var8 - integer2) / (var8 * 2.0);
      }

      switch (this.scaleMode) {
         case 1:
            var8 = Math.max(integer1, integer2);
            var10 = this.scale * integer1 / var8;
            var12 = this.scale * integer2 / var8;
            if (this.center) {
               var14 = this.scale * (var8 - integer1) / (var8 * 2.0);
               var16 = this.scale * (var8 - integer2) / (var8 * 2.0);
            }
            break;
         case 2:
            var10 = this.scale;
            var12 = this.scale;
            var14 = 0.0;
            var16 = 0.0;
      }

      var7.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      var7.pos(0.0, integer2, 0.0).tex(var14, var16 + var12).color(255, 255, 255, 255).endVertex();
      var7.pos(integer1, integer2, 0.0).tex(var14 + var10, var16 + var12).color(255, 255, 255, 255).endVertex();
      var7.pos(integer1, 0.0, 0.0).tex(var14 + var10, var16).color(255, 255, 255, 255).endVertex();
      var7.pos(0.0, 0.0, 0.0).tex(var14, var16).color(255, 255, 255, 255).endVertex();
      var6.draw();
   }
}
