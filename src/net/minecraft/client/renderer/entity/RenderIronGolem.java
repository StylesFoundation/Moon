package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerIronGolemFlower;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.ResourceLocation;

public class RenderIronGolem extends RenderLiving<EntityIronGolem> {
   private static final ResourceLocation ironGolemTextures = new ResourceLocation("textures/entity/iron_golem.png");

   public RenderIronGolem(RenderManager renderManager) {
      super(renderManager, new ModelIronGolem(), 0.5F);
      this.addLayer(new LayerIronGolemFlower(this));
   }

   protected ResourceLocation getEntityTexture(EntityIronGolem entityIronGolem) {
      return ironGolemTextures;
   }

   protected void rotateCorpse(EntityIronGolem entityIronGolem, float float2, float float3, float float4) {
      super.rotateCorpse(entityIronGolem, float2, float3, float4);
      if (entityIronGolem.limbSwingAmount >= 0.01) {
         float var7 = (Math.abs((entityIronGolem.limbSwing - entityIronGolem.limbSwingAmount * (1.0F - float4) + 6.0F) % 13.0F - 6.5F) - 3.25F) / 3.25F;
         GlStateManager.rotate(6.5F * var7, 0.0F, 0.0F, 1.0F);
      }
   }
}
