package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerSaddle;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class RenderPig extends RenderLiving<EntityPig> {
   private static final ResourceLocation pigTextures = new ResourceLocation("textures/entity/pig/pig.png");

   public RenderPig(RenderManager renderManager, ModelBase modelBase, float float3) {
      super(renderManager, modelBase, float3);
      this.addLayer(new LayerSaddle(this));
   }

   protected ResourceLocation getEntityTexture(EntityPig entityPig) {
      return pigTextures;
   }
}
