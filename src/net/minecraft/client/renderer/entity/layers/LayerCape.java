package net.minecraft.client.renderer.entity.layers;

import com.moon.Moon;
import com.moon.events.impl.render.RenderEntityEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;

public class LayerCape implements LayerRenderer<AbstractClientPlayer> {
   private final RenderPlayer playerRenderer;

   public LayerCape(RenderPlayer renderPlayer) {
      this.playerRenderer = renderPlayer;
   }

   public void doRenderLayer(
      AbstractClientPlayer abstractClientPlayer, float float2, float float3, float float4, float float5, float float6, float float7, float float8
   ) {
      if (abstractClientPlayer.hasPlayerInfo()
         && !abstractClientPlayer.isInvisible()
         && abstractClientPlayer.isWearing(EnumPlayerModelParts.CAPE)
         && abstractClientPlayer.getLocationCape() != null) {
         RenderEntityEvent var9 = new RenderEntityEvent(abstractClientPlayer, false, false);
         Moon.getInstance().getEventBus().publish(var9);
         if (var9.isChild()) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * float8, 0.0F);
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.playerRenderer.bindTexture(abstractClientPlayer.getLocationCape());
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 0.0F, 0.125F);
         double var10 = abstractClientPlayer.prevChasingPosX
            + (abstractClientPlayer.chasingPosX - abstractClientPlayer.prevChasingPosX) * float4
            - (abstractClientPlayer.prevPosX + (abstractClientPlayer.posX - abstractClientPlayer.prevPosX) * float4);
         double var12 = abstractClientPlayer.prevChasingPosY
            + (abstractClientPlayer.chasingPosY - abstractClientPlayer.prevChasingPosY) * float4
            - (abstractClientPlayer.prevPosY + (abstractClientPlayer.posY - abstractClientPlayer.prevPosY) * float4);
         double var14 = abstractClientPlayer.prevChasingPosZ
            + (abstractClientPlayer.chasingPosZ - abstractClientPlayer.prevChasingPosZ) * float4
            - (abstractClientPlayer.prevPosZ + (abstractClientPlayer.posZ - abstractClientPlayer.prevPosZ) * float4);
         float var16 = abstractClientPlayer.prevRenderYawOffset + (abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset) * float4;
         double var17 = MathHelper.sin(var16 * (float) Math.PI / 180.0F);
         double var19 = -MathHelper.cos(var16 * (float) Math.PI / 180.0F);
         float var21 = MathHelper.clamp_float((float)var12 * 10.0F, -6.0F, 32.0F);
         float var22 = (float)(var10 * var17 + var14 * var19) * 100.0F;
         float var23 = (float)(var10 * var19 - var14 * var17) * 100.0F;
         if (var22 < 0.0F) {
            var22 = 0.0F;
         }

         if (var22 > 165.0F) {
            var22 = 165.0F;
         }

         if (var21 < -5.0F) {
            var21 = -5.0F;
         }

         float var24 = abstractClientPlayer.prevCameraYaw + (abstractClientPlayer.cameraYaw - abstractClientPlayer.prevCameraYaw) * float4;
         var21 += MathHelper.sin(
               (
                     abstractClientPlayer.prevDistanceWalkedModified
                        + (abstractClientPlayer.distanceWalkedModified - abstractClientPlayer.prevDistanceWalkedModified) * float4
                  )
                  * 6.0F
            )
            * 32.0F
            * var24;
         if (abstractClientPlayer.isSneaking()) {
            var21 += 25.0F;
            GlStateManager.translate(0.0F, 0.142F, -0.0178F);
         }

         GlStateManager.rotate(6.0F + var22 / 2.0F + var21, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(var23 / 2.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(-var23 / 2.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
         this.playerRenderer.getMainModel().renderCape(0.0625F);
         GlStateManager.popMatrix();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
