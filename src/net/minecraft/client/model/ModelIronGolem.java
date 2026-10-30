package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;

public class ModelIronGolem extends ModelBase {
   public ModelRenderer ironGolemHead;
   public ModelRenderer ironGolemBody;
   public ModelRenderer ironGolemRightArm;
   public ModelRenderer ironGolemLeftArm;
   public ModelRenderer ironGolemLeftLeg;
   public ModelRenderer ironGolemRightLeg;

   public ModelIronGolem() {
      this(0.0F);
   }

   public ModelIronGolem(float float1) {
      this(float1, -7.0F);
   }

   public ModelIronGolem(float float1, float float2) {
      short var5 = 128;
      short var6 = 128;
      this.ironGolemHead = new ModelRenderer(this).setTextureSize(var5, var6);
      this.ironGolemHead.setRotationPoint(0.0F, 0.0F + float2, -2.0F);
      this.ironGolemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, float1);
      this.ironGolemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, float1);
      this.ironGolemBody = new ModelRenderer(this).setTextureSize(var5, var6);
      this.ironGolemBody.setRotationPoint(0.0F, 0.0F + float2, 0.0F);
      this.ironGolemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, float1);
      this.ironGolemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, float1 + 0.5F);
      this.ironGolemRightArm = new ModelRenderer(this).setTextureSize(var5, var6);
      this.ironGolemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
      this.ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, float1);
      this.ironGolemLeftArm = new ModelRenderer(this).setTextureSize(var5, var6);
      this.ironGolemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
      this.ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, float1);
      this.ironGolemLeftLeg = new ModelRenderer(this, 0, 22).setTextureSize(var5, var6);
      this.ironGolemLeftLeg.setRotationPoint(-4.0F, 18.0F + float2, 0.0F);
      this.ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, float1);
      this.ironGolemRightLeg = new ModelRenderer(this, 0, 22).setTextureSize(var5, var6);
      this.ironGolemRightLeg.mirror = true;
      this.ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18.0F + float2, 0.0F);
      this.ironGolemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, float1);
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      this.ironGolemHead.render(float7);
      this.ironGolemBody.render(float7);
      this.ironGolemLeftLeg.render(float7);
      this.ironGolemRightLeg.render(float7);
      this.ironGolemRightArm.render(float7);
      this.ironGolemLeftArm.render(float7);
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      this.ironGolemHead.rotateAngleY = float4 / (180.0F / (float)Math.PI);
      this.ironGolemHead.rotateAngleX = float5 / (180.0F / (float)Math.PI);
      this.ironGolemLeftLeg.rotateAngleX = -1.5F * this.func_78172_a(float1, 13.0F) * float2;
      this.ironGolemRightLeg.rotateAngleX = 1.5F * this.func_78172_a(float1, 13.0F) * float2;
      this.ironGolemLeftLeg.rotateAngleY = 0.0F;
      this.ironGolemRightLeg.rotateAngleY = 0.0F;
   }

   @Override
   public void setLivingAnimations(EntityLivingBase entityLivingBase, float float2, float float3, float float4) {
      EntityIronGolem var8 = (EntityIronGolem)entityLivingBase;
      int var9 = var8.getAttackTimer();
      if (var9 > 0) {
         this.ironGolemRightArm.rotateAngleX = -2.0F + 1.5F * this.func_78172_a(var9 - float4, 10.0F);
         this.ironGolemLeftArm.rotateAngleX = -2.0F + 1.5F * this.func_78172_a(var9 - float4, 10.0F);
      } else {
         int var10 = var8.getHoldRoseTick();
         if (var10 > 0) {
            this.ironGolemRightArm.rotateAngleX = -0.8F + 0.025F * this.func_78172_a(var10, 70.0F);
            this.ironGolemLeftArm.rotateAngleX = 0.0F;
         } else {
            this.ironGolemRightArm.rotateAngleX = (-0.2F + 1.5F * this.func_78172_a(float2, 13.0F)) * float3;
            this.ironGolemLeftArm.rotateAngleX = (-0.2F - 1.5F * this.func_78172_a(float2, 13.0F)) * float3;
         }
      }
   }

   private float func_78172_a(float float1, float float2) {
      return (Math.abs(float1 % float2 - float2 * 0.5F) - float2 * 0.25F) / (float2 * 0.25F);
   }
}
