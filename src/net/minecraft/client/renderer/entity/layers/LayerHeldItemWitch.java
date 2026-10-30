package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LayerHeldItemWitch implements LayerRenderer<EntityWitch> {
   private final RenderWitch witchRenderer;

   public LayerHeldItemWitch(RenderWitch renderWitch) {
      this.witchRenderer = renderWitch;
   }

   public void doRenderLayer(EntityWitch entityWitch, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      ItemStack var9 = entityWitch.getHeldItem();
      if (var9 != null) {
         GlStateManager.color(1.0F, 1.0F, 1.0F);
         GlStateManager.pushMatrix();
         if (this.witchRenderer.getMainModel().isChild) {
            GlStateManager.translate(0.0F, 0.625F, 0.0F);
            GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
         }

         ((ModelWitch)this.witchRenderer.getMainModel()).villagerNose.postRender(0.0625F);
         GlStateManager.translate(-0.0625F, 0.53125F, 0.21875F);
         Item var10 = var9.getItem();
         Minecraft var11 = Minecraft.getMinecraft();
         if (var10 instanceof ItemBlock && var11.getBlockRendererDispatcher().isRenderTypeChest(Block.getBlockFromItem(var10), var9.getMetadata())) {
            GlStateManager.translate(0.0F, 0.0625F, -0.25F);
            GlStateManager.rotate(30.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-5.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(0.375F, -0.375F, 0.375F);
         } else if (var10 == Items.bow) {
            GlStateManager.translate(0.0F, 0.125F, -0.125F);
            GlStateManager.rotate(-45.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(0.625F, -0.625F, 0.625F);
            GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-20.0F, 0.0F, 1.0F, 0.0F);
         } else if (var10.isFull3D()) {
            if (var10.shouldRotateAroundWhenRendering()) {
               GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.translate(0.0F, -0.0625F, 0.0F);
            }

            this.witchRenderer.transformHeldFull3DItemLayer();
            GlStateManager.translate(0.0625F, -0.125F, 0.0F);
            GlStateManager.scale(0.625F, -0.625F, 0.625F);
            GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
         } else {
            GlStateManager.translate(0.1875F, 0.1875F, 0.0F);
            GlStateManager.scale(0.875F, 0.875F, 0.875F);
            GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-60.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-30.0F, 0.0F, 0.0F, 1.0F);
         }

         GlStateManager.rotate(-15.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(40.0F, 0.0F, 0.0F, 1.0F);
         var11.getItemRenderer().Method807(entityWitch, var9, ItemCameraTransforms.TransformType.THIRD_PERSON);
         GlStateManager.popMatrix();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
