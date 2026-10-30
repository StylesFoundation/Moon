package net.minecraft.client.model;

import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelGhast extends ModelBase {
   public ModelRenderer body;
   public ModelRenderer[] tentacles = new ModelRenderer[9];

   public ModelGhast() {
      byte var4 = -16;
      this.body = new ModelRenderer(this, 0, 0);
      this.body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
      this.body.rotationPointY += 8.0F;
      Random var5 = new Random(1660L);

      for (int var6 = 0; var6 < this.tentacles.length; var6++) {
         this.tentacles[var6] = new ModelRenderer(this, 0, 0);
         float var7 = ((var6 % 3 - var6 / 3 % 2 * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
         float var8 = (var6 / 3 / 2.0F * 2.0F - 1.0F) * 5.0F;
         int var9 = var5.nextInt(7) + 8;
         this.tentacles[var6].addBox(-1.0F, 0.0F, -1.0F, 2, var9, 2);
         this.tentacles[var6].rotationPointX = var7;
         this.tentacles[var6].rotationPointZ = var8;
         this.tentacles[var6].rotationPointY = 15.0F;
      }
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      for (int var11 = 0; var11 < this.tentacles.length; var11++) {
         this.tentacles[var11].rotateAngleX = 0.2F * MathHelper.sin(float3 * 0.3F + var11) + 0.4F;
      }
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 0.6F, 0.0F);
      this.body.render(float7);

      for (ModelRenderer var14 : this.tentacles) {
         var14.render(float7);
      }

      GlStateManager.popMatrix();
   }
}
