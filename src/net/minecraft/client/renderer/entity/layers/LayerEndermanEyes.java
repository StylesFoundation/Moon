package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.shaders.Shaders;

public class LayerEndermanEyes implements LayerRenderer<EntityEnderman> {
   private static final ResourceLocation RES_ENDERMAN_EYES = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
   private final RenderEnderman endermanRenderer;

   public LayerEndermanEyes(RenderEnderman renderEnderman) {
      this.endermanRenderer = renderEnderman;
   }

   public void doRenderLayer(EntityEnderman entityEnderman, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      if (!Minecraft.getMinecraft().getRenderManager().Method591()) {
         this.endermanRenderer.bindTexture(RES_ENDERMAN_EYES);
         GlStateManager.enableBlend();
         GlStateManager.disableAlpha();
         GlStateManager.blendFunc(1, 1);
         GlStateManager.disableLighting();
         GlStateManager.depthMask(!entityEnderman.isInvisible());
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
         this.endermanRenderer.getMainModel().render(entityEnderman, float2, float3, float5, float6, float7, float8);
         Config.getRenderGlobal().renderOverlayEyes = false;
         if (Config.isShaders()) {
            Shaders.endSpiderEyes();
         }

         this.endermanRenderer.setLightmap(entityEnderman, float4);
         GlStateManager.depthMask(true);
         GlStateManager.disableBlend();
         GlStateManager.enableAlpha();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
