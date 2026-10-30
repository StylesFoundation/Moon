package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.util.ResourceLocation;

public class RenderMagmaCube extends RenderLiving<EntityMagmaCube> {
   private static final ResourceLocation magmaCubeTextures = new ResourceLocation("textures/entity/slime/magmacube.png");

   public RenderMagmaCube(RenderManager renderManager) {
      super(renderManager, new ModelMagmaCube(), 0.25F);
   }

   protected ResourceLocation getEntityTexture(EntityMagmaCube entityMagmaCube) {
      return magmaCubeTextures;
   }

   protected void preRenderCallback(EntityMagmaCube entityMagmaCube, float float2) {
      int var3 = entityMagmaCube.getSlimeSize();
      float var4 = (entityMagmaCube.prevSquishFactor + (entityMagmaCube.squishFactor - entityMagmaCube.prevSquishFactor) * float2) / (var3 * 0.5F + 1.0F);
      float var5 = 1.0F / (var4 + 1.0F);
      float var6 = var3;
      GlStateManager.scale(var5 * var6, 1.0F / var5 * var6, var5 * var6);
   }
}
