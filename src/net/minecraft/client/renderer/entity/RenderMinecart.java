package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderMinecart<T extends EntityMinecart> extends Render<T> {
   private static final ResourceLocation minecartTextures = new ResourceLocation("textures/entity/minecart.png");
   public ModelBase i = new ModelMinecart();

   public RenderMinecart(RenderManager renderManager) {
      super(renderManager);
      this.shadowSize = 0.5F;
   }

   public void doRender(T entityMinecart, double double2, double double3, double double4, float float5, float float6) {
      GlStateManager.pushMatrix();
      this.bindEntityTexture((T)entityMinecart);
      long var12 = entityMinecart.getEntityId() * 493286711L;
      var12 = var12 * var12 * 4392167121L + var12 * 98761L;
      float var14 = (((float)(var12 >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float var15 = (((float)(var12 >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float var16 = (((float)(var12 >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      GlStateManager.translate(var14, var15, var16);
      double var17 = entityMinecart.lastTickPosX + (entityMinecart.posX - entityMinecart.lastTickPosX) * float6;
      double var19 = entityMinecart.lastTickPosY + (entityMinecart.posY - entityMinecart.lastTickPosY) * float6;
      double var21 = entityMinecart.lastTickPosZ + (entityMinecart.posZ - entityMinecart.lastTickPosZ) * float6;
      Vec3 var25 = entityMinecart.func_70489_a(var17, var19, var21);
      float var26 = entityMinecart.prevRotationPitch + (entityMinecart.rotationPitch - entityMinecart.prevRotationPitch) * float6;
      if (var25 != null) {
         Vec3 var27 = entityMinecart.func_70495_a(var17, var19, var21, 0.3F);
         Vec3 var28 = entityMinecart.func_70495_a(var17, var19, var21, -0.3F);
         if (var27 == null) {
            var27 = var25;
         }

         if (var28 == null) {
            var28 = var25;
         }

         double2 += var25.xCoord - var17;
         double3 += (var27.yCoord + var28.yCoord) / 2.0 - var19;
         double4 += var25.zCoord - var21;
         Vec3 var29 = var28.addVector(-var27.xCoord, -var27.yCoord, -var27.zCoord);
         if (var29.lengthVector() != 0.0) {
            var29 = var29.normalize();
            float5 = (float)(Math.atan2(var29.zCoord, var29.xCoord) * 180.0 / Math.PI);
            var26 = (float)(Math.atan(var29.yCoord) * 73.0);
         }
      }

      GlStateManager.translate((float)double2, (float)double3 + 0.375F, (float)double4);
      GlStateManager.rotate(180.0F - float5, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-var26, 0.0F, 0.0F, 1.0F);
      float var32 = entityMinecart.getRollingAmplitude() - float6;
      float var33 = entityMinecart.getDamage() - float6;
      if (var33 < 0.0F) {
         var33 = 0.0F;
      }

      if (var32 > 0.0F) {
         GlStateManager.rotate(MathHelper.sin(var32) * var32 * var33 / 10.0F * entityMinecart.getRollingDirection(), 1.0F, 0.0F, 0.0F);
      }

      int var35 = entityMinecart.getDisplayTileOffset();
      IBlockState var30 = entityMinecart.getDisplayTile();
      if (var30.getBlock().getRenderType() != -1) {
         GlStateManager.pushMatrix();
         this.bindTexture(TextureMap.locationBlocksTexture);
         GlStateManager.scale(0.75F, 0.75F, 0.75F);
         GlStateManager.translate(-0.5F, (var35 - 8) / 16.0F, 0.5F);
         this.func_180560_a((T)entityMinecart, float6, var30);
         GlStateManager.popMatrix();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.bindEntityTexture((T)entityMinecart);
      }

      GlStateManager.scale(-1.0F, -1.0F, 1.0F);
      this.i.render(entityMinecart, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.popMatrix();
      super.doRender((T)entityMinecart, double2, double3, double4, float5, float6);
   }

   protected ResourceLocation getEntityTexture(T entityMinecart) {
      return minecartTextures;
   }

   protected void func_180560_a(T entityMinecart, float float2, IBlockState iBlockState) {
      GlStateManager.pushMatrix();
      Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(iBlockState, entityMinecart.getBrightness(float2));
      GlStateManager.popMatrix();
   }
}
