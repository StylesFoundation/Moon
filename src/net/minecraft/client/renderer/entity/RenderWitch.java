package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItemWitch;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.util.ResourceLocation;

public class RenderWitch extends RenderLiving<EntityWitch> {
   private static final ResourceLocation witchTextures = new ResourceLocation("textures/entity/witch.png");

   public RenderWitch(RenderManager renderManager) {
      super(renderManager, new ModelWitch(0.0F), 0.5F);
      this.addLayer(new LayerHeldItemWitch(this));
   }

   public void doRender(EntityWitch entityWitch, double double2, double double3, double double4, float float5, float float6) {
      ((ModelWitch)this.mainModel).field_82900_g = entityWitch.getHeldItem() != null;
      super.doRender(entityWitch, double2, double3, double4, float5, float6);
   }

   protected ResourceLocation getEntityTexture(EntityWitch entityWitch) {
      return witchTextures;
   }

   @Override
   public void transformHeldFull3DItemLayer() {
      GlStateManager.translate(0.0F, 0.1875F, 0.0F);
   }

   protected void preRenderCallback(EntityWitch entityWitch, float float2) {
      GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
   }
}
