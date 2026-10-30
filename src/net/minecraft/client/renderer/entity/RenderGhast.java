package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.ResourceLocation;

public class RenderGhast extends RenderLiving<EntityGhast> {
   private static final ResourceLocation ghastTextures = new ResourceLocation("textures/entity/ghast/ghast.png");
   private static final ResourceLocation ghastShootingTextures = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");

   public RenderGhast(RenderManager renderManager) {
      super(renderManager, new ModelGhast(), 0.5F);
   }

   protected ResourceLocation getEntityTexture(EntityGhast entityGhast) {
      return entityGhast.isAttacking() ? ghastShootingTextures : ghastTextures;
   }

   protected void preRenderCallback(EntityGhast entityGhast, float float2) {
      GlStateManager.scale(4.5F, 4.5F, 4.5F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
