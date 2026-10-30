package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelLeashKnot extends ModelBase {
   public ModelRenderer field_110723_a;

   public ModelLeashKnot() {
      this(0, 0, 32, 32);
   }

   public ModelLeashKnot(int integer1, int integer2, int integer3, int integer4) {
      this.textureWidth = integer3;
      this.textureHeight = integer4;
      this.field_110723_a = new ModelRenderer(this, integer1, integer2);
      this.field_110723_a.addBox(-3.0F, -6.0F, -3.0F, 6, 8, 6, 0.0F);
      this.field_110723_a.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      this.field_110723_a.render(float7);
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      super.setRotationAngles(float1, float2, float3, float4, float5, float6, entity);
      this.field_110723_a.rotateAngleY = float4 / (180.0F / (float)Math.PI);
      this.field_110723_a.rotateAngleX = float5 / (180.0F / (float)Math.PI);
   }
}
