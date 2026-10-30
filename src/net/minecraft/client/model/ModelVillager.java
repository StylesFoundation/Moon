package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelVillager extends ModelBase {
   public ModelRenderer villagerHead;
   public ModelRenderer villagerBody;
   public ModelRenderer villagerArms;
   public ModelRenderer rightVillagerLeg;
   public ModelRenderer leftVillagerLeg;
   public ModelRenderer villagerNose;

   public ModelVillager(float float1) {
      this(float1, 0.0F, 64, 64);
   }

   public ModelVillager(float float1, float float2, int integer3, int integer4) {
      this.villagerHead = new ModelRenderer(this).setTextureSize(integer3, integer4);
      this.villagerHead.setRotationPoint(0.0F, 0.0F + float2, 0.0F);
      this.villagerHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, float1);
      this.villagerNose = new ModelRenderer(this).setTextureSize(integer3, integer4);
      this.villagerNose.setRotationPoint(0.0F, float2 - 2.0F, 0.0F);
      this.villagerNose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, float1);
      this.villagerHead.addChild(this.villagerNose);
      this.villagerBody = new ModelRenderer(this).setTextureSize(integer3, integer4);
      this.villagerBody.setRotationPoint(0.0F, 0.0F + float2, 0.0F);
      this.villagerBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, float1);
      this.villagerBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, float1 + 0.5F);
      this.villagerArms = new ModelRenderer(this).setTextureSize(integer3, integer4);
      this.villagerArms.setRotationPoint(0.0F, 0.0F + float2 + 2.0F, 0.0F);
      this.villagerArms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, float1);
      this.villagerArms.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, float1);
      this.villagerArms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, float1);
      this.rightVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(integer3, integer4);
      this.rightVillagerLeg.setRotationPoint(-2.0F, 12.0F + float2, 0.0F);
      this.rightVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, float1);
      this.leftVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(integer3, integer4);
      this.leftVillagerLeg.mirror = true;
      this.leftVillagerLeg.setRotationPoint(2.0F, 12.0F + float2, 0.0F);
      this.leftVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, float1);
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      this.setRotationAngles(float2, float3, float4, float5, float6, float7, entity);
      this.villagerHead.render(float7);
      this.villagerBody.render(float7);
      this.rightVillagerLeg.render(float7);
      this.leftVillagerLeg.render(float7);
      this.villagerArms.render(float7);
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      this.villagerHead.rotateAngleY = float4 / (180.0F / (float)Math.PI);
      this.villagerHead.rotateAngleX = float5 / (180.0F / (float)Math.PI);
      this.villagerArms.rotationPointY = 3.0F;
      this.villagerArms.rotationPointZ = -1.0F;
      this.villagerArms.rotateAngleX = -0.75F;
      this.rightVillagerLeg.rotateAngleX = MathHelper.cos(float1 * 0.6662F) * 1.4F * float2 * 0.5F;
      this.leftVillagerLeg.rotateAngleX = MathHelper.cos(float1 * 0.6662F + (float) Math.PI) * 1.4F * float2 * 0.5F;
      this.rightVillagerLeg.rotateAngleY = 0.0F;
      this.leftVillagerLeg.rotateAngleY = 0.0F;
   }
}
