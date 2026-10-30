package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEntity extends Render<Entity> {
   public RenderEntity(RenderManager renderManager) {
      super(renderManager);
   }

   @Override
   public void doRender(Entity entity, double double2, double double3, double double4, float float5, float float6) {
      GlStateManager.pushMatrix();
      renderOffsetAABB(entity.getEntityBoundingBox(), double2 - entity.lastTickPosX, double3 - entity.lastTickPosY, double4 - entity.lastTickPosZ);
      GlStateManager.popMatrix();
      super.doRender(entity, double2, double3, double4, float5, float6);
   }

   @Override
   protected ResourceLocation getEntityTexture(Entity entity) {
      return null;
   }
}
