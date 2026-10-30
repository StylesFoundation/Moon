package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderFish extends Render<EntityFishHook> {
   private static final ResourceLocation FISH_PARTICLES = new ResourceLocation("textures/particle/particles.png");

   public RenderFish(RenderManager renderManager) {
      super(renderManager);
   }

   public void doRender(EntityFishHook entityFishHook, double double2, double double3, double double4, float float5, float float6) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)double2, (float)double3, (float)double4);
      GlStateManager.enableRescaleNormal();
      GlStateManager.scale(0.5F, 0.5F, 0.5F);
      this.bindEntityTexture(entityFishHook);
      Tessellator var12 = Tessellator.getInstance();
      WorldRenderer var13 = var12.getWorldRenderer();
      GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      var13.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
      var13.pos(-0.5, -0.5, 0.0).tex(0.0625, 0.1875).normal(0.0F, 1.0F, 0.0F).endVertex();
      var13.pos(0.5, -0.5, 0.0).tex(0.125, 0.1875).normal(0.0F, 1.0F, 0.0F).endVertex();
      var13.pos(0.5, 0.5, 0.0).tex(0.125, 0.125).normal(0.0F, 1.0F, 0.0F).endVertex();
      var13.pos(-0.5, 0.5, 0.0).tex(0.0625, 0.125).normal(0.0F, 1.0F, 0.0F).endVertex();
      var12.draw();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      if (entityFishHook.angler != null) {
         float var24 = MathHelper.sin(MathHelper.sqrt_float(entityFishHook.angler.getSwingProgress(float6)) * (float) Math.PI);
         Vec3 var25 = new Vec3(-0.36, 0.03, 0.35)
            .rotatePitch(
               -(entityFishHook.angler.prevRotationPitch + (entityFishHook.angler.rotationPitch - entityFishHook.angler.prevRotationPitch) * float6)
                  * (float) Math.PI
                  / 180.0F
            )
            .rotateYaw(
               -(entityFishHook.angler.prevRotationYaw + (entityFishHook.angler.rotationYaw - entityFishHook.angler.prevRotationYaw) * float6)
                  * (float) Math.PI
                  / 180.0F
            );
         var25 = var25.rotateYaw(var24 * 0.5F);
         var25 = var25.rotatePitch(-var24 * 0.7F);
         double var26 = entityFishHook.angler.prevPosX + (entityFishHook.angler.posX - entityFishHook.angler.prevPosX) * float6 + var25.xCoord;
         double var28 = entityFishHook.angler.prevPosY + (entityFishHook.angler.posY - entityFishHook.angler.prevPosY) * float6 + var25.yCoord;
         double var30 = entityFishHook.angler.prevPosZ + (entityFishHook.angler.posZ - entityFishHook.angler.prevPosZ) * float6 + var25.zCoord;
         double var32 = entityFishHook.angler.getEyeHeight();
         if (this.renderManager.options != null && this.renderManager.options.thirdPersonView > 0
            || entityFishHook.angler != Minecraft.getMinecraft().thePlayer) {
            float var34 = (
                  entityFishHook.angler.prevRenderYawOffset + (entityFishHook.angler.renderYawOffset - entityFishHook.angler.prevRenderYawOffset) * float6
               )
               * (float) Math.PI
               / 180.0F;
            double var35 = MathHelper.sin(var34);
            double var37 = MathHelper.cos(var34);
            var26 = entityFishHook.angler.prevPosX + (entityFishHook.angler.posX - entityFishHook.angler.prevPosX) * float6 - var37 * 0.35 - var35 * 0.8;
            var28 = entityFishHook.angler.prevPosY + var32 + (entityFishHook.angler.posY - entityFishHook.angler.prevPosY) * float6 - 0.45;
            var30 = entityFishHook.angler.prevPosZ + (entityFishHook.angler.posZ - entityFishHook.angler.prevPosZ) * float6 - var35 * 0.35 + var37 * 0.8;
            var32 = entityFishHook.angler.isSneaking() ? -0.1875 : 0.0;
         }

         double var51 = entityFishHook.prevPosX + (entityFishHook.posX - entityFishHook.prevPosX) * float6;
         double var36 = entityFishHook.prevPosY + (entityFishHook.posY - entityFishHook.prevPosY) * float6 + 0.25;
         double var38 = entityFishHook.prevPosZ + (entityFishHook.posZ - entityFishHook.prevPosZ) * float6;
         double var40 = (float)(var26 - var51);
         double var42 = (float)(var28 - var36) + var32;
         double var44 = (float)(var30 - var38);
         GlStateManager.disableTexture2D();
         GlStateManager.disableLighting();
         var13.begin(3, DefaultVertexFormats.POSITION_COLOR);

         for (int var47 = 0; var47 <= 16; var47++) {
            float var48 = var47 / 16.0F;
            var13.pos(double2 + var40 * var48, double3 + var42 * (var48 * var48 + var48) * 0.5 + 0.25, double4 + var44 * var48).color(0, 0, 0, 255).endVertex();
         }

         var12.draw();
         GlStateManager.enableLighting();
         GlStateManager.enableTexture2D();
         super.doRender(entityFishHook, double2, double3, double4, float5, float6);
      }
   }

   protected ResourceLocation getEntityTexture(EntityFishHook entityFishHook) {
      return FISH_PARTICLES;
   }
}
