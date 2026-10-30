package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBook extends ModelBase {
   public ModelRenderer coverRight = new ModelRenderer(this).setTextureOffset(0, 0).addBox(-6.0F, -5.0F, 0.0F, 6, 10, 0);
   public ModelRenderer coverLeft = new ModelRenderer(this).setTextureOffset(16, 0).addBox(0.0F, -5.0F, 0.0F, 6, 10, 0);
   public ModelRenderer pagesRight = new ModelRenderer(this).setTextureOffset(0, 10).addBox(0.0F, -4.0F, -0.99F, 5, 8, 1);
   public ModelRenderer pagesLeft = new ModelRenderer(this).setTextureOffset(12, 10).addBox(0.0F, -4.0F, -0.01F, 5, 8, 1);
   public ModelRenderer flippingPageRight = new ModelRenderer(this).setTextureOffset(24, 10).addBox(0.0F, -4.0F, 0.0F, 5, 8, 0);
   public ModelRenderer flippingPageLeft = new ModelRenderer(this).setTextureOffset(24, 10).addBox(0.0F, -4.0F, 0.0F, 5, 8, 0);
   public ModelRenderer bookSpine = new ModelRenderer(this).setTextureOffset(12, 0).addBox(-1.0F, -5.0F, 0.0F, 2, 10, 0);

   public ModelBook() {
      this.coverRight.setRotationPoint(0.0F, 0.0F, -1.0F);
      this.coverLeft.setRotationPoint(0.0F, 0.0F, 1.0F);
      this.bookSpine.rotateAngleY = (float) (Math.PI / 2);
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      this.coverRight.render(float7);
      this.coverLeft.render(float7);
      this.bookSpine.render(float7);
      this.pagesRight.render(float7);
      this.pagesLeft.render(float7);
      this.flippingPageRight.render(float7);
      this.flippingPageLeft.render(float7);
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      float var8 = (MathHelper.sin(float1 * 0.02F) * 0.1F + 1.25F) * float4;
      this.coverRight.rotateAngleY = (float) Math.PI + var8;
      this.coverLeft.rotateAngleY = -var8;
      this.pagesRight.rotateAngleY = var8;
      this.pagesLeft.rotateAngleY = -var8;
      this.flippingPageRight.rotateAngleY = var8 - var8 * 2.0F * float2;
      this.flippingPageLeft.rotateAngleY = var8 - var8 * 2.0F * float3;
      this.pagesRight.rotationPointX = MathHelper.sin(var8);
      this.pagesLeft.rotationPointX = MathHelper.sin(var8);
      this.flippingPageRight.rotationPointX = MathHelper.sin(var8);
      this.flippingPageLeft.rotationPointX = MathHelper.sin(var8);
   }
}
