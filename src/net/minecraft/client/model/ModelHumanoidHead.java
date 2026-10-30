package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelHumanoidHead extends ModelSkeletonHead {
   public final ModelRenderer head = new ModelRenderer(this, 32, 0);

   public ModelHumanoidHead() {
      super(0, 0, 64, 64);
      this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.25F);
      this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      super.render(entity, float2, float3, float4, float5, float6, float7);
      this.head.render(float7);
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      super.setRotationAngles(float1, float2, float3, float4, float5, float6, entity);
      this.head.rotateAngleY = this.skeletonHead.rotateAngleY;
      this.head.rotateAngleX = this.skeletonHead.rotateAngleX;
   }
}
