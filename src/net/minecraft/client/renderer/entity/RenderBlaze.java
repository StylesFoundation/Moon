package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBlaze;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.ResourceLocation;

public class RenderBlaze extends RenderLiving<EntityBlaze> {
   private static final ResourceLocation blazeTextures = new ResourceLocation("textures/entity/blaze.png");

   public RenderBlaze(RenderManager renderManager) {
      super(renderManager, new ModelBlaze(), 0.5F);
   }

   protected ResourceLocation getEntityTexture(EntityBlaze entityBlaze) {
      return blazeTextures;
   }
}
