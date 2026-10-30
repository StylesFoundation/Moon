package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerSlimeGel;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;

public class RenderSlime extends RenderLiving<EntitySlime> {
   private static final ResourceLocation slimeTextures = new ResourceLocation("textures/entity/slime/slime.png");

   public RenderSlime(RenderManager renderManager, ModelBase modelBase, float float3) {
      super(renderManager, modelBase, float3);
      this.addLayer(new LayerSlimeGel(this));
   }

   public void doRender(EntitySlime entitySlime, double double2, double double3, double double4, float float5, float float6) {
      this.shadowSize = 0.25F * entitySlime.getSlimeSize();
      super.doRender(entitySlime, double2, double3, double4, float5, float6);
   }

   protected void preRenderCallback(EntitySlime entitySlime, float float2) {
      float var3 = entitySlime.getSlimeSize();
      float var4 = (entitySlime.prevSquishFactor + (entitySlime.squishFactor - entitySlime.prevSquishFactor) * float2) / (var3 * 0.5F + 1.0F);
      float var5 = 1.0F / (var4 + 1.0F);
      GlStateManager.scale(var5 * var3, 1.0F / var5 * var3, var5 * var3);
   }

   protected ResourceLocation getEntityTexture(EntitySlime entitySlime) {
      return slimeTextures;
   }
}
