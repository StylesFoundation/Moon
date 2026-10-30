package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

public class RenderSheep extends RenderLiving<EntitySheep> {
   private static final ResourceLocation shearedSheepTextures = new ResourceLocation("textures/entity/sheep/sheep.png");

   public RenderSheep(RenderManager renderManager, ModelBase modelBase, float float3) {
      super(renderManager, modelBase, float3);
      this.addLayer(new LayerSheepWool(this));
   }

   protected ResourceLocation getEntityTexture(EntitySheep entitySheep) {
      return shearedSheepTextures;
   }
}
