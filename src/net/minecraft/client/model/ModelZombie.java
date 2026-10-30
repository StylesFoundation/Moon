package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelZombie extends ModelBiped {
   public ModelZombie() {
      this(0.0F, false);
   }

   protected ModelZombie(float float1, float float2, int integer3, int integer4) {
      super(float1, float2, integer3, integer4);
   }

   public ModelZombie(float float1, boolean boolean2) {
      super(float1, 0.0F, 64, boolean2 ? 32 : 64);
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
