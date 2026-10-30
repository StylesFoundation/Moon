package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.src.Config;
import net.optifine.shaders.Shaders;

public abstract class RenderLiving<T extends EntityLiving> extends RendererLivingEntity<T> {
   public RenderLiving(RenderManager renderManager, ModelBase modelBase, float float3) {
      super(renderManager, modelBase, float3);
   }

   protected boolean canRenderName(T entityLiving) {
      return super.canRenderName((T)entityLiving)
         && (entityLiving.getAlwaysRenderNameTagForRender() || entityLiving.hasCustomName() && entityLiving == this.renderManager.pointedEntity);
   }

   public boolean shouldRender(T entityLiving, ICamera iCamera, double double3, double double4, double double5) {
      if (super.shouldRender((T)entityLiving, iCamera, double3, double4, double5)) {
         return true;
      } else if (entityLiving.getLeashed() && entityLiving.getLeashedToEntity() != null) {
         Entity var9 = entityLiving.getLeashedToEntity();
         return iCamera.isBoundingBoxInFrustum(var9.getEntityBoundingBox());
      } else {
         return false;
      }
   }

   public void doRender(T entityLiving, double double2, double double3, double double4, float float5, float float6) {
      super.doRender((T)entityLiving, double2, double3, double4, float5, float6);
      this.renderLeash((T)entityLiving, double2, double3, double4, float5, float6);
   }

   public void setLightmap(T entityLiving, float float2) {
      int var5 = entityLiving.getBrightnessForRender(float2);
      int var6 = var5 % 65536;
      int var7 = var5 / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6, var7);
   }

   private double interpolateValue(double double1, double double2, double double3) {
      return double1 + (double2 - double1) * double3;
   }

   protected void renderLeash(T entityLiving, double double2, double double3, double double4, float float5, float float6) {
      if (!Config.isShaders() || !Shaders.isShadowPass) {
         Entity var12 = entityLiving.getLeashedToEntity();
         if (var12 != null) {
            double3 -= (1.6 - entityLiving.height) * 0.5;
            Tessellator var13 = Tessellator.getInstance();
            WorldRenderer var14 = var13.getWorldRenderer();
            double var15 = this.interpolateValue(var12.prevRotationYaw, var12.rotationYaw, float6 * 0.5F) * (float) (Math.PI / 180.0);
            double var17 = this.interpolateValue(var12.prevRotationPitch, var12.rotationPitch, float6 * 0.5F) * (float) (Math.PI / 180.0);
            double var19 = Math.cos(var15);
            double var21 = Math.sin(var15);
            double var23 = Math.sin(var17);
            if (var12 instanceof EntityHanging) {
               var19 = 0.0;
               var21 = 0.0;
               var23 = -1.0;
            }

            double var25 = Math.cos(var17);
            double var27 = this.interpolateValue(var12.prevPosX, var12.posX, float6) - var19 * 0.7 - var21 * 0.5 * var25;
            double var29 = this.interpolateValue(var12.prevPosY + var12.getEyeHeight() * 0.7, var12.posY + var12.getEyeHeight() * 0.7, float6)
               - var23 * 0.5
               - 0.25;
            double var31 = this.interpolateValue(var12.prevPosZ, var12.posZ, float6) - var21 * 0.7 + var19 * 0.5 * var25;
            double var33 = this.interpolateValue(entityLiving.prevRenderYawOffset, entityLiving.renderYawOffset, float6) * (float) (Math.PI / 180.0)
               + (Math.PI / 2);
            var19 = Math.cos(var33) * entityLiving.width * 0.4;
            var21 = Math.sin(var33) * entityLiving.width * 0.4;
            double var35 = this.interpolateValue(entityLiving.prevPosX, entityLiving.posX, float6) + var19;
            double var37 = this.interpolateValue(entityLiving.prevPosY, entityLiving.posY, float6);
            double var39 = this.interpolateValue(entityLiving.prevPosZ, entityLiving.posZ, float6) + var21;
            double2 += var19;
            double4 += var21;
            double var41 = (float)(var27 - var35);
            double var43 = (float)(var29 - var37);
            double var45 = (float)(var31 - var39);
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            if (Config.isShaders()) {
               Shaders.beginLeash();
            }

            var14.begin(5, DefaultVertexFormats.POSITION_COLOR);

            for (int var50 = 0; var50 <= 24; var50++) {
               float var51 = 0.5F;
               float var52 = 0.4F;
               float var53 = 0.3F;
               if (var50 % 2 == 0) {
                  var51 = 0.35F;
                  var52 = 0.28F;
                  var53 = 0.21000001F;
               }

               float var54 = var50 / 24.0F;
               var14.pos(
                     double2 + var41 * var54 + 0.0,
                     double3 + var43 * (var54 * var54 + var54) * 0.5 + ((24.0F - var50) / 18.0F + 0.125F),
                     double4 + var45 * var54
                  )
                  .color(var51, var52, var53, 1.0F)
                  .endVertex();
               var14.pos(
                     double2 + var41 * var54 + 0.025,
                     double3 + var43 * (var54 * var54 + var54) * 0.5 + ((24.0F - var50) / 18.0F + 0.125F) + 0.025,
                     double4 + var45 * var54
                  )
                  .color(var51, var52, var53, 1.0F)
                  .endVertex();
            }

            var13.draw();
            var14.begin(5, DefaultVertexFormats.POSITION_COLOR);

            for (int var60 = 0; var60 <= 24; var60++) {
               float var61 = 0.5F;
               float var62 = 0.4F;
               float var63 = 0.3F;
               if (var60 % 2 == 0) {
                  var61 = 0.35F;
                  var62 = 0.28F;
                  var63 = 0.21000001F;
               }

               float var64 = var60 / 24.0F;
               var14.pos(
                     double2 + var41 * var64 + 0.0,
                     double3 + var43 * (var64 * var64 + var64) * 0.5 + ((24.0F - var60) / 18.0F + 0.125F) + 0.025,
                     double4 + var45 * var64
                  )
                  .color(var61, var62, var63, 1.0F)
                  .endVertex();
               var14.pos(
                     double2 + var41 * var64 + 0.025,
                     double3 + var43 * (var64 * var64 + var64) * 0.5 + ((24.0F - var60) / 18.0F + 0.125F),
                     double4 + var45 * var64 + 0.025
                  )
                  .color(var61, var62, var63, 1.0F)
                  .endVertex();
            }

            var13.draw();
            if (Config.isShaders()) {
               Shaders.endLeash();
            }

            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.enableCull();
         }
      }
   }

   public static int Method732() {
      return 92;
   }
}
