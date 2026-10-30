package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.MathHelper;

public class ModelWither extends ModelBase {
   public ModelRenderer[] field_82905_a;
   public ModelRenderer[] field_82904_b;

   public ModelWither(float float1) {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.field_82905_a = new ModelRenderer[3];
      this.field_82905_a[0] = new ModelRenderer(this, 0, 16);
      this.field_82905_a[0].addBox(-10.0F, 3.9F, -0.5F, 20, 3, 3, float1);
      this.field_82905_a[1] = new ModelRenderer(this).setTextureSize(this.textureWidth, this.textureHeight);
      this.field_82905_a[1].setRotationPoint(-2.0F, 6.9F, -0.5F);
      this.field_82905_a[1].setTextureOffset(0, 22).addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, float1);
      this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0F, 1.5F, 0.5F, 11, 2, 2, float1);
      this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0F, 4.0F, 0.5F, 11, 2, 2, float1);
      this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0F, 6.5F, 0.5F, 11, 2, 2, float1);
      this.field_82905_a[2] = new ModelRenderer(this, 12, 22);
      this.field_82905_a[2].addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, float1);
      this.field_82904_b = new ModelRenderer[3];
      this.field_82904_b[0] = new ModelRenderer(this, 0, 0);
      this.field_82904_b[0].addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, float1);
      this.field_82904_b[1] = new ModelRenderer(this, 32, 0);
      this.field_82904_b[1].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6, float1);
      this.field_82904_b[1].rotationPointX = -8.0F;
      this.field_82904_b[1].rotationPointY = 4.0F;
      this.field_82904_b[2] = new ModelRenderer(this, 32, 0);
      this.field_82904_b[2].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6, float1);
      this.field_82904_b[2].rotationPointX = 10.0F;
      this.field_82904_b[2].rotationPointY = 4.0F;
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);

      for (ModelRenderer var14 : this.field_82904_b) {
         var14.render(float7);
      }

      ModelRenderer[] var15 = this.field_82905_a;
      int var16 = var15.length;

      for (int var17 = 0; var17 < var16; var17++) {
         var15[var17].render(float7);
      }
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      float var8 = MathHelper.cos(float3 * 0.1F);
      this.field_82905_a[1].rotateAngleX = (0.065F + 0.05F * var8) * (float) Math.PI;
      this.field_82905_a[2]
         .setRotationPoint(
            -2.0F, 6.9F + MathHelper.cos(this.field_82905_a[1].rotateAngleX) * 10.0F, -0.5F + MathHelper.sin(this.field_82905_a[1].rotateAngleX) * 10.0F
         );
      this.field_82905_a[2].rotateAngleX = (0.265F + 0.1F * var8) * (float) Math.PI;
      this.field_82904_b[0].rotateAngleY = float4 / (180.0F / (float)Math.PI);
      this.field_82904_b[0].rotateAngleX = float5 / (180.0F / (float)Math.PI);
   }

   @Override
   public void setLivingAnimations(EntityLivingBase entityLivingBase, float float2, float float3, float float4) {
      EntityWither var8 = (EntityWither)entityLivingBase;

      for (int var9 = 1; var9 < 3; var9++) {
         this.field_82904_b[var9].rotateAngleY = (var8.func_82207_a(var9 - 1) - entityLivingBase.renderYawOffset) / (180.0F / (float)Math.PI);
         this.field_82904_b[var9].rotateAngleX = var8.func_82210_r(var9 - 1) / (180.0F / (float)Math.PI);
      }
   }
}
