package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.src.Config;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.optifine.CustomColors;

public class RenderXPOrb extends Render<EntityXPOrb> {
   private static final ResourceLocation experienceOrbTextures = new ResourceLocation("textures/entity/experience_orb.png");

   public RenderXPOrb(RenderManager renderManager) {
      super(renderManager);
      this.shadowSize = 0.15F;
      this.shadowOpaque = 0.75F;
   }

   public void doRender(EntityXPOrb entityXPOrb, double double2, double double3, double double4, float float5, float float6) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)double2, (float)double3, (float)double4);
      this.bindEntityTexture(entityXPOrb);
      int var12 = entityXPOrb.getTextureByXP();
      float var13 = var12 % 4 * 16 / 64.0F;
      float var14 = (var12 % 4 * 16 + 16) / 64.0F;
      float var15 = var12 / 4 * 16 / 64.0F;
      float var16 = (var12 / 4 * 16 + 16) / 64.0F;
      int var20 = entityXPOrb.getBrightnessForRender(float6);
      int var21 = var20 % 65536;
      int var22 = var20 / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var21, var22);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      float var24 = (entityXPOrb.xpColor + float6) / 2.0F;
      if (Config.isCustomColors()) {
         var24 = CustomColors.getXpOrbTimer(var24);
      }

      var22 = (int)((MathHelper.sin(var24 + 0.0F) + 1.0F) * 0.5F * 255.0F);
      int var26 = (int)((MathHelper.sin(var24 + (float) (Math.PI * 4.0 / 3.0)) + 1.0F) * 0.1F * 255.0F);
      GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(0.3F, 0.3F, 0.3F);
      Tessellator var28 = Tessellator.getInstance();
      WorldRenderer var29 = var28.getWorldRenderer();
      var29.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
      int var30 = var22;
      int var31 = 255;
      int var32 = var26;
      if (Config.isCustomColors()) {
         int var33 = CustomColors.getXpOrbColor(var24);
         if (var33 >= 0) {
            var30 = var33 >> 16 & 0xFF;
            var31 = var33 >> 8 & 0xFF;
            var32 = var33 & 0xFF;
         }
      }

      var29.pos(-0.5, -0.25, 0.0).tex(var13, var16).color(var30, var31, var32, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
      var29.pos(0.5, -0.25, 0.0).tex(var14, var16).color(var30, var31, var32, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
      var29.pos(0.5, 0.75, 0.0).tex(var14, var15).color(var30, var31, var32, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
      var29.pos(-0.5, 0.75, 0.0).tex(var13, var15).color(var30, var31, var32, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
      var28.draw();
      GlStateManager.disableBlend();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender(entityXPOrb, double2, double3, double4, float5, float6);
   }

   protected ResourceLocation getEntityTexture(EntityXPOrb entityXPOrb) {
      return experienceOrbTextures;
   }
}
