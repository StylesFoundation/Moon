package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderBoat extends Render<EntityBoat> {
   private static final ResourceLocation boatTextures = new ResourceLocation("textures/entity/boat.png");
   public ModelBase i = new ModelBoat();

   public RenderBoat(RenderManager renderManager) {
      super(renderManager);
      this.shadowSize = 0.5F;
   }

   public void doRender(EntityBoat entityBoat, double double2, double double3, double double4, float float5, float float6) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)double2, (float)double3 + 0.25F, (float)double4);
      GlStateManager.rotate(180.0F - float5, 0.0F, 1.0F, 0.0F);
      float var10 = entityBoat.getTimeSinceHit() - float6;
      float var11 = entityBoat.getDamageTaken() - float6;
      if (var11 < 0.0F) {
         var11 = 0.0F;
      }

      if (var10 > 0.0F) {
         GlStateManager.rotate(MathHelper.sin(var10) * var10 * var11 / 10.0F * entityBoat.getForwardDirection(), 1.0F, 0.0F, 0.0F);
      }

      GlStateManager.scale(0.75F, 0.75F, 0.75F);
      GlStateManager.scale(1.3333334F, 1.3333334F, 1.3333334F);
      this.bindEntityTexture(entityBoat);
      GlStateManager.scale(-1.0F, -1.0F, 1.0F);
      this.i.render(entityBoat, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.popMatrix();
      super.doRender(entityBoat, double2, double3, double4, float5, float6);
   }

   protected ResourceLocation getEntityTexture(EntityBoat entityBoat) {
      return boatTextures;
   }
}
