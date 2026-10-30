package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.entity.monster.EntitySlime;

public class LayerSlimeGel implements LayerRenderer<EntitySlime> {
   private final RenderSlime slimeRenderer;
   private final ModelBase slimeModel = new ModelSlime(0);

   public LayerSlimeGel(RenderSlime renderSlime) {
      this.slimeRenderer = renderSlime;
   }

   public void doRenderLayer(EntitySlime entitySlime, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      if (!entitySlime.isInvisible()) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableNormalize();
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
         this.slimeModel.render(entitySlime, float2, float3, float5, float6, float7, float8);
         GlStateManager.disableBlend();
         GlStateManager.disableNormalize();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return true;
   }
}
