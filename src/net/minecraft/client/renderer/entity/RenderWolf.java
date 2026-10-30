package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerWolfCollar;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

public class RenderWolf extends RenderLiving<EntityWolf> {
   private static final ResourceLocation wolfTextures = new ResourceLocation("textures/entity/wolf/wolf.png");
   private static final ResourceLocation tamedWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
   private static final ResourceLocation anrgyWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

   public RenderWolf(RenderManager renderManager, ModelBase modelBase, float float3) {
      super(renderManager, modelBase, float3);
      this.addLayer(new LayerWolfCollar(this));
   }

   protected float handleRotationFloat(EntityWolf entityWolf, float float2) {
      return entityWolf.getTailRotation();
   }

   public void doRender(EntityWolf entityWolf, double double2, double double3, double double4, float float5, float float6) {
      if (entityWolf.isWolfWet()) {
         float var10 = entityWolf.getBrightness(float6) * entityWolf.getShadingWhileWet(float6);
         GlStateManager.color(var10, var10, var10);
      }

      super.doRender(entityWolf, double2, double3, double4, float5, float6);
   }

   protected ResourceLocation getEntityTexture(EntityWolf entityWolf) {
      return entityWolf.isTamed() ? tamedWolfTextures : (entityWolf.isAngry() ? anrgyWolfTextures : wolfTextures);
   }
}
