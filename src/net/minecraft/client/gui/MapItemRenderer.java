package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;

public class MapItemRenderer {
   private static final ResourceLocation mapIcons = new ResourceLocation("textures/map/map_icons.png");
   private final TextureManager textureManager;
   private final Map<String, MapItemRenderer.Instance> loadedMaps = Maps.newHashMap();

   public MapItemRenderer(TextureManager textureManager) {
      this.textureManager = textureManager;
   }

   public void updateMapTexture(MapData mapData) {
      this.getMapRendererInstance(mapData).updateMapTexture();
   }

   public void renderMap(MapData mapData, boolean boolean2) {
      this.getMapRendererInstance(mapData).render(boolean2);
   }

   private MapItemRenderer.Instance getMapRendererInstance(MapData mapData) {
      MapItemRenderer.Instance var2 = this.loadedMaps.get(mapData.mapName);
      if (var2 == null) {
         var2 = new MapItemRenderer.Instance(mapData);
         this.loadedMaps.put(mapData.mapName, var2);
      }

      return var2;
   }

   public void clearLoadedMaps() {
      for (MapItemRenderer.Instance var2 : this.loadedMaps.values()) {
         this.textureManager.deleteTexture(var2.location);
      }

      this.loadedMaps.clear();
   }

   class Instance {
      private final MapData mapData;
      private final DynamicTexture mapTexture;
      private final ResourceLocation location;
      private final int[] mapTextureData;

      private Instance(MapData mapData) {
         this.mapData = mapData;
         this.mapTexture = new DynamicTexture(128, 128);
         this.mapTextureData = this.mapTexture.getTextureData();
         this.location = MapItemRenderer.this.textureManager.getDynamicTextureLocation("map/" + mapData.mapName, this.mapTexture);
         Arrays.fill(this.mapTextureData, 0);
      }

      private void updateMapTexture() {
         for (int var3 = 0; var3 < 16384; var3++) {
            int var4 = this.mapData.colors[var3] & 255;
            if (var4 / 4 == 0) {
               this.mapTextureData[var3] = (var3 + var3 / 128 & 1) * 8 + 16 << 24;
            } else {
               this.mapTextureData[var3] = MapColor.mapColorArray[var4 / 4].getMapColor(var4 & 3);
            }
         }

         this.mapTexture.updateDynamicTexture();
      }

      private void render(boolean boolean1) {
         Tessellator var6 = Tessellator.getInstance();
         WorldRenderer var7 = var6.getWorldRenderer();
         MapItemRenderer.this.textureManager.bindTexture(this.location);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(1, 771, 0, 1);
         GlStateManager.disableAlpha();
         var7.begin(7, DefaultVertexFormats.POSITION_TEX);
         var7.pos(0.0, 128.0, -0.01F).tex(0.0, 1.0).endVertex();
         var7.pos(128.0, 128.0, -0.01F).tex(1.0, 1.0).endVertex();
         var7.pos(128.0, 0.0, -0.01F).tex(1.0, 0.0).endVertex();
         var7.pos(0.0, 0.0, -0.01F).tex(0.0, 0.0).endVertex();
         var6.draw();
         GlStateManager.enableAlpha();
         GlStateManager.disableBlend();
         MapItemRenderer.this.textureManager.bindTexture(MapItemRenderer.mapIcons);
         int var9 = 0;

         for (Vec4b var11 : this.mapData.mapDecorations.values()) {
            if (!boolean1 || var11.Method972() == 1) {
               GlStateManager.pushMatrix();
               GlStateManager.translate(0.0F + var11.Method973() / 2.0F + 64.0F, 0.0F + var11.Method974() / 2.0F + 64.0F, -0.02F);
               GlStateManager.rotate(var11.Method975() * 360 / 16.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.scale(4.0F, 4.0F, 3.0F);
               GlStateManager.translate(-0.125F, 0.125F, 0.0F);
               byte var12 = var11.Method972();
               float var13 = var12 % 4 / 4.0F;
               float var14 = var12 / 4 / 4.0F;
               float var15 = (var12 % 4 + 1) / 4.0F;
               float var16 = (var12 / 4 + 1) / 4.0F;
               var7.begin(7, DefaultVertexFormats.POSITION_TEX);
               var7.pos(-1.0, 1.0, var9 * -0.001F).tex(var13, var14).endVertex();
               var7.pos(1.0, 1.0, var9 * -0.001F).tex(var15, var14).endVertex();
               var7.pos(1.0, -1.0, var9 * -0.001F).tex(var15, var16).endVertex();
               var7.pos(-1.0, -1.0, var9 * -0.001F).tex(var13, var16).endVertex();
               var6.draw();
               GlStateManager.popMatrix();
               var9++;
            }
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 0.0F, -0.04F);
         GlStateManager.scale(1.0F, 1.0F, 1.0F);
         GlStateManager.popMatrix();
      }
   }
}
