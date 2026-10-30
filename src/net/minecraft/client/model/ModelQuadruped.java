package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelQuadruped extends ModelBase {
   public ModelRenderer head = new ModelRenderer(this, 0, 0);
   public ModelRenderer body;
   public ModelRenderer leg1;
   public ModelRenderer leg2;
   public ModelRenderer leg3;
   public ModelRenderer leg4;
   protected float childYOffset = 8.0F;
   protected float childZOffset = 4.0F;

   public ModelQuadruped(int integer, float float2) {
      this.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, float2);
      this.head.setRotationPoint(0.0F, 18 - integer, -6.0F);
      this.body = new ModelRenderer(this, 28, 8);
      this.body.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, float2);
      this.body.setRotationPoint(0.0F, 17 - integer, 2.0F);
      this.leg1 = new ModelRenderer(this, 0, 16);
      this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, integer, 4, float2);
      this.leg1.setRotationPoint(-3.0F, 24 - integer, 7.0F);
      this.leg2 = new ModelRenderer(this, 0, 16);
      this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, integer, 4, float2);
      this.leg2.setRotationPoint(3.0F, 24 - integer, 7.0F);
      this.leg3 = new ModelRenderer(this, 0, 16);
      this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, integer, 4, float2);
      this.leg3.setRotationPoint(-3.0F, 24 - integer, -5.0F);
      this.leg4 = new ModelRenderer(this, 0, 16);
      this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, integer, 4, float2);
      this.leg4.setRotationPoint(3.0F, 24 - integer, -5.0F);
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      if (this.isChild) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, this.childYOffset * float7, this.childZOffset * float7);
         this.head.render(float7);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.scale(0.5F, 0.5F, 0.5F);
         GlStateManager.translate(0.0F, 24.0F * float7, 0.0F);
         this.body.render(float7);
         this.leg1.render(float7);
         this.leg2.render(float7);
         this.leg3.render(float7);
         this.leg4.render(float7);
         GlStateManager.popMatrix();
      } else {
         this.head.render(float7);
         this.body.render(float7);
         this.leg1.render(float7);
         this.leg2.render(float7);
         this.leg3.render(float7);
         this.leg4.render(float7);
      }
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      this.head.rotateAngleX = float5 / (180.0F / (float)Math.PI);
      this.head.rotateAngleY = float4 / (180.0F / (float)Math.PI);
      this.body.rotateAngleX = (float) (Math.PI / 2);
      this.leg1.rotateAngleX = MathHelper.cos(float1 * 0.6662F) * 1.4F * float2;
      this.leg2.rotateAngleX = MathHelper.cos(float1 * 0.6662F + (float) Math.PI) * 1.4F * float2;
      this.leg3.rotateAngleX = MathHelper.cos(float1 * 0.6662F + (float) Math.PI) * 1.4F * float2;
      this.leg4.rotateAngleX = MathHelper.cos(float1 * 0.6662F) * 1.4F * float2;
   }
}
