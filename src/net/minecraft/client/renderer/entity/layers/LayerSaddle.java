package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class LayerSaddle implements LayerRenderer<EntityPig> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
   private final RenderPig pigRenderer;
   private final ModelPig pigModel = new ModelPig(0.5F);

   public LayerSaddle(RenderPig renderPig) {
      this.pigRenderer = renderPig;
   }

   public void doRenderLayer(EntityPig entityPig, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      if (entityPig.getSaddled()) {
         this.pigRenderer.bindTexture(TEXTURE);
         this.pigModel.setModelAttributes(this.pigRenderer.getMainModel());
         this.pigModel.render(entityPig, float2, float3, float5, float6, float7, float8);
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }
}
