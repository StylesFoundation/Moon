package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelChicken extends ModelBase {
   public ModelRenderer head;
   public ModelRenderer body;
   public ModelRenderer rightLeg;
   public ModelRenderer leftLeg;
   public ModelRenderer rightWing;
   public ModelRenderer leftWing;
   public ModelRenderer bill;
   public ModelRenderer chin;

   public ModelChicken() {
      byte var3 = 16;
      this.head = new ModelRenderer(this, 0, 0);
      this.head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
      this.head.setRotationPoint(0.0F, 15.0F, -4.0F);
      this.bill = new ModelRenderer(this, 14, 0);
      this.bill.addBox(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
      this.bill.setRotationPoint(0.0F, 15.0F, -4.0F);
      this.chin = new ModelRenderer(this, 14, 4);
      this.chin.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
      this.chin.setRotationPoint(0.0F, 15.0F, -4.0F);
      this.body = new ModelRenderer(this, 0, 9);
      this.body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
      this.body.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.rightLeg = new ModelRenderer(this, 26, 0);
      this.rightLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
      this.rightLeg.setRotationPoint(-2.0F, 19.0F, 1.0F);
      this.leftLeg = new ModelRenderer(this, 26, 0);
      this.leftLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
      this.leftLeg.setRotationPoint(1.0F, 19.0F, 1.0F);
      this.rightWing = new ModelRenderer(this, 24, 13);
      this.rightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
      this.rightWing.setRotationPoint(-4.0F, 13.0F, 0.0F);
      this.leftWing = new ModelRenderer(this, 24, 13);
      this.leftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
      this.leftWing.setRotationPoint(4.0F, 13.0F, 0.0F);
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      if (this.isChild) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 5.0F * float7, 2.0F * float7);
         this.head.render(float7);
         this.bill.render(float7);
         this.chin.render(float7);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.scale(0.5F, 0.5F, 0.5F);
         GlStateManager.translate(0.0F, 24.0F * float7, 0.0F);
         this.body.render(float7);
         this.rightLeg.render(float7);
         this.leftLeg.render(float7);
         this.rightWing.render(float7);
         this.leftWing.render(float7);
         GlStateManager.popMatrix();
      } else {
         this.head.render(float7);
         this.bill.render(float7);
         this.chin.render(float7);
         this.body.render(float7);
         this.rightLeg.render(float7);
         this.leftLeg.render(float7);
         this.rightWing.render(float7);
         this.leftWing.render(float7);
      }
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      this.head.rotateAngleX = float5 / (180.0F / (float)Math.PI);
      this.head.rotateAngleY = float4 / (180.0F / (float)Math.PI);
      this.bill.rotateAngleX = this.head.rotateAngleX;
      this.bill.rotateAngleY = this.head.rotateAngleY;
      this.chin.rotateAngleX = this.head.rotateAngleX;
      this.chin.rotateAngleY = this.head.rotateAngleY;
      this.body.rotateAngleX = (float) (Math.PI / 2);
      this.rightLeg.rotateAngleX = MathHelper.cos(float1 * 0.6662F) * 1.4F * float2;
      this.leftLeg.rotateAngleX = MathHelper.cos(float1 * 0.6662F + (float) Math.PI) * 1.4F * float2;
      this.rightWing.rotateAngleZ = float3;
      this.leftWing.rotateAngleZ = -float3;
   }
}
