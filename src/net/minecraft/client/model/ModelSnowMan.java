package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSnowMan extends ModelBase {
   public ModelRenderer body;
   public ModelRenderer bottomBody;
   public ModelRenderer head = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
   public ModelRenderer rightHand;
   public ModelRenderer leftHand;

   public ModelSnowMan() {
      this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, -0.5F);
      this.head.setRotationPoint(0.0F, 4.0F, 0.0F);
      this.rightHand = new ModelRenderer(this, 32, 0).setTextureSize(64, 64);
      this.rightHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, -0.5F);
      this.rightHand.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.leftHand = new ModelRenderer(this, 32, 0).setTextureSize(64, 64);
      this.leftHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, -0.5F);
      this.leftHand.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.body = new ModelRenderer(this, 0, 16).setTextureSize(64, 64);
      this.body.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, -0.5F);
      this.body.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.bottomBody = new ModelRenderer(this, 0, 36).setTextureSize(64, 64);
      this.bottomBody.addBox(-6.0F, -12.0F, -6.0F, 12, 12, 12, -0.5F);
      this.bottomBody.setRotationPoint(0.0F, 24.0F, 0.0F);
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      super.setRotationAngles(float1, float2, float3, float4, float5, float6, entity);
      this.head.rotateAngleY = float4 / (180.0F / (float)Math.PI);
      this.head.rotateAngleX = float5 / (180.0F / (float)Math.PI);
      this.body.rotateAngleY = float4 / (180.0F / (float)Math.PI) * 0.25F;
      float var8 = MathHelper.sin(this.body.rotateAngleY);
      float var9 = MathHelper.cos(this.body.rotateAngleY);
      this.rightHand.rotateAngleZ = 1.0F;
      this.leftHand.rotateAngleZ = -1.0F;
      this.rightHand.rotateAngleY = 0.0F + this.body.rotateAngleY;
      this.leftHand.rotateAngleY = (float) Math.PI + this.body.rotateAngleY;
      this.rightHand.rotationPointX = var9 * 5.0F;
      this.rightHand.rotationPointZ = -var8 * 5.0F;
      this.leftHand.rotationPointX = -var9 * 5.0F;
      this.leftHand.rotationPointZ = var8 * 5.0F;
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      this.body.render(float7);
      this.bottomBody.render(float7);
      this.head.render(float7);
      this.rightHand.render(float7);
      this.leftHand.render(float7);
   }
}
