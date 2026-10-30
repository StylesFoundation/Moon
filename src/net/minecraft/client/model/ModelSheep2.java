package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;

public class ModelSheep2 extends ModelQuadruped {
   private float headRotationAngleX;

   public ModelSheep2() {
      super(12, 0.0F);
      this.head = new ModelRenderer(this, 0, 0);
      this.head.addBox(-3.0F, -4.0F, -6.0F, 6, 6, 8, 0.0F);
      this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
      this.body = new ModelRenderer(this, 28, 8);
      this.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 0.0F);
      this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
   }

   @Override
   public void setLivingAnimations(EntityLivingBase entityLivingBase, float float2, float float3, float float4) {
      super.setLivingAnimations(entityLivingBase, float2, float3, float4);
      this.head.rotationPointY = 6.0F + ((EntitySheep)entityLivingBase).getHeadRotationPointY(float4) * 9.0F;
      this.headRotationAngleX = ((EntitySheep)entityLivingBase).getHeadRotationAngleX(float4);
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      super.setRotationAngles(float1, float2, float3, float4, float5, float6, entity);
      this.head.rotateAngleX = this.headRotationAngleX;
   }
}
