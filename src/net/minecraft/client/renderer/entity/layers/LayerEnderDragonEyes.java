package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.shaders.Shaders;

public class LayerEnderDragonEyes implements LayerRenderer<EntityDragon> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
   private final RenderDragon dragonRenderer;

   public LayerEnderDragonEyes(RenderDragon renderDragon) {
      this.dragonRenderer = renderDragon;
   }

   public void doRenderLayer(EntityDragon entityDragon, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      if (!Minecraft.getMinecraft().getRenderManager().Method591()) {
         this.dragonRenderer.bindTexture(TEXTURE);
         GlStateManager.enableBlend();
         GlStateManager.disableAlpha();
         GlStateManager.blendFunc(1, 1);
         GlStateManager.disableLighting();
         GlStateManager.depthFunc(514);
         char var11 = '\uf0f0';
         char var12 = '\uf0f0';
         boolean var13 = false;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
         GlStateManager.enableLighting();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         if (Config.isShaders()) {
            Shaders.beginSpiderEyes();
         }

         Config.getRenderGlobal().renderOverlayEyes = true;
         this.dragonRenderer.getMainModel().render(entityDragon, float2, float3, float5, float6, float7, float8);
         Config.getRenderGlobal().renderOverlayEyes = false;
         if (Config.isShaders()) {
            Shaders.endSpiderEyes();
         }

         this.dragonRenderer.setLightmap(entityDragon, float4);
         GlStateManager.disableBlend();
         GlStateManager.enableAlpha();
         GlStateManager.depthFunc(515);
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
