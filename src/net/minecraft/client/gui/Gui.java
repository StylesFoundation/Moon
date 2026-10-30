package net.minecraft.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class Gui {
   public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
   public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
   public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
   protected float zLevel;

   protected void drawHorizontalLine(int integer1, int integer2, int integer3, int integer4) {
      if (integer2 < integer1) {
         int var5 = integer1;
         integer1 = integer2;
         integer2 = var5;
      }

      drawRect(integer1, integer3, integer2 + 1, integer3 + 1, integer4);
   }

   protected void drawVerticalLine(int integer1, int integer2, int integer3, int integer4) {
      if (integer3 < integer2) {
         int var5 = integer2;
         integer2 = integer3;
         integer3 = var5;
      }

      drawRect(integer1, integer2 + 1, integer1 + 1, integer3, integer4);
   }

   public static void drawRect(int integer1, int integer2, int integer3, int integer4, int integer5) {
      if (integer1 < integer3) {
         int var7 = integer1;
         integer1 = integer3;
         integer3 = var7;
      }

      if (integer2 < integer4) {
         int var13 = integer2;
         integer2 = integer4;
         integer4 = var13;
      }

      float var14 = (integer5 >> 24 & 0xFF) / 255.0F;
      float var8 = (integer5 >> 16 & 0xFF) / 255.0F;
      float var9 = (integer5 >> 8 & 0xFF) / 255.0F;
      float var10 = (integer5 & 0xFF) / 255.0F;
      Tessellator var11 = Tessellator.getInstance();
      WorldRenderer var12 = var11.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(var8, var9, var10, var14);
      var12.begin(7, DefaultVertexFormats.POSITION);
      var12.pos(integer1, integer4, 0.0).endVertex();
      var12.pos(integer3, integer4, 0.0).endVertex();
      var12.pos(integer3, integer2, 0.0).endVertex();
      var12.pos(integer1, integer2, 0.0).endVertex();
      var11.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   protected void drawGradientRect(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      float var9 = (integer5 >> 24 & 0xFF) / 255.0F;
      float var10 = (integer5 >> 16 & 0xFF) / 255.0F;
      float var11 = (integer5 >> 8 & 0xFF) / 255.0F;
      float var12 = (integer5 & 0xFF) / 255.0F;
      float var13 = (integer6 >> 24 & 0xFF) / 255.0F;
      float var14 = (integer6 >> 16 & 0xFF) / 255.0F;
      float var15 = (integer6 >> 8 & 0xFF) / 255.0F;
      float var16 = (integer6 & 0xFF) / 255.0F;
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      Tessellator var17 = Tessellator.getInstance();
      WorldRenderer var18 = var17.getWorldRenderer();
      var18.begin(7, DefaultVertexFormats.POSITION_COLOR);
      var18.pos(integer3, integer2, this.zLevel).color(var10, var11, var12, var9).endVertex();
      var18.pos(integer1, integer2, this.zLevel).color(var10, var11, var12, var9).endVertex();
      var18.pos(integer1, integer4, this.zLevel).color(var14, var15, var16, var13).endVertex();
      var18.pos(integer3, integer4, this.zLevel).color(var14, var15, var16, var13).endVertex();
      var17.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
   }

   public void drawCenteredString(FontRenderer fontRenderer, String string, int integer3, int integer4, int integer5) {
      fontRenderer.drawStringWithShadow(string, integer3 - fontRenderer.getStringWidth(string) / 2, integer4, integer5);
   }

   public void drawString(FontRenderer fontRenderer, String string, int integer3, int integer4, int integer5) {
      fontRenderer.drawStringWithShadow(string, integer3, integer4, integer5);
   }

   public void drawTexturedModalRect(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      Tessellator var11 = Tessellator.getInstance();
      WorldRenderer var12 = var11.getWorldRenderer();
      var12.begin(7, DefaultVertexFormats.POSITION_TEX);
      var12.pos(integer1, integer2 + integer6, this.zLevel).tex(integer3 * 0.00390625F, (integer4 + integer6) * 0.00390625F).endVertex();
      var12.pos(integer1 + integer5, integer2 + integer6, this.zLevel)
         .tex((integer3 + integer5) * 0.00390625F, (integer4 + integer6) * 0.00390625F)
         .endVertex();
      var12.pos(integer1 + integer5, integer2, this.zLevel).tex((integer3 + integer5) * 0.00390625F, integer4 * 0.00390625F).endVertex();
      var12.pos(integer1, integer2, this.zLevel).tex(integer3 * 0.00390625F, integer4 * 0.00390625F).endVertex();
      var11.draw();
   }

   public void drawTexturedModalRect(float float1, float float2, int integer3, int integer4, int integer5, int integer6) {
      Tessellator var11 = Tessellator.getInstance();
      WorldRenderer var12 = var11.getWorldRenderer();
      var12.begin(7, DefaultVertexFormats.POSITION_TEX);
      var12.pos(float1 + 0.0F, float2 + integer6, this.zLevel).tex(integer3 * 0.00390625F, (integer4 + integer6) * 0.00390625F).endVertex();
      var12.pos(float1 + integer5, float2 + integer6, this.zLevel).tex((integer3 + integer5) * 0.00390625F, (integer4 + integer6) * 0.00390625F).endVertex();
      var12.pos(float1 + integer5, float2 + 0.0F, this.zLevel).tex((integer3 + integer5) * 0.00390625F, integer4 * 0.00390625F).endVertex();
      var12.pos(float1 + 0.0F, float2 + 0.0F, this.zLevel).tex(integer3 * 0.00390625F, integer4 * 0.00390625F).endVertex();
      var11.draw();
   }

   public void drawTexturedModalRect(int integer1, int integer2, TextureAtlasSprite textureAtlasSprite, int integer4, int integer5) {
      Tessellator var8 = Tessellator.getInstance();
      WorldRenderer var9 = var8.getWorldRenderer();
      var9.begin(7, DefaultVertexFormats.POSITION_TEX);
      var9.pos(integer1, integer2 + integer5, this.zLevel).tex(textureAtlasSprite.getMinU(), textureAtlasSprite.getMaxV()).endVertex();
      var9.pos(integer1 + integer4, integer2 + integer5, this.zLevel).tex(textureAtlasSprite.getMaxU(), textureAtlasSprite.getMaxV()).endVertex();
      var9.pos(integer1 + integer4, integer2, this.zLevel).tex(textureAtlasSprite.getMaxU(), textureAtlasSprite.getMinV()).endVertex();
      var9.pos(integer1, integer2, this.zLevel).tex(textureAtlasSprite.getMinU(), textureAtlasSprite.getMinV()).endVertex();
      var8.draw();
   }

   public static void drawModalRectWithCustomSizedTexture(
      int integer1, int integer2, float float3, float float4, int integer5, int integer6, float float7, float float8
   ) {
      float var10 = 1.0F / float7;
      float var11 = 1.0F / float8;
      Tessellator var12 = Tessellator.getInstance();
      WorldRenderer var13 = var12.getWorldRenderer();
      var13.begin(7, DefaultVertexFormats.POSITION_TEX);
      var13.pos(integer1, integer2 + integer6, 0.0).tex(float3 * var10, (float4 + integer6) * var11).endVertex();
      var13.pos(integer1 + integer5, integer2 + integer6, 0.0).tex((float3 + integer5) * var10, (float4 + integer6) * var11).endVertex();
      var13.pos(integer1 + integer5, integer2, 0.0).tex((float3 + integer5) * var10, float4 * var11).endVertex();
      var13.pos(integer1, integer2, 0.0).tex(float3 * var10, float4 * var11).endVertex();
      var12.draw();
   }

   public static void drawScaledCustomSizeModalRect(
      int integer1, int integer2, float float3, float float4, int integer5, int integer6, int integer7, int integer8, float float9, float float10
   ) {
      float var12 = 1.0F / float9;
      float var13 = 1.0F / float10;
      Tessellator var14 = Tessellator.getInstance();
      WorldRenderer var15 = var14.getWorldRenderer();
      var15.begin(7, DefaultVertexFormats.POSITION_TEX);
      var15.pos(integer1, integer2 + integer8, 0.0).tex(float3 * var12, (float4 + integer6) * var13).endVertex();
      var15.pos(integer1 + integer7, integer2 + integer8, 0.0).tex((float3 + integer5) * var12, (float4 + integer6) * var13).endVertex();
      var15.pos(integer1 + integer7, integer2, 0.0).tex((float3 + integer5) * var12, float4 * var13).endVertex();
      var15.pos(integer1, integer2, 0.0).tex(float3 * var12, float4 * var13).endVertex();
      var14.draw();
   }
}
