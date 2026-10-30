package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelSkeletonHead extends ModelBase {
   public ModelRenderer skeletonHead;

   public ModelSkeletonHead() {
      this(0, 35, 64, 64);
   }

   public ModelSkeletonHead(int integer1, int integer2, int integer3, int integer4) {
      this.textureWidth = integer3;
      this.textureHeight = integer4;
      this.skeletonHead = new ModelRenderer(this, integer1, integer2);
      this.skeletonHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
      this.skeletonHead.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      this.skeletonHead.render(float7);
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      super.setRotationAngles(float1, float2, float3, float4, float5, float6, entity);
      this.skeletonHead.rotateAngleY = float4 / (180.0F / (float)Math.PI);
      this.skeletonHead.rotateAngleX = float5 / (180.0F / (float)Math.PI);
   }
}
