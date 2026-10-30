package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelSquid extends ModelBase {
   public ModelRenderer squidBody;
   public ModelRenderer[] squidTentacles = new ModelRenderer[8];

   public ModelSquid() {
      byte var4 = -16;
      this.squidBody = new ModelRenderer(this, 0, 0);
      this.squidBody.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
      this.squidBody.rotationPointY += 8.0F;

      for (int var5 = 0; var5 < this.squidTentacles.length; var5++) {
         this.squidTentacles[var5] = new ModelRenderer(this, 48, 0);
         double var6 = var5 * Math.PI * 2.0 / this.squidTentacles.length;
         float var8 = (float)Math.cos(var6) * 5.0F;
         float var9 = (float)Math.sin(var6) * 5.0F;
         this.squidTentacles[var5].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
         this.squidTentacles[var5].rotationPointX = var8;
         this.squidTentacles[var5].rotationPointZ = var9;
         this.squidTentacles[var5].rotationPointY = 15.0F;
         var6 = var5 * Math.PI * -2.0 / this.squidTentacles.length + (Math.PI / 2);
         this.squidTentacles[var5].rotateAngleY = (float)var6;
      }
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      ModelRenderer[] var11 = this.squidTentacles;
      int var12 = var11.length;

      for (int var13 = 0; var13 < var12; var13++) {
         var11[var13].rotateAngleX = float3;
      }
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      this.squidBody.render(float7);
      ModelRenderer[] var11 = this.squidTentacles;
      int var12 = var11.length;

      for (int var13 = 0; var13 < var12; var13++) {
         var11[var13].render(float7);
      }
   }
}
