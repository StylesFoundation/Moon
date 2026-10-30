package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;

public class RenderCow extends RenderLiving<EntityCow> {
   private static final ResourceLocation cowTextures = new ResourceLocation("textures/entity/cow/cow.png");

   public RenderCow(RenderManager renderManager, ModelBase modelBase, float float3) {
      super(renderManager, modelBase, float3);
   }

   protected ResourceLocation getEntityTexture(EntityCow entityCow) {
      return cowTextures;
   }
}
