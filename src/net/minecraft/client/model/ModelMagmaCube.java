package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;

public class ModelMagmaCube extends ModelBase {
   public ModelRenderer[] segments = new ModelRenderer[8];
   public ModelRenderer core;

   public ModelMagmaCube() {
      for (int var4 = 0; var4 < this.segments.length; var4++) {
         byte var5 = 0;
         int var6 = var4;
         if (var4 == 2) {
            var5 = 24;
            var6 = 10;
         } else if (var4 == 3) {
            var5 = 24;
            var6 = 19;
         }

         this.segments[var4] = new ModelRenderer(this, var5, var6);
         this.segments[var4].addBox(-4.0F, 16 + var4, -4.0F, 8, 1, 8);
      }

      this.core = new ModelRenderer(this, 0, 16);
      this.core.addBox(-2.0F, 18.0F, -2.0F, 4, 4, 4);
   }

   @Override
   public void setLivingAnimations(EntityLivingBase entityLivingBase, float float2, float float3, float float4) {
      EntityMagmaCube var8 = (EntityMagmaCube)entityLivingBase;
      float var9 = var8.prevSquishFactor + (var8.squishFactor - var8.prevSquishFactor) * float4;
      if (var9 < 0.0F) {
         var9 = 0.0F;
      }

      for (int var10 = 0; var10 < this.segments.length; var10++) {
         this.segments[var10].rotationPointY = -(4 - var10) * var9 * 1.7F;
      }
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      this.core.render(float7);
      ModelRenderer[] var11 = this.segments;
      int var12 = var11.length;

      for (int var13 = 0; var13 < var12; var13++) {
         var11[var13].render(float7);
      }
   }
}
