package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWither;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class LayerWitherAura implements LayerRenderer<EntityWither> {
   private static final ResourceLocation WITHER_ARMOR = new ResourceLocation("textures/entity/wither/wither_armor.png");
   private final RenderWither witherRenderer;
   private final ModelWither witherModel = new ModelWither(0.5F);

   public LayerWitherAura(RenderWither renderWither) {
      this.witherRenderer = renderWither;
   }

   public void doRenderLayer(EntityWither entityWither, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      if (entityWither.isArmored()) {
         GlStateManager.depthMask(!entityWither.isInvisible());
         this.witherRenderer.bindTexture(WITHER_ARMOR);
         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         float var11 = entityWither.ticksExisted + float4;
         float var12 = MathHelper.cos(var11 * 0.02F) * 3.0F;
         float var13 = var11 * 0.01F;
         GlStateManager.translate(var12, var13, 0.0F);
         GlStateManager.matrixMode(5888);
         GlStateManager.enableBlend();
         GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
         GlStateManager.disableLighting();
         GlStateManager.blendFunc(1, 1);
         this.witherModel.setLivingAnimations(entityWither, float2, float3, float4);
         this.witherModel.setModelAttributes(this.witherRenderer.getMainModel());
         this.witherModel.render(entityWither, float2, float3, float5, float6, float7, float8);
         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         GlStateManager.matrixMode(5888);
         GlStateManager.enableLighting();
         GlStateManager.disableBlend();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
