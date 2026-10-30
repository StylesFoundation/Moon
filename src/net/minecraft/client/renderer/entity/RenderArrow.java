package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderArrow extends Render<EntityArrow> {
   private static final ResourceLocation arrowTextures = new ResourceLocation("textures/entity/arrow.png");

   public RenderArrow(RenderManager renderManager) {
      super(renderManager);
   }

   public void doRender(EntityArrow entityArrow, double double2, double double3, double double4, float float5, float float6) {
      this.bindEntityTexture(entityArrow);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)double2, (float)double3, (float)double4);
      GlStateManager.rotate(entityArrow.prevRotationYaw + (entityArrow.rotationYaw - entityArrow.prevRotationYaw) * float6 - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(entityArrow.prevRotationPitch + (entityArrow.rotationPitch - entityArrow.prevRotationPitch) * float6, 0.0F, 0.0F, 1.0F);
      Tessellator var12 = Tessellator.getInstance();
      WorldRenderer var13 = var12.getWorldRenderer();
      float var17 = 0.0F;
      float var18 = 0.15625F;
      float var21 = 0.15625F;
      float var22 = 0.3125F;
      GlStateManager.enableRescaleNormal();
      float var24 = entityArrow.arrowShake - float6;
      if (var24 > 0.0F) {
         GlStateManager.rotate(-MathHelper.sin(var24 * 3.0F) * var24, 0.0F, 0.0F, 1.0F);
      }

      GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(0.05625F, 0.05625F, 0.05625F);
      GlStateManager.translate(-4.0F, 0.0F, 0.0F);
      GL11.glNormal3f(0.05625F, 0.0F, 0.0F);
      var13.begin(7, DefaultVertexFormats.POSITION_TEX);
      var13.pos(-7.0, -2.0, -2.0).tex(0.0, 0.15625).endVertex();
      var13.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.15625).endVertex();
      var13.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.3125).endVertex();
      var13.pos(-7.0, 2.0, -2.0).tex(0.0, 0.3125).endVertex();
      var12.draw();
      GL11.glNormal3f(-0.05625F, 0.0F, 0.0F);
      var13.begin(7, DefaultVertexFormats.POSITION_TEX);
      var13.pos(-7.0, 2.0, -2.0).tex(0.0, 0.15625).endVertex();
      var13.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.15625).endVertex();
      var13.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.3125).endVertex();
      var13.pos(-7.0, -2.0, -2.0).tex(0.0, 0.3125).endVertex();
      var12.draw();

      for (int var25 = 0; var25 < 4; var25++) {
         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         GL11.glNormal3f(0.0F, 0.0F, 0.05625F);
         var13.begin(7, DefaultVertexFormats.POSITION_TEX);
         var13.pos(-8.0, -2.0, 0.0).tex(0.0, 0.0).endVertex();
         var13.pos(8.0, -2.0, 0.0).tex(0.5, 0.0).endVertex();
         var13.pos(8.0, 2.0, 0.0).tex(0.5, 0.15625).endVertex();
         var13.pos(-8.0, 2.0, 0.0).tex(0.0, 0.15625).endVertex();
         var12.draw();
      }

      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender(entityArrow, double2, double3, double4, float5, float6);
   }

   protected ResourceLocation getEntityTexture(EntityArrow entityArrow) {
      return arrowTextures;
   }
}
