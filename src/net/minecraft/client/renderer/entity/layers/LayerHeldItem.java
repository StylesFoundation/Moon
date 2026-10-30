package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LayerHeldItem implements LayerRenderer<EntityLivingBase> {
   private final RendererLivingEntity<?> livingEntityRenderer;

   public LayerHeldItem(RendererLivingEntity<?> rendererLivingEntity) {
      this.livingEntityRenderer = rendererLivingEntity;
   }

   @Override
   public void doRenderLayer(
      EntityLivingBase entityLivingBase, float float2, float float3, float float4, float float5, float float6, float float7, float float8
   ) {
      ItemStack var9 = entityLivingBase.getHeldItem();
      if (var9 != null) {
         GlStateManager.pushMatrix();
         if (this.livingEntityRenderer.getMainModel().isChild) {
            GlStateManager.translate(0.0F, 0.625F, 0.0F);
            GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
         }

         ((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F);
         GlStateManager.translate(-0.0625F, 0.4375F, 0.0625F);
         if (entityLivingBase instanceof EntityPlayer && ((EntityPlayer)entityLivingBase).fishEntity != null) {
            var9 = new ItemStack(Items.fishing_rod, 0);
         }

         Item var10 = var9.getItem();
         Minecraft var11 = Minecraft.getMinecraft();
         if (var10 instanceof ItemBlock && Block.getBlockFromItem(var10).getRenderType() == 2) {
            GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
            GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(-0.375F, -0.375F, 0.375F);
         }

         if (entityLivingBase.isSneaking()) {
            GlStateManager.translate(0.0F, 0.203125F, 0.0F);
         }

         var11.getItemRenderer().Method807(entityLivingBase, var9, ItemCameraTransforms.TransformType.THIRD_PERSON);
         GlStateManager.popMatrix();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
