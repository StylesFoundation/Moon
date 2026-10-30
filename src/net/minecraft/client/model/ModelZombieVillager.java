package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelZombieVillager extends ModelBiped {
   public ModelZombieVillager() {
      this(0.0F, 0.0F, false);
   }

   public ModelZombieVillager(float float1, float float2, boolean boolean3) {
      super(float1, 0.0F, 64, boolean3 ? 32 : 64);
      if (boolean3) {
         this.bipedHead = new ModelRenderer(this, 0, 0);
         this.bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8, 8, 8, float1);
         this.bipedHead.setRotationPoint(0.0F, 0.0F + float2, 0.0F);
      } else {
         this.bipedHead = new ModelRenderer(this);
         this.bipedHead.setRotationPoint(0.0F, 0.0F + float2, 0.0F);
         this.bipedHead.setTextureOffset(0, 32).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, float1);
         this.bipedHead.setTextureOffset(24, 32).addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, float1);
      }
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      super.setRotationAngles(float1, float2, float3, float4, float5, float6, entity);
      float var8 = MathHelper.sin(this.swingProgress * (float) Math.PI);
      float var9 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
      this.bipedRightArm.rotateAngleZ = 0.0F;
      this.bipedLeftArm.rotateAngleZ = 0.0F;
      this.bipedRightArm.rotateAngleY = -(0.1F - var8 * 0.6F);
      this.bipedLeftArm.rotateAngleY = 0.1F - var8 * 0.6F;
      this.bipedRightArm.rotateAngleX = (float) (-Math.PI / 2);
      this.bipedLeftArm.rotateAngleX = (float) (-Math.PI / 2);
      this.bipedRightArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
      this.bipedLeftArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
      this.bipedRightArm.rotateAngleZ = this.bipedRightArm.rotateAngleZ + (MathHelper.cos(float3 * 0.09F) * 0.05F + 0.05F);
      this.bipedLeftArm.rotateAngleZ = this.bipedLeftArm.rotateAngleZ - (MathHelper.cos(float3 * 0.09F) * 0.05F + 0.05F);
      this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX + MathHelper.sin(float3 * 0.067F) * 0.05F;
      this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX - MathHelper.sin(float3 * 0.067F) * 0.05F;
   }
}
