package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBlaze extends ModelBase {
   public ModelRenderer[] blazeSticks = new ModelRenderer[12];
   public ModelRenderer blazeHead;

   public ModelBlaze() {
      for (int var4 = 0; var4 < this.blazeSticks.length; var4++) {
         this.blazeSticks[var4] = new ModelRenderer(this, 0, 16);
         this.blazeSticks[var4].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
      }

      this.blazeHead = new ModelRenderer(this, 0, 0);
      this.blazeHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      this.blazeHead.render(float7);
      ModelRenderer[] var11 = this.blazeSticks;
      int var12 = var11.length;

      for (int var13 = 0; var13 < var12; var13++) {
         var11[var13].render(float7);
      }
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      float var11 = float3 * (float) Math.PI * -0.1F;

      for (int var12 = 0; var12 < 4; var12++) {
         this.blazeSticks[var12].rotationPointY = -2.0F + MathHelper.cos((var12 * 2 + float3) * 0.25F);
         this.blazeSticks[var12].rotationPointX = MathHelper.cos(var11) * 9.0F;
         this.blazeSticks[var12].rotationPointZ = MathHelper.sin(var11) * 9.0F;
         var11++;
      }

      var11 = (float) (Math.PI / 4) + float3 * (float) Math.PI * 0.03F;

      for (int var15 = 4; var15 < 8; var15++) {
         this.blazeSticks[var15].rotationPointY = 2.0F + MathHelper.cos((var15 * 2 + float3) * 0.25F);
         this.blazeSticks[var15].rotationPointX = MathHelper.cos(var11) * 7.0F;
         this.blazeSticks[var15].rotationPointZ = MathHelper.sin(var11) * 7.0F;
         var11++;
      }

      var11 = 0.47123894F + float3 * (float) Math.PI * -0.05F;

      for (int var16 = 8; var16 < 12; var16++) {
         this.blazeSticks[var16].rotationPointY = 11.0F + MathHelper.cos((var16 * 1.5F + float3) * 0.5F);
         this.blazeSticks[var16].rotationPointX = MathHelper.cos(var11) * 5.0F;
         this.blazeSticks[var16].rotationPointZ = MathHelper.sin(var11) * 5.0F;
         var11++;
      }

      this.blazeHead.rotateAngleY = float4 / (180.0F / (float)Math.PI);
      this.blazeHead.rotateAngleX = float5 / (180.0F / (float)Math.PI);
   }
}
