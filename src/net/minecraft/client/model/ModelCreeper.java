package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelCreeper extends ModelBase {
   public ModelRenderer head;
   public ModelRenderer creeperArmor;
   public ModelRenderer body;
   public ModelRenderer leg1;
   public ModelRenderer leg2;
   public ModelRenderer leg3;
   public ModelRenderer leg4;

   public ModelCreeper() {
      this(0.0F);
   }

   public ModelCreeper(float float1) {
      byte var4 = 6;
      this.head = new ModelRenderer(this, 0, 0);
      this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, float1);
      this.head.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.creeperArmor = new ModelRenderer(this, 32, 0);
      this.creeperArmor.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, float1 + 0.5F);
      this.creeperArmor.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.body = new ModelRenderer(this, 16, 16);
      this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, float1);
      this.body.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.leg1 = new ModelRenderer(this, 0, 16);
      this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, float1);
      this.leg1.setRotationPoint(-2.0F, 18.0F, 4.0F);
      this.leg2 = new ModelRenderer(this, 0, 16);
      this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, float1);
      this.leg2.setRotationPoint(2.0F, 18.0F, 4.0F);
      this.leg3 = new ModelRenderer(this, 0, 16);
      this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, float1);
      this.leg3.setRotationPoint(-2.0F, 18.0F, -4.0F);
      this.leg4 = new ModelRenderer(this, 0, 16);
      this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, float1);
      this.leg4.setRotationPoint(2.0F, 18.0F, -4.0F);
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      this.head.render(float7);
      this.body.render(float7);
      this.leg1.render(float7);
      this.leg2.render(float7);
      this.leg3.render(float7);
      this.leg4.render(float7);
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      this.head.rotateAngleY = float4 / (180.0F / (float)Math.PI);
      this.head.rotateAngleX = float5 / (180.0F / (float)Math.PI);
      this.leg1.rotateAngleX = MathHelper.cos(float1 * 0.6662F) * 1.4F * float2;
      this.leg2.rotateAngleX = MathHelper.cos(float1 * 0.6662F + (float) Math.PI) * 1.4F * float2;
      this.leg3.rotateAngleX = MathHelper.cos(float1 * 0.6662F + (float) Math.PI) * 1.4F * float2;
      this.leg4.rotateAngleX = MathHelper.cos(float1 * 0.6662F) * 1.4F * float2;
   }
}
