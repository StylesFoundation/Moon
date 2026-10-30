package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.MathHelper;

public class ModelWolf extends ModelBase {
   public ModelRenderer wolfHeadMain = new ModelRenderer(this, 0, 0);
   public ModelRenderer wolfBody;
   public ModelRenderer wolfLeg1;
   public ModelRenderer wolfLeg2;
   public ModelRenderer wolfLeg3;
   public ModelRenderer wolfLeg4;
   public ModelRenderer wolfTail;
   public ModelRenderer wolfMane;

   public ModelWolf() {
      this.wolfHeadMain.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 4, 0.0F);
      this.wolfHeadMain.setRotationPoint(-1.0F, 13.5F, -7.0F);
      this.wolfBody = new ModelRenderer(this, 18, 14);
      this.wolfBody.addBox(-4.0F, -2.0F, -3.0F, 6, 9, 6, 0.0F);
      this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
      this.wolfMane = new ModelRenderer(this, 21, 0);
      this.wolfMane.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 7, 0.0F);
      this.wolfMane.setRotationPoint(-1.0F, 14.0F, 2.0F);
      this.wolfLeg1 = new ModelRenderer(this, 0, 18);
      this.wolfLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
      this.wolfLeg2 = new ModelRenderer(this, 0, 18);
      this.wolfLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
      this.wolfLeg3 = new ModelRenderer(this, 0, 18);
      this.wolfLeg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
      this.wolfLeg4 = new ModelRenderer(this, 0, 18);
      this.wolfLeg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
      this.wolfTail = new ModelRenderer(this, 9, 18);
      this.wolfTail.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
      this.wolfHeadMain.setTextureOffset(16, 14).addBox(-3.0F, -5.0F, 0.0F, 2, 2, 1, 0.0F);
      this.wolfHeadMain.setTextureOffset(16, 14).addBox(1.0F, -5.0F, 0.0F, 2, 2, 1, 0.0F);
      this.wolfHeadMain.setTextureOffset(0, 10).addBox(-1.5F, 0.0F, -5.0F, 3, 3, 4, 0.0F);
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      super.render(entity, float2, float3, float4, float5, float6, float7);
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      if (this.isChild) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 5.0F * float7, 2.0F * float7);
         this.wolfHeadMain.renderWithRotation(float7);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.scale(0.5F, 0.5F, 0.5F);
         GlStateManager.translate(0.0F, 24.0F * float7, 0.0F);
         this.wolfBody.render(float7);
         this.wolfLeg1.render(float7);
         this.wolfLeg2.render(float7);
         this.wolfLeg3.render(float7);
         this.wolfLeg4.render(float7);
         this.wolfTail.renderWithRotation(float7);
         this.wolfMane.render(float7);
         GlStateManager.popMatrix();
      } else {
         this.wolfHeadMain.renderWithRotation(float7);
         this.wolfBody.render(float7);
         this.wolfLeg1.render(float7);
         this.wolfLeg2.render(float7);
         this.wolfLeg3.render(float7);
         this.wolfLeg4.render(float7);
         this.wolfTail.renderWithRotation(float7);
         this.wolfMane.render(float7);
      }
   }

   @Override
   public void setLivingAnimations(EntityLivingBase entityLivingBase, float float2, float float3, float float4) {
      EntityWolf var8 = (EntityWolf)entityLivingBase;
      if (var8.isAngry()) {
         this.wolfTail.rotateAngleY = 0.0F;
      } else {
         this.wolfTail.rotateAngleY = MathHelper.cos(float2 * 0.6662F) * 1.4F * float3;
      }

      if (var8.isSitting()) {
         this.wolfMane.setRotationPoint(-1.0F, 16.0F, -3.0F);
         this.wolfMane.rotateAngleX = (float) (Math.PI * 2.0 / 5.0);
         this.wolfMane.rotateAngleY = 0.0F;
         this.wolfBody.setRotationPoint(0.0F, 18.0F, 0.0F);
         this.wolfBody.rotateAngleX = (float) (Math.PI / 4);
         this.wolfTail.setRotationPoint(-1.0F, 21.0F, 6.0F);
         this.wolfLeg1.setRotationPoint(-2.5F, 22.0F, 2.0F);
         this.wolfLeg1.rotateAngleX = (float) (Math.PI * 3.0 / 2.0);
         this.wolfLeg2.setRotationPoint(0.5F, 22.0F, 2.0F);
         this.wolfLeg2.rotateAngleX = (float) (Math.PI * 3.0 / 2.0);
         this.wolfLeg3.rotateAngleX = 5.811947F;
         this.wolfLeg3.setRotationPoint(-2.49F, 17.0F, -4.0F);
         this.wolfLeg4.rotateAngleX = 5.811947F;
         this.wolfLeg4.setRotationPoint(0.51F, 17.0F, -4.0F);
      } else {
         this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
         this.wolfBody.rotateAngleX = (float) (Math.PI / 2);
         this.wolfMane.setRotationPoint(-1.0F, 14.0F, -3.0F);
         this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
         this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
         this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
         this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
         this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
         this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
         this.wolfLeg1.rotateAngleX = MathHelper.cos(float2 * 0.6662F) * 1.4F * float3;
         this.wolfLeg2.rotateAngleX = MathHelper.cos(float2 * 0.6662F + (float) Math.PI) * 1.4F * float3;
         this.wolfLeg3.rotateAngleX = MathHelper.cos(float2 * 0.6662F + (float) Math.PI) * 1.4F * float3;
         this.wolfLeg4.rotateAngleX = MathHelper.cos(float2 * 0.6662F) * 1.4F * float3;
      }

      this.wolfHeadMain.rotateAngleZ = var8.getInterestedAngle(float4) + var8.getShakeAngle(float4, 0.0F);
      this.wolfMane.rotateAngleZ = var8.getShakeAngle(float4, -0.08F);
      this.wolfBody.rotateAngleZ = var8.getShakeAngle(float4, -0.16F);
      this.wolfTail.rotateAngleZ = var8.getShakeAngle(float4, -0.2F);
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      super.setRotationAngles(float1, float2, float3, float4, float5, float6, entity);
      this.wolfHeadMain.rotateAngleX = float5 / (180.0F / (float)Math.PI);
      this.wolfHeadMain.rotateAngleY = float4 / (180.0F / (float)Math.PI);
      this.wolfTail.rotateAngleX = float3;
   }
}
