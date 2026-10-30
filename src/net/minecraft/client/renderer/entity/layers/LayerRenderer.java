package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.EntityLivingBase;

public interface LayerRenderer<E extends EntityLivingBase> {
   void doRenderLayer(E entityLivingBase, float float2, float float3, float float4, float float5, float float6, float float7, float float8);

   boolean shouldCombineTextures();
}
