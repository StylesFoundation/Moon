package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderEntityItem extends Render<EntityItem> {
   private final RenderItem itemRenderer;
   private final Random field_177079_e = new Random();

   public RenderEntityItem(RenderManager renderManager, RenderItem renderItem) {
      super(renderManager);
      this.itemRenderer = renderItem;
      this.shadowSize = 0.15F;
      this.shadowOpaque = 0.75F;
   }

   private int func_177077_a(EntityItem entityItem, double double2, double double3, double double4, float float5, IBakedModel iBakedModel) {
      ItemStack var10 = entityItem.Method464();
      Item var11 = var10.getItem();
      if (var11 == null) {
         return 0;
      }

      boolean var12 = iBakedModel.isGui3d();
      int var13 = this.func_177078_a(var10);
      float var15 = MathHelper.sin((entityItem.getAge() + float5) / 10.0F + entityItem.hoverStart) * 0.1F + 0.1F;
      float var16 = iBakedModel.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
      GlStateManager.translate((float)double2, (float)double3 + var15 + 0.25F * var16, (float)double4);
      if (var12 || this.renderManager.options != null) {
         GlStateManager.rotate(((entityItem.getAge() + float5) / 20.0F + entityItem.hoverStart) * (180.0F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
      }

      if (!var12) {
         float var17 = -0.0F * (var13 - 1) * 0.5F;
         float var18 = -0.0F * (var13 - 1) * 0.5F;
         float var19 = -0.046875F * (var13 - 1) * 0.5F;
         GlStateManager.translate(var17, var18, var19);
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      return var13;
   }

   private int func_177078_a(ItemStack itemStack) {
      return 1;
   }

   public void doRender(EntityItem entityItem, double double2, double double3, double double4, float float5, float float6) {
      if (!entityItem.aC) {
         ItemStack var12 = entityItem.Method464();
         this.field_177079_e.setSeed(187L);
         boolean var13 = false;
         if (this.bindEntityTexture(entityItem)) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entityItem)).setBlurMipmap(false, false);
            var13 = true;
         }

         GlStateManager.enableRescaleNormal();
         boolean var14 = Minecraft.getMinecraft().getRenderManager().Method591();
         GlStateManager.alphaFunc(516, var14 ? 0.99F : 0.1F);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.pushMatrix();
         IBakedModel var15 = this.itemRenderer.getItemModelMesher().getItemModel(var12);
         int var16 = this.func_177077_a(entityItem, double2, double3, double4, float6, var15);

         for (int var17 = 0; var17 < var16; var17++) {
            if (var15.isGui3d()) {
               GlStateManager.pushMatrix();
               if (var17 > 0) {
                  float var18 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                  float var19 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                  float var20 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                  GlStateManager.translate(var18, var19, var20);
               }

               GlStateManager.scale(0.5F, 0.5F, 0.5F);
               var15.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
               this.itemRenderer.renderItem(var12, var15);
               GlStateManager.popMatrix();
            } else {
               GlStateManager.pushMatrix();
               var15.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
               this.itemRenderer.renderItem(var12, var15);
               GlStateManager.popMatrix();
               float var21 = var15.getItemCameraTransforms().ground.scale.x;
               float var22 = var15.getItemCameraTransforms().ground.scale.y;
               float var23 = var15.getItemCameraTransforms().ground.scale.z;
               GlStateManager.translate(0.0F * var21, 0.0F * var22, 0.046875F * var23);
            }
         }

         GlStateManager.popMatrix();
         GlStateManager.disableRescaleNormal();
         GlStateManager.disableBlend();
         this.bindEntityTexture(entityItem);
         if (var13) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entityItem)).restoreLastBlurMipmap();
         }

         super.doRender(entityItem, double2, double3, double4, float5, float6);
      }
   }

   protected ResourceLocation getEntityTexture(EntityItem entityItem) {
      return TextureMap.locationBlocksTexture;
   }
}
