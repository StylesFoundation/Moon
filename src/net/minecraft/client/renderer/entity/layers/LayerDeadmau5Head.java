package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;

public class LayerDeadmau5Head implements LayerRenderer<AbstractClientPlayer> {
   private final RenderPlayer playerRenderer;

   public LayerDeadmau5Head(RenderPlayer renderPlayer) {
      this.playerRenderer = renderPlayer;
   }

   public void doRenderLayer(
      AbstractClientPlayer abstractClientPlayer, float float2, float float3, float float4, float float5, float float6, float float7, float float8
   ) {
      if (abstractClientPlayer.getName().equals("deadmau5") && abstractClientPlayer.hasSkin() && !abstractClientPlayer.isInvisible()) {
         this.playerRenderer.bindTexture(abstractClientPlayer.getLocationSkin());

         for (int var9 = 0; var9 < 2; var9++) {
            float var10 = abstractClientPlayer.prevRotationYaw
               + (abstractClientPlayer.rotationYaw - abstractClientPlayer.prevRotationYaw) * float4
               - (abstractClientPlayer.prevRenderYawOffset + (abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset) * float4);
            float var11 = abstractClientPlayer.prevRotationPitch + (abstractClientPlayer.rotationPitch - abstractClientPlayer.prevRotationPitch) * float4;
            GlStateManager.pushMatrix();
            GlStateManager.rotate(var10, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(var11, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.375F * (var9 * 2 - 1), 0.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.375F, 0.0F);
            GlStateManager.rotate(-var11, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-var10, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(1.3333334F, 1.3333334F, 1.3333334F);
            this.playerRenderer.getMainModel().renderDeadmau5Head(0.0625F);
            GlStateManager.popMatrix();
         }
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return true;
   }
}
