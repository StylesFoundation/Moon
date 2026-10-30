package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelPlayer extends ModelBiped {
   public ModelRenderer bipedLeftArmwear;
   public ModelRenderer bipedRightArmwear;
   public ModelRenderer bipedLeftLegwear;
   public ModelRenderer bipedRightLegwear;
   public ModelRenderer bipedBodyWear;
   private final ModelRenderer bipedCape;
   private final ModelRenderer bipedDeadmau5Head;
   private final boolean smallArms;

   public ModelPlayer(float float1, boolean boolean2) {
      super(float1, 0.0F, 64, 64);
      this.smallArms = boolean2;
      this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
      this.bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, float1);
      this.bipedCape = new ModelRenderer(this, 0, 0);
      this.bipedCape.setTextureSize(64, 32);
      this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, float1);
      if (boolean2) {
         this.bipedLeftArm = new ModelRenderer(this, 32, 48);
         this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, float1);
         this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
         this.bipedRightArm = new ModelRenderer(this, 40, 16);
         this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, float1);
         this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
         this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
         this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, float1 + 0.25F);
         this.bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
         this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
         this.bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, float1 + 0.25F);
         this.bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
      } else {
         this.bipedLeftArm = new ModelRenderer(this, 32, 48);
         this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, float1);
         this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
         this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, float1 + 0.25F);
         this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
         this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, float1 + 0.25F);
         this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
      }

      this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
      this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, float1);
      this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
      this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, float1 + 0.25F);
      this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
      this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, float1 + 0.25F);
      this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.bipedBodyWear = new ModelRenderer(this, 16, 32);
      this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, float1 + 0.25F);
      this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      super.render(entity, float2, float3, float4, float5, float6, float7);
      GlStateManager.pushMatrix();
      if (this.isChild) {
         GlStateManager.scale(0.5F, 0.5F, 0.5F);
         GlStateManager.translate(0.0F, 24.0F * float7, 0.0F);
      } else if (entity.isSneaking()) {
         GlStateManager.translate(0.0F, 0.2F, 0.0F);
      }

      this.bipedLeftLegwear.render(float7);
      this.bipedRightLegwear.render(float7);
      this.bipedLeftArmwear.render(float7);
      this.bipedRightArmwear.render(float7);
      this.bipedBodyWear.render(float7);
      GlStateManager.popMatrix();
   }

   public void renderDeadmau5Head(float float1) {
      copyModelAngles(this.bipedHead, this.bipedDeadmau5Head);
      this.bipedDeadmau5Head.rotationPointX = 0.0F;
      this.bipedDeadmau5Head.rotationPointY = 0.0F;
      this.bipedDeadmau5Head.render(float1);
   }

   public void renderCape(float float1) {
      this.bipedCape.render(float1);
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      super.setRotationAngles(float1, float2, float3, float4, float5, float6, entity);
      copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
      copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
      copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
      copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
      copyModelAngles(this.bipedBody, this.bipedBodyWear);
   }

   public void renderRightArm() {
      this.bipedRightArm.render(0.0625F);
      this.bipedRightArmwear.render(0.0625F);
   }

   public void renderLeftArm() {
      this.bipedLeftArm.render(0.0625F);
      this.bipedLeftArmwear.render(0.0625F);
   }

   @Override
   public void setInvisible(boolean boolean1) {
      super.setInvisible(boolean1);
      this.bipedLeftArmwear.showModel = boolean1;
      this.bipedRightArmwear.showModel = boolean1;
      this.bipedLeftLegwear.showModel = boolean1;
      this.bipedRightLegwear.showModel = boolean1;
      this.bipedBodyWear.showModel = boolean1;
      this.bipedCape.showModel = boolean1;
      this.bipedDeadmau5Head.showModel = boolean1;
   }

   @Override
   public void postRenderArm(float float1) {
      if (this.smallArms) {
         this.bipedRightArm.rotationPointX++;
         this.bipedRightArm.postRender(float1);
         this.bipedRightArm.rotationPointX--;
      } else {
         this.bipedRightArm.postRender(float1);
      }
   }
}
