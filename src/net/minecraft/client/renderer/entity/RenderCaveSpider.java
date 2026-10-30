package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.util.ResourceLocation;

public class RenderCaveSpider extends RenderSpider<EntityCaveSpider> {
   private static final ResourceLocation caveSpiderTextures = new ResourceLocation("textures/entity/spider/cave_spider.png");

   public RenderCaveSpider(RenderManager renderManager) {
      super(renderManager);
      this.shadowSize *= 0.7F;
   }

   protected void preRenderCallback(EntityCaveSpider entityCaveSpider, float float2) {
      GlStateManager.scale(0.7F, 0.7F, 0.7F);
   }

   protected ResourceLocation getEntityTexture(EntityCaveSpider entityCaveSpider) {
      return caveSpiderTextures;
   }
}
