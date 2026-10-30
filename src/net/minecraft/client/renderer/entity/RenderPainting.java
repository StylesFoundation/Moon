package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderPainting extends Render<EntityPainting> {
   private static final ResourceLocation KRISTOFFER_PAINTING_TEXTURE = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");

   public RenderPainting(RenderManager renderManager) {
      super(renderManager);
   }

   public void doRender(EntityPainting entityPainting, double double2, double double3, double double4, float float5, float float6) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(double2, double3, double4);
      GlStateManager.rotate(180.0F - float5, 0.0F, 1.0F, 0.0F);
      GlStateManager.enableRescaleNormal();
      this.bindEntityTexture(entityPainting);
      EntityPainting.EnumArt var10 = entityPainting.art;
      GlStateManager.scale(0.0625F, 0.0625F, 0.0625F);
      this.renderPainting(entityPainting, var10.sizeX, var10.sizeY, var10.offsetX, var10.offsetY);
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender(entityPainting, double2, double3, double4, float5, float6);
   }

   protected ResourceLocation getEntityTexture(EntityPainting entityPainting) {
      return KRISTOFFER_PAINTING_TEXTURE;
   }

   private void renderPainting(EntityPainting entityPainting, int integer2, int integer3, int integer4, int integer5) {
      float var8 = -integer2 / 2.0F;
      float var9 = -integer3 / 2.0F;

      for (int var23 = 0; var23 < integer2 / 16; var23++) {
         for (int var24 = 0; var24 < integer3 / 16; var24++) {
            float var25 = var8 + (var23 + 1) * 16;
            float var26 = var8 + var23 * 16;
            float var27 = var9 + (var24 + 1) * 16;
            float var28 = var9 + var24 * 16;
            this.setLightmap(entityPainting, (var25 + var26) / 2.0F, (var27 + var28) / 2.0F);
            float var29 = (integer4 + integer2 - var23 * 16) / 256.0F;
            float var30 = (integer4 + integer2 - (var23 + 1) * 16) / 256.0F;
            float var31 = (integer5 + integer3 - var24 * 16) / 256.0F;
            float var32 = (integer5 + integer3 - (var24 + 1) * 16) / 256.0F;
            Tessellator var33 = Tessellator.getInstance();
            WorldRenderer var34 = var33.getWorldRenderer();
            var34.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
            var34.pos(var25, var28, -0.5).tex(var30, var31).normal(0.0F, 0.0F, -1.0F).endVertex();
            var34.pos(var26, var28, -0.5).tex(var29, var31).normal(0.0F, 0.0F, -1.0F).endVertex();
            var34.pos(var26, var27, -0.5).tex(var29, var32).normal(0.0F, 0.0F, -1.0F).endVertex();
            var34.pos(var25, var27, -0.5).tex(var30, var32).normal(0.0F, 0.0F, -1.0F).endVertex();
            var34.pos(var25, var27, 0.5).tex(0.75, 0.0).normal(0.0F, 0.0F, 1.0F).endVertex();
            var34.pos(var26, var27, 0.5).tex(0.8125, 0.0).normal(0.0F, 0.0F, 1.0F).endVertex();
            var34.pos(var26, var28, 0.5).tex(0.8125, 0.0625).normal(0.0F, 0.0F, 1.0F).endVertex();
            var34.pos(var25, var28, 0.5).tex(0.75, 0.0625).normal(0.0F, 0.0F, 1.0F).endVertex();
            var34.pos(var25, var27, -0.5).tex(0.75, 0.001953125).normal(0.0F, 1.0F, 0.0F).endVertex();
            var34.pos(var26, var27, -0.5).tex(0.8125, 0.001953125).normal(0.0F, 1.0F, 0.0F).endVertex();
            var34.pos(var26, var27, 0.5).tex(0.8125, 0.001953125).normal(0.0F, 1.0F, 0.0F).endVertex();
            var34.pos(var25, var27, 0.5).tex(0.75, 0.001953125).normal(0.0F, 1.0F, 0.0F).endVertex();
            var34.pos(var25, var28, 0.5).tex(0.75, 0.001953125).normal(0.0F, -1.0F, 0.0F).endVertex();
            var34.pos(var26, var28, 0.5).tex(0.8125, 0.001953125).normal(0.0F, -1.0F, 0.0F).endVertex();
            var34.pos(var26, var28, -0.5).tex(0.8125, 0.001953125).normal(0.0F, -1.0F, 0.0F).endVertex();
            var34.pos(var25, var28, -0.5).tex(0.75, 0.001953125).normal(0.0F, -1.0F, 0.0F).endVertex();
            var34.pos(var25, var27, 0.5).tex(0.7519531F, 0.0).normal(-1.0F, 0.0F, 0.0F).endVertex();
            var34.pos(var25, var28, 0.5).tex(0.7519531F, 0.0625).normal(-1.0F, 0.0F, 0.0F).endVertex();
            var34.pos(var25, var28, -0.5).tex(0.7519531F, 0.0625).normal(-1.0F, 0.0F, 0.0F).endVertex();
            var34.pos(var25, var27, -0.5).tex(0.7519531F, 0.0).normal(-1.0F, 0.0F, 0.0F).endVertex();
            var34.pos(var26, var27, -0.5).tex(0.7519531F, 0.0).normal(1.0F, 0.0F, 0.0F).endVertex();
            var34.pos(var26, var28, -0.5).tex(0.7519531F, 0.0625).normal(1.0F, 0.0F, 0.0F).endVertex();
            var34.pos(var26, var28, 0.5).tex(0.7519531F, 0.0625).normal(1.0F, 0.0F, 0.0F).endVertex();
            var34.pos(var26, var27, 0.5).tex(0.7519531F, 0.0).normal(1.0F, 0.0F, 0.0F).endVertex();
            var33.draw();
         }
      }
   }

   private void setLightmap(EntityPainting entityPainting, float float2, float float3) {
      int var6 = MathHelper.floor_double(entityPainting.posX);
      int var7 = MathHelper.floor_double(entityPainting.posY + float3 / 16.0F);
      int var8 = MathHelper.floor_double(entityPainting.posZ);
      EnumFacing var9 = entityPainting.facingDirection;
      if (var9 == EnumFacing.NORTH) {
         var6 = MathHelper.floor_double(entityPainting.posX + float2 / 16.0F);
      }

      if (var9 == EnumFacing.WEST) {
         var8 = MathHelper.floor_double(entityPainting.posZ - float2 / 16.0F);
      }

      if (var9 == EnumFacing.SOUTH) {
         var6 = MathHelper.floor_double(entityPainting.posX - float2 / 16.0F);
      }

      if (var9 == EnumFacing.EAST) {
         var8 = MathHelper.floor_double(entityPainting.posZ + float2 / 16.0F);
      }

      int var10 = this.renderManager.worldObj.getCombinedLight(new BlockPos(var6, var7, var8), 0);
      int var11 = var10 % 65536;
      int var12 = var10 / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var11, var12);
      GlStateManager.color(1.0F, 1.0F, 1.0F);
   }
}
