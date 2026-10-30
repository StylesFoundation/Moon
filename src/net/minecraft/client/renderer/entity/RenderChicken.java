package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderChicken extends RenderLiving<EntityChicken> {
   private static final ResourceLocation chickenTextures = new ResourceLocation("textures/entity/chicken.png");

   public RenderChicken(RenderManager renderManager, ModelBase modelBase, float float3) {
      super(renderManager, modelBase, float3);
   }

   protected ResourceLocation getEntityTexture(EntityChicken entityChicken) {
      return chickenTextures;
   }

   protected float handleRotationFloat(EntityChicken entityChicken, float float2) {
      float var3 = entityChicken.field_70888_h + (entityChicken.wingRotation - entityChicken.field_70888_h) * float2;
      float var4 = entityChicken.field_70884_g + (entityChicken.destPos - entityChicken.field_70884_g) * float2;
      return (MathHelper.sin(var3) + 1.0F) * var4;
   }
}
