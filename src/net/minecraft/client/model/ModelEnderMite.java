package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelEnderMite extends ModelBase {
   private static final int[][] field_178716_a = new int[][]{{4, 3, 2}, {6, 4, 5}, {3, 3, 1}, {1, 2, 1}};
   private static final int[][] field_178714_b = new int[][]{{0, 0}, {0, 5}, {0, 14}, {0, 18}};
   private static final int field_178715_c = field_178716_a.length;
   public final ModelRenderer[] field_178713_d = new ModelRenderer[field_178715_c];

   public ModelEnderMite() {
      float var4 = -3.5F;

      for (int var5 = 0; var5 < this.field_178713_d.length; var5++) {
         this.field_178713_d[var5] = new ModelRenderer(this, field_178714_b[var5][0], field_178714_b[var5][1]);
         this.field_178713_d[var5]
            .addBox(
               field_178716_a[var5][0] * -0.5F,
               0.0F,
               field_178716_a[var5][2] * -0.5F,
               field_178716_a[var5][0],
               field_178716_a[var5][1],
               field_178716_a[var5][2]
            );
         this.field_178713_d[var5].setRotationPoint(0.0F, 24 - field_178716_a[var5][1], var4);
         if (var5 < this.field_178713_d.length - 1) {
            var4 += (field_178716_a[var5][2] + field_178716_a[var5 + 1][2]) * 0.5F;
         }
      }
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      ModelRenderer[] var11 = this.field_178713_d;
      int var12 = var11.length;

      for (int var13 = 0; var13 < var12; var13++) {
         var11[var13].render(float7);
      }
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      for (int var11 = 0; var11 < this.field_178713_d.length; var11++) {
         this.field_178713_d[var11].rotateAngleY = MathHelper.cos(float3 * 0.9F + var11 * 0.15F * (float) Math.PI)
            * (float) Math.PI
            * 0.01F
            * (1 + Math.abs(var11 - 2));
         this.field_178713_d[var11].rotationPointX = MathHelper.sin(float3 * 0.9F + var11 * 0.15F * (float) Math.PI)
            * (float) Math.PI
            * 0.1F
            * Math.abs(var11 - 2);
      }
   }
}
