package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.shaders.Shaders;

public class LayerSpiderEyes implements LayerRenderer<EntitySpider> {
   private static final ResourceLocation SPIDER_EYES = new ResourceLocation("textures/entity/spider_eyes.png");
   private final RenderSpider spiderRenderer;

   public LayerSpiderEyes(RenderSpider renderSpider) {
      this.spiderRenderer = renderSpider;
   }

   public void doRenderLayer(EntitySpider entitySpider, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      if (!Minecraft.getMinecraft().getRenderManager().Method591()) {
         this.spiderRenderer.bindTexture(SPIDER_EYES);
         GlStateManager.enableBlend();
         GlStateManager.disableAlpha();
         GlStateManager.blendFunc(1, 1);
         GlStateManager.depthMask(!entitySpider.isInvisible());
         int var11 = 61680;
         int var12 = 61680;
         int var13 = 0;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         if (Config.isShaders()) {
            Shaders.beginSpiderEyes();
         }

         Config.getRenderGlobal().renderOverlayEyes = true;
         this.spiderRenderer.getMainModel().render(entitySpider, float2, float3, float5, float6, float7, float8);
         Config.getRenderGlobal().renderOverlayEyes = false;
         if (Config.isShaders()) {
            Shaders.endSpiderEyes();
         }

         var11 = entitySpider.getBrightnessForRender(float4);
         var12 = var11 % 65536;
         var13 = var11 / 65536;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var12, var13);
         this.spiderRenderer.setLightmap(entitySpider, float4);
         GlStateManager.disableBlend();
         GlStateManager.enableAlpha();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
