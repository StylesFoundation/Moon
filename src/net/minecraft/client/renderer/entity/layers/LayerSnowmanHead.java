package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class LayerSnowmanHead implements LayerRenderer<EntitySnowman> {
   private final RenderSnowMan snowManRenderer;

   public LayerSnowmanHead(RenderSnowMan renderSnowMan) {
      this.snowManRenderer = renderSnowMan;
   }

   public void doRenderLayer(EntitySnowman entitySnowman, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      if (!entitySnowman.isInvisible()) {
         GlStateManager.pushMatrix();
         this.snowManRenderer.getMainModel().head.postRender(0.0625F);
         GlStateManager.translate(0.0F, -0.34375F, 0.0F);
         GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.scale(0.625F, -0.625F, -0.625F);
         Minecraft.getMinecraft().getItemRenderer().Method807(entitySnowman, new ItemStack(Blocks.pumpkin, 1), ItemCameraTransforms.TransformType.HEAD);
         GlStateManager.popMatrix();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return true;
   }
}
