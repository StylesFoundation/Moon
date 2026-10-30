package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelSlime extends ModelBase {
   public ModelRenderer slimeBodies;
   public ModelRenderer slimeRightEye;
   public ModelRenderer slimeLeftEye;
   public ModelRenderer slimeMouth;

   public ModelSlime(int integer) {
      this.slimeBodies = new ModelRenderer(this, 0, integer);
      this.slimeBodies.addBox(-4.0F, 16.0F, -4.0F, 8, 8, 8);
      if (integer > 0) {
         this.slimeBodies = new ModelRenderer(this, 0, integer);
         this.slimeBodies.addBox(-3.0F, 17.0F, -3.0F, 6, 6, 6);
         this.slimeRightEye = new ModelRenderer(this, 32, 0);
         this.slimeRightEye.addBox(-3.25F, 18.0F, -3.5F, 2, 2, 2);
         this.slimeLeftEye = new ModelRenderer(this, 32, 4);
         this.slimeLeftEye.addBox(1.25F, 18.0F, -3.5F, 2, 2, 2);
         this.slimeMouth = new ModelRenderer(this, 32, 8);
         this.slimeMouth.addBox(0.0F, 21.0F, -3.5F, 1, 1, 1);
      }
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      this.slimeBodies.render(float7);
      if (this.slimeRightEye != null) {
         this.slimeRightEye.render(float7);
         this.slimeLeftEye.render(float7);
         this.slimeMouth.render(float7);
      }
   }
}
