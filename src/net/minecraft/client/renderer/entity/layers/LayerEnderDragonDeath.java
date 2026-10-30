package net.minecraft.client.renderer.entity.layers;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.EntityDragon;

public class LayerEnderDragonDeath implements LayerRenderer<EntityDragon> {
   public void doRenderLayer(EntityDragon entityDragon, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      if (!Minecraft.getMinecraft().getRenderManager().Method591()) {
         if (entityDragon.deathTicks > 0) {
            Tessellator var11 = Tessellator.getInstance();
            WorldRenderer var12 = var11.getWorldRenderer();
            RenderHelper.disableStandardItemLighting();
            float var13 = (entityDragon.deathTicks + float4) / 200.0F;
            float var14 = 0.0F;
            if (var13 > 0.8F) {
               var14 = (var13 - 0.8F) / 0.2F;
            }

            Random var15 = new Random(432L);
            GlStateManager.disableTexture2D();
            GlStateManager.shadeModel(7425);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 1);
            GlStateManager.disableAlpha();
            GlStateManager.enableCull();
            GlStateManager.depthMask(false);
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, -1.0F, -2.0F);

            for (int var16 = 0; var16 < (var13 + var13 * var13) / 2.0F * 60.0F; var16++) {
               GlStateManager.rotate(var15.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(var15.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(var15.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.rotate(var15.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(var15.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(var15.nextFloat() * 360.0F + var13 * 90.0F, 0.0F, 0.0F, 1.0F);
               float var17 = var15.nextFloat() * 20.0F + 5.0F + var14 * 10.0F;
               float var18 = var15.nextFloat() * 2.0F + 1.0F + var14 * 2.0F;
               var12.begin(6, DefaultVertexFormats.POSITION_COLOR);
               var12.pos(0.0, 0.0, 0.0).color(255, 255, 255, (int)(255.0F * (1.0F - var14))).endVertex();
               var12.pos(-0.866 * var18, var17, -0.5F * var18).color(255, 0, 255, 0).endVertex();
               var12.pos(0.866 * var18, var17, -0.5F * var18).color(255, 0, 255, 0).endVertex();
               var12.pos(0.0, var17, var18).color(255, 0, 255, 0).endVertex();
               var12.pos(-0.866 * var18, var17, -0.5F * var18).color(255, 0, 255, 0).endVertex();
               var11.draw();
            }

            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.disableCull();
            GlStateManager.disableBlend();
            GlStateManager.shadeModel(7424);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
            RenderHelper.enableStandardItemLighting();
         }
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
