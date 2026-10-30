package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.init.Blocks;

public class LayerIronGolemFlower implements LayerRenderer<EntityIronGolem> {
   private final RenderIronGolem ironGolemRenderer;

   public LayerIronGolemFlower(RenderIronGolem renderIronGolem) {
      this.ironGolemRenderer = renderIronGolem;
   }

   public void doRenderLayer(EntityIronGolem entityIronGolem, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      if (entityIronGolem.getHoldRoseTick() != 0) {
         BlockRendererDispatcher var11 = Minecraft.getMinecraft().getBlockRendererDispatcher();
         GlStateManager.enableRescaleNormal();
         GlStateManager.pushMatrix();
         GlStateManager.rotate(
            5.0F + 180.0F * ((ModelIronGolem)this.ironGolemRenderer.getMainModel()).ironGolemRightArm.rotateAngleX / (float) Math.PI, 1.0F, 0.0F, 0.0F
         );
         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.translate(-0.9375F, -0.625F, -0.9375F);
         GlStateManager.scale(0.5F, -0.5F, 0.5F);
         int var13 = entityIronGolem.getBrightnessForRender(float4);
         int var14 = var13 % 65536;
         int var15 = var13 / 65536;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var14, var15);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.ironGolemRenderer.bindTexture(TextureMap.locationBlocksTexture);
         var11.renderBlockBrightness(Blocks.red_flower.getDefaultState(), 1.0F);
         GlStateManager.popMatrix();
         GlStateManager.disableRescaleNormal();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
