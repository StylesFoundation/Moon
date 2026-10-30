package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelLeashKnot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.util.ResourceLocation;

public class RenderLeashKnot extends Render<EntityLeashKnot> {
   private static final ResourceLocation leashKnotTextures = new ResourceLocation("textures/entity/lead_knot.png");
   public ModelLeashKnot i = new ModelLeashKnot();

   public RenderLeashKnot(RenderManager renderManager) {
      super(renderManager);
   }

   public void doRender(EntityLeashKnot entityLeashKnot, double double2, double double3, double double4, float float5, float float6) {
      GlStateManager.pushMatrix();
      GlStateManager.disableCull();
      GlStateManager.translate((float)double2, (float)double3, (float)double4);
      GlStateManager.enableRescaleNormal();
      GlStateManager.scale(-1.0F, -1.0F, 1.0F);
      GlStateManager.enableAlpha();
      this.bindEntityTexture(entityLeashKnot);
      this.i.render(entityLeashKnot, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.popMatrix();
      super.doRender(entityLeashKnot, double2, double3, double4, float5, float6);
   }

   protected ResourceLocation getEntityTexture(EntityLeashKnot entityLeashKnot) {
      return leashKnotTextures;
   }
}
