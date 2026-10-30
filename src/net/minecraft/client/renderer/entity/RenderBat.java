package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderBat extends RenderLiving<EntityBat> {
   private static final ResourceLocation batTextures = new ResourceLocation("textures/entity/bat.png");

   public RenderBat(RenderManager renderManager) {
      super(renderManager, new ModelBat(), 0.25F);
   }

   protected ResourceLocation getEntityTexture(EntityBat entityBat) {
      return batTextures;
   }

   protected void preRenderCallback(EntityBat entityBat, float float2) {
      GlStateManager.scale(0.35F, 0.35F, 0.35F);
   }

   protected void rotateCorpse(EntityBat entityBat, float float2, float float3, float float4) {
      if (!entityBat.getIsBatHanging()) {
         GlStateManager.translate(0.0F, MathHelper.cos(float2 * 0.3F) * 0.1F, 0.0F);
      } else {
         GlStateManager.translate(0.0F, -0.1F, 0.0F);
      }

      super.rotateCorpse(entityBat, float2, float3, float4);
   }
}
