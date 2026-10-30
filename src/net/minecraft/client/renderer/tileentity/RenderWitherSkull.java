package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.ResourceLocation;

public class RenderWitherSkull extends Render<EntityWitherSkull> {
   private static final ResourceLocation invulnerableWitherTextures = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
   private static final ResourceLocation witherTextures = new ResourceLocation("textures/entity/wither/wither.png");
   public ModelSkeletonHead j = new ModelSkeletonHead();

   public RenderWitherSkull(RenderManager renderManager) {
      super(renderManager);
   }

   private float func_82400_a(float float1, float float2, float float3) {
      float var4 = float2 - float1;

      while (var4 < -180.0F) {
         var4 += 360.0F;
      }

      while (var4 >= 180.0F) {
         var4 -= 360.0F;
      }

      return float1 + float3 * var4;
   }

   public void doRender(EntityWitherSkull entityWitherSkull, double double2, double double3, double double4, float float5, float float6) {
      GlStateManager.pushMatrix();
      GlStateManager.disableCull();
      float var10 = this.func_82400_a(entityWitherSkull.prevRotationYaw, entityWitherSkull.rotationYaw, float6);
      float var11 = entityWitherSkull.prevRotationPitch + (entityWitherSkull.rotationPitch - entityWitherSkull.prevRotationPitch) * float6;
      GlStateManager.translate((float)double2, (float)double3, (float)double4);
      GlStateManager.enableRescaleNormal();
      GlStateManager.scale(-1.0F, -1.0F, 1.0F);
      GlStateManager.enableAlpha();
      this.bindEntityTexture(entityWitherSkull);
      this.j.render(entityWitherSkull, 0.0F, 0.0F, 0.0F, var10, var11, 0.0625F);
      GlStateManager.popMatrix();
      super.doRender(entityWitherSkull, double2, double3, double4, float5, float6);
   }

   protected ResourceLocation getEntityTexture(EntityWitherSkull entityWitherSkull) {
      return entityWitherSkull.isInvulnerable() ? invulnerableWitherTextures : witherTextures;
   }
}
