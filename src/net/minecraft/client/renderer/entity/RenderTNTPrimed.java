package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderTNTPrimed extends Render<EntityTNTPrimed> {
   public RenderTNTPrimed(RenderManager renderManager) {
      super(renderManager);
      this.shadowSize = 0.5F;
   }

   public void doRender(EntityTNTPrimed entityTNTPrimed, double double2, double double3, double double4, float float5, float float6) {
      BlockRendererDispatcher var12 = Minecraft.getMinecraft().getBlockRendererDispatcher();
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)double2, (float)double3 + 0.5F, (float)double4);
      if (entityTNTPrimed.fuse - float6 + 1.0F < 10.0F) {
         float var13 = MathHelper.clamp_float(1.0F - (entityTNTPrimed.fuse - float6 + 1.0F) / 10.0F, 0.0F, 1.0F);
         var13 *= var13;
         var13 *= var13;
         float var14 = 1.0F + var13 * 0.3F;
         GlStateManager.scale(var14, var14, var14);
      }

      float var17 = (1.0F - (entityTNTPrimed.fuse - float6 + 1.0F) / 100.0F) * 0.8F;
      this.bindEntityTexture(entityTNTPrimed);
      GlStateManager.translate(-0.5F, -0.5F, 0.5F);
      var12.renderBlockBrightness(Blocks.tnt.getDefaultState(), entityTNTPrimed.getBrightness(float6));
      GlStateManager.translate(0.0F, 0.0F, 1.0F);
      if (entityTNTPrimed.fuse / 5 % 2 == 0) {
         GlStateManager.disableTexture2D();
         GlStateManager.disableLighting();
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 772);
         GlStateManager.color(1.0F, 1.0F, 1.0F, var17);
         GlStateManager.doPolygonOffset(-3.0F, -3.0F);
         GlStateManager.enablePolygonOffset();
         var12.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0F);
         GlStateManager.doPolygonOffset(0.0F, 0.0F);
         GlStateManager.disablePolygonOffset();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableBlend();
         GlStateManager.enableLighting();
         GlStateManager.enableTexture2D();
      }

      GlStateManager.popMatrix();
      super.doRender(entityTNTPrimed, double2, double3, double4, float5, float6);
   }

   protected ResourceLocation getEntityTexture(EntityTNTPrimed entityTNTPrimed) {
      return TextureMap.locationBlocksTexture;
   }
}
