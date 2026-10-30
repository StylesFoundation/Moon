package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.monster.EntityEnderman;

public class LayerHeldBlock implements LayerRenderer<EntityEnderman> {
   private final RenderEnderman endermanRenderer;

   public LayerHeldBlock(RenderEnderman renderEnderman) {
      this.endermanRenderer = renderEnderman;
   }

   public void doRenderLayer(EntityEnderman entityEnderman, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      IBlockState var11 = entityEnderman.getHeldBlockState();
      if (var11.getBlock().getMaterial() != Material.air) {
         BlockRendererDispatcher var12 = Minecraft.getMinecraft().getBlockRendererDispatcher();
         GlStateManager.enableRescaleNormal();
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 0.6875F, -0.75F);
         GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.25F, 0.1875F, 0.25F);
         GlStateManager.scale(-0.5F, -0.5F, 0.5F);
         int var14 = entityEnderman.getBrightnessForRender(float4);
         int var15 = var14 % 65536;
         int var16 = var14 / 65536;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var15, var16);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.endermanRenderer.bindTexture(TextureMap.locationBlocksTexture);
         var12.renderBlockBrightness(var11, 1.0F);
         GlStateManager.popMatrix();
         GlStateManager.disableRescaleNormal();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
