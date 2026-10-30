package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

public class LayerCreeperCharge implements LayerRenderer<EntityCreeper> {
   private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
   private final RenderCreeper creeperRenderer;
   private final ModelCreeper creeperModel = new ModelCreeper(2.0F);

   public LayerCreeperCharge(RenderCreeper renderCreeper) {
      this.creeperRenderer = renderCreeper;
   }

   public void doRenderLayer(EntityCreeper entityCreeper, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      if (entityCreeper.getPowered()) {
         boolean var11 = entityCreeper.isInvisible();
         GlStateManager.depthMask(!var11);
         this.creeperRenderer.bindTexture(LIGHTNING_TEXTURE);
         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         float var12 = entityCreeper.ticksExisted + float4;
         GlStateManager.translate(var12 * 0.01F, var12 * 0.01F, 0.0F);
         GlStateManager.matrixMode(5888);
         GlStateManager.enableBlend();
         GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
         GlStateManager.disableLighting();
         GlStateManager.blendFunc(1, 1);
         this.creeperModel.setModelAttributes(this.creeperRenderer.getMainModel());
         this.creeperModel.render(entityCreeper, float2, float3, float5, float6, float7, float8);
         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         GlStateManager.matrixMode(5888);
         GlStateManager.enableLighting();
         GlStateManager.disableBlend();
         GlStateManager.depthMask(var11);
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
