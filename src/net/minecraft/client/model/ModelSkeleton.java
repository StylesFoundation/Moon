package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;

public class ModelSkeleton extends ModelZombie {
   public ModelSkeleton() {
      this(0.0F, false);
   }

   public ModelSkeleton(float float1, boolean boolean2) {
      super(float1, 0.0F, 64, 32);
      if (!boolean2) {
         this.bipedRightArm = new ModelRenderer(this, 40, 16);
         this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, float1);
         this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
         this.bipedLeftArm = new ModelRenderer(this, 40, 16);
         this.bipedLeftArm.mirror = true;
         this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, float1);
         this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.bipedRightLeg = new ModelRenderer(this, 0, 16);
         this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, float1);
         this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
         this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
         this.bipedLeftLeg.mirror = true;
         this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, float1);
         this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
      }
   }

   @Override
   public void setLivingAnimations(EntityLivingBase entityLivingBase, float float2, float float3, float float4) {
      this.aimedBow = ((EntitySkeleton)entityLivingBase).getSkeletonType() == 1;
      super.setLivingAnimations(entityLivingBase, float2, float3, float4);
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      super.setRotationAngles(float1, float2, float3, float4, float5, float6, entity);
   }
}
