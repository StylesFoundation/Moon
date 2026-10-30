package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.ResourceLocation;

public class RenderSquid extends RenderLiving<EntitySquid> {
   private static final ResourceLocation squidTextures = new ResourceLocation("textures/entity/squid.png");

   public RenderSquid(RenderManager renderManager, ModelBase modelBase, float float3) {
      super(renderManager, modelBase, float3);
   }

   protected ResourceLocation getEntityTexture(EntitySquid entitySquid) {
      return squidTextures;
   }

   protected void rotateCorpse(EntitySquid entitySquid, float float2, float float3, float float4) {
      float var5 = entitySquid.prevSquidPitch + (entitySquid.squidPitch - entitySquid.prevSquidPitch) * float4;
      float var6 = entitySquid.prevSquidYaw + (entitySquid.squidYaw - entitySquid.prevSquidYaw) * float4;
      GlStateManager.translate(0.0F, 0.5F, 0.0F);
      GlStateManager.rotate(180.0F - float3, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var5, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(var6, 0.0F, 1.0F, 0.0F);
      GlStateManager.translate(0.0F, -1.2F, 0.0F);
   }

   protected float handleRotationFloat(EntitySquid entitySquid, float float2) {
      return entitySquid.lastTentacleAngle + (entitySquid.tentacleAngle - entitySquid.lastTentacleAngle) * float2;
   }
}
