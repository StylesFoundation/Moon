package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelGuardian;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class RenderGuardian extends RenderLiving<EntityGuardian> {
   private static final ResourceLocation GUARDIAN_TEXTURE = new ResourceLocation("textures/entity/guardian.png");
   private static final ResourceLocation GUARDIAN_ELDER_TEXTURE = new ResourceLocation("textures/entity/guardian_elder.png");
   private static final ResourceLocation GUARDIAN_BEAM_TEXTURE = new ResourceLocation("textures/entity/guardian_beam.png");
   int field_177115_a = ((ModelGuardian)this.mainModel).func_178706_a();

   public RenderGuardian(RenderManager renderManager) {
      super(renderManager, new ModelGuardian(), 0.5F);
   }

   public boolean shouldRender(EntityGuardian entityGuardian, ICamera iCamera, double double3, double double4, double double5) {
      if (super.shouldRender(entityGuardian, iCamera, double3, double4, double5)) {
         return true;
      }

      if (entityGuardian.hasTargetedEntity()) {
         EntityLivingBase var9 = entityGuardian.getTargetedEntity();
         if (var9 != null) {
            Vec3 var10 = this.func_177110_a(var9, var9.height * 0.5, 1.0F);
            Vec3 var11 = this.func_177110_a(entityGuardian, entityGuardian.getEyeHeight(), 1.0F);
            return iCamera.isBoundingBoxInFrustum(AxisAlignedBB.fromBounds(var11.xCoord, var11.yCoord, var11.zCoord, var10.xCoord, var10.yCoord, var10.zCoord));
         }
      }

      return false;
   }

   private Vec3 func_177110_a(EntityLivingBase entityLivingBase, double double2, float float3) {
      double var5 = entityLivingBase.lastTickPosX + (entityLivingBase.posX - entityLivingBase.lastTickPosX) * float3;
      double var7 = double2 + entityLivingBase.lastTickPosY + (entityLivingBase.posY - entityLivingBase.lastTickPosY) * float3;
      double var9 = entityLivingBase.lastTickPosZ + (entityLivingBase.posZ - entityLivingBase.lastTickPosZ) * float3;
      return new Vec3(var5, var7, var9);
   }

   public void doRender(EntityGuardian entityGuardian, double double2, double double3, double double4, float float5, float float6) {
      if (this.field_177115_a != ((ModelGuardian)this.mainModel).func_178706_a()) {
         this.mainModel = new ModelGuardian();
         this.field_177115_a = ((ModelGuardian)this.mainModel).func_178706_a();
      }

      super.doRender(entityGuardian, double2, double3, double4, float5, float6);
      EntityLivingBase var12 = entityGuardian.getTargetedEntity();
      if (var12 != null) {
         float var13 = entityGuardian.func_175477_p(float6);
         Tessellator var14 = Tessellator.getInstance();
         WorldRenderer var15 = var14.getWorldRenderer();
         this.bindTexture(GUARDIAN_BEAM_TEXTURE);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
         GlStateManager.disableLighting();
         GlStateManager.disableCull();
         GlStateManager.disableBlend();
         GlStateManager.depthMask(true);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
         GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
         float var17 = (float)entityGuardian.worldObj.getTotalWorldTime() + float6;
         float var18 = var17 * 0.5F % 1.0F;
         float var19 = entityGuardian.getEyeHeight();
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)double2, (float)double3 + var19, (float)double4);
         Vec3 var20 = this.func_177110_a(var12, var12.height * 0.5, float6);
         Vec3 var21 = this.func_177110_a(entityGuardian, var19, float6);
         Vec3 var22 = var20.subtract(var21);
         double var23 = var22.lengthVector() + 1.0;
         var22 = var22.normalize();
         float var25 = (float)Math.acos(var22.yCoord);
         float var26 = (float)Math.atan2(var22.zCoord, var22.xCoord);
         GlStateManager.rotate(((float) (Math.PI / 2) - var26) * (180.0F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(var25 * (180.0F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
         double var28 = var17 * 0.05 * -1.5;
         var15.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         float var30 = var13 * var13;
         int var31 = 64 + (int)(var30 * 240.0F);
         int var32 = 32 + (int)(var30 * 192.0F);
         int var33 = 128 - (int)(var30 * 64.0F);
         double var38 = 0.0 + Math.cos(var28 + (Math.PI * 3.0 / 4.0)) * 0.282;
         double var40 = 0.0 + Math.sin(var28 + (Math.PI * 3.0 / 4.0)) * 0.282;
         double var42 = 0.0 + Math.cos(var28 + (Math.PI / 4)) * 0.282;
         double var44 = 0.0 + Math.sin(var28 + (Math.PI / 4)) * 0.282;
         double var46 = 0.0 + Math.cos(var28 + (Math.PI * 5.0 / 4.0)) * 0.282;
         double var48 = 0.0 + Math.sin(var28 + (Math.PI * 5.0 / 4.0)) * 0.282;
         double var50 = 0.0 + Math.cos(var28 + (Math.PI * 7.0 / 4.0)) * 0.282;
         double var52 = 0.0 + Math.sin(var28 + (Math.PI * 7.0 / 4.0)) * 0.282;
         double var54 = 0.0 + Math.cos(var28 + Math.PI) * 0.2;
         double var56 = 0.0 + Math.sin(var28 + Math.PI) * 0.2;
         double var58 = 0.0 + Math.cos(var28 + 0.0) * 0.2;
         double var60 = 0.0 + Math.sin(var28 + 0.0) * 0.2;
         double var62 = 0.0 + Math.cos(var28 + (Math.PI / 2)) * 0.2;
         double var64 = 0.0 + Math.sin(var28 + (Math.PI / 2)) * 0.2;
         double var66 = 0.0 + Math.cos(var28 + (Math.PI * 3.0 / 2.0)) * 0.2;
         double var68 = 0.0 + Math.sin(var28 + (Math.PI * 3.0 / 2.0)) * 0.2;
         double var74 = -1.0F + var18;
         double var76 = var23 * 2.5 + var74;
         var15.pos(var54, var23, var56).tex(0.4999, var76).color(var31, var32, var33, 255).endVertex();
         var15.pos(var54, 0.0, var56).tex(0.4999, var74).color(var31, var32, var33, 255).endVertex();
         var15.pos(var58, 0.0, var60).tex(0.0, var74).color(var31, var32, var33, 255).endVertex();
         var15.pos(var58, var23, var60).tex(0.0, var76).color(var31, var32, var33, 255).endVertex();
         var15.pos(var62, var23, var64).tex(0.4999, var76).color(var31, var32, var33, 255).endVertex();
         var15.pos(var62, 0.0, var64).tex(0.4999, var74).color(var31, var32, var33, 255).endVertex();
         var15.pos(var66, 0.0, var68).tex(0.0, var74).color(var31, var32, var33, 255).endVertex();
         var15.pos(var66, var23, var68).tex(0.0, var76).color(var31, var32, var33, 255).endVertex();
         double var78 = 0.0;
         if (entityGuardian.ticksExisted % 2 == 0) {
            var78 = 0.5;
         }

         var15.pos(var38, var23, var40).tex(0.5, var78 + 0.5).color(var31, var32, var33, 255).endVertex();
         var15.pos(var42, var23, var44).tex(1.0, var78 + 0.5).color(var31, var32, var33, 255).endVertex();
         var15.pos(var50, var23, var52).tex(1.0, var78).color(var31, var32, var33, 255).endVertex();
         var15.pos(var46, var23, var48).tex(0.5, var78).color(var31, var32, var33, 255).endVertex();
         var14.draw();
         GlStateManager.popMatrix();
      }
   }

   protected void preRenderCallback(EntityGuardian entityGuardian, float float2) {
      if (entityGuardian.isElder()) {
         GlStateManager.scale(2.35F, 2.35F, 2.35F);
      }
   }

   protected ResourceLocation getEntityTexture(EntityGuardian entityGuardian) {
      return entityGuardian.isElder() ? GUARDIAN_ELDER_TEXTURE : GUARDIAN_TEXTURE;
   }
}
