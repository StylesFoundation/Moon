package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;

public class ModelDragon extends ModelBase {
   public ModelRenderer head;
   public ModelRenderer spine;
   public ModelRenderer jaw;
   public ModelRenderer body;
   public ModelRenderer rearLeg;
   public ModelRenderer frontLeg;
   public ModelRenderer rearLegTip;
   public ModelRenderer frontLegTip;
   public ModelRenderer rearFoot;
   public ModelRenderer frontFoot;
   public ModelRenderer wing;
   public ModelRenderer wingTip;
   private float partialTicks;

   public ModelDragon(float float1) {
      this.textureWidth = 256;
      this.textureHeight = 256;
      this.setTextureOffset("body.body", 0, 0);
      this.setTextureOffset("wing.skin", -56, 88);
      this.setTextureOffset("wingtip.skin", -56, 144);
      this.setTextureOffset("rearleg.main", 0, 0);
      this.setTextureOffset("rearfoot.main", 112, 0);
      this.setTextureOffset("rearlegtip.main", 196, 0);
      this.setTextureOffset("head.upperhead", 112, 30);
      this.setTextureOffset("wing.bone", 112, 88);
      this.setTextureOffset("head.upperlip", 176, 44);
      this.setTextureOffset("jaw.jaw", 176, 65);
      this.setTextureOffset("frontleg.main", 112, 104);
      this.setTextureOffset("wingtip.bone", 112, 136);
      this.setTextureOffset("frontfoot.main", 144, 104);
      this.setTextureOffset("neck.box", 192, 104);
      this.setTextureOffset("frontlegtip.main", 226, 138);
      this.setTextureOffset("body.scale", 220, 53);
      this.setTextureOffset("head.scale", 0, 0);
      this.setTextureOffset("neck.scale", 48, 0);
      this.setTextureOffset("head.nostril", 112, 0);
      this.head = new ModelRenderer(this, "head");
      this.head.addBox("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16);
      this.head.addBox("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16);
      this.head.mirror = true;
      this.head.addBox("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6);
      this.head.addBox("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4);
      this.head.mirror = false;
      this.head.addBox("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6);
      this.head.addBox("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4);
      this.jaw = new ModelRenderer(this, "jaw");
      this.jaw.setRotationPoint(0.0F, 4.0F, -8.0F);
      this.jaw.addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
      this.head.addChild(this.jaw);
      this.spine = new ModelRenderer(this, "neck");
      this.spine.addBox("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
      this.spine.addBox("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
      this.body = new ModelRenderer(this, "body");
      this.body.setRotationPoint(0.0F, 4.0F, 8.0F);
      this.body.addBox("body", -12.0F, 0.0F, -16.0F, 24, 24, 64);
      this.body.addBox("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12);
      this.body.addBox("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12);
      this.body.addBox("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12);
      this.wing = new ModelRenderer(this, "wing");
      this.wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
      this.wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
      this.wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
      this.wingTip = new ModelRenderer(this, "wingtip");
      this.wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
      this.wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
      this.wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
      this.wing.addChild(this.wingTip);
      this.frontLeg = new ModelRenderer(this, "frontleg");
      this.frontLeg.setRotationPoint(-12.0F, 20.0F, 2.0F);
      this.frontLeg.addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8);
      this.frontLegTip = new ModelRenderer(this, "frontlegtip");
      this.frontLegTip.setRotationPoint(0.0F, 20.0F, -1.0F);
      this.frontLegTip.addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6);
      this.frontLeg.addChild(this.frontLegTip);
      this.frontFoot = new ModelRenderer(this, "frontfoot");
      this.frontFoot.setRotationPoint(0.0F, 23.0F, 0.0F);
      this.frontFoot.addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16);
      this.frontLegTip.addChild(this.frontFoot);
      this.rearLeg = new ModelRenderer(this, "rearleg");
      this.rearLeg.setRotationPoint(-16.0F, 16.0F, 42.0F);
      this.rearLeg.addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16);
      this.rearLegTip = new ModelRenderer(this, "rearlegtip");
      this.rearLegTip.setRotationPoint(0.0F, 32.0F, -4.0F);
      this.rearLegTip.addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12);
      this.rearLeg.addChild(this.rearLegTip);
      this.rearFoot = new ModelRenderer(this, "rearfoot");
      this.rearFoot.setRotationPoint(0.0F, 31.0F, 4.0F);
      this.rearFoot.addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24);
      this.rearLegTip.addChild(this.rearFoot);
   }

   @Override
   public void setLivingAnimations(EntityLivingBase entityLivingBase, float float2, float float3, float float4) {
      this.partialTicks = float4;
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      GlStateManager.pushMatrix();
      EntityDragon var11 = (EntityDragon)entity;
      float var12 = var11.prevAnimTime + (var11.animTime - var11.prevAnimTime) * this.partialTicks;
      this.jaw.rotateAngleX = (float)(Math.sin(var12 * (float) Math.PI * 2.0F) + 1.0) * 0.2F;
      float var13 = (float)(Math.sin(var12 * (float) Math.PI * 2.0F - 1.0F) + 1.0);
      var13 = (var13 * var13 * 1.0F + var13 * 2.0F) * 0.05F;
      GlStateManager.translate(0.0F, var13 - 2.0F, -3.0F);
      GlStateManager.rotate(var13 * 2.0F, 1.0F, 0.0F, 0.0F);
      float var15 = 0.0F;
      double[] var17 = var11.getMovementOffsets(6, this.partialTicks);
      float var18 = this.updateRotations(var11.getMovementOffsets(5, this.partialTicks)[0] - var11.getMovementOffsets(10, this.partialTicks)[0]);
      float var19 = this.updateRotations(var11.getMovementOffsets(5, this.partialTicks)[0] + var18 / 2.0F);
      float var20 = var12 * (float) Math.PI * 2.0F;
      float var14 = 20.0F;
      float var21 = -12.0F;

      for (int var22 = 0; var22 < 5; var22++) {
         double[] var23 = var11.getMovementOffsets(5 - var22, this.partialTicks);
         float var24 = (float)Math.cos(var22 * 0.45F + var20) * 0.15F;
         this.spine.rotateAngleY = this.updateRotations(var23[0] - var17[0]) * (float) Math.PI / 180.0F * 1.5F;
         this.spine.rotateAngleX = var24 + (float)(var23[1] - var17[1]) * (float) Math.PI / 180.0F * 1.5F * 5.0F;
         this.spine.rotateAngleZ = -this.updateRotations(var23[0] - var19) * (float) Math.PI / 180.0F * 1.5F;
         this.spine.rotationPointY = var14;
         this.spine.rotationPointZ = var21;
         this.spine.rotationPointX = var15;
         var14 = (float)(var14 + Math.sin(this.spine.rotateAngleX) * 10.0);
         var21 = (float)(var21 - Math.cos(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0);
         var15 = (float)(var15 - Math.sin(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0);
         this.spine.render(float7);
      }

      this.head.rotationPointY = var14;
      this.head.rotationPointZ = var21;
      this.head.rotationPointX = var15;
      double[] var31 = var11.getMovementOffsets(0, this.partialTicks);
      this.head.rotateAngleY = this.updateRotations(var31[0] - var17[0]) * (float) Math.PI / 180.0F;
      this.head.rotateAngleZ = -this.updateRotations(var31[0] - var19) * (float) Math.PI / 180.0F;
      this.head.render(float7);
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-var18 * 1.5F * 1.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(0.0F, -1.0F, 0.0F);
      this.body.rotateAngleZ = 0.0F;
      this.body.render(float7);

      for (int var33 = 0; var33 < 2; var33++) {
         GlStateManager.enableCull();
         float var35 = var12 * (float) Math.PI * 2.0F;
         this.wing.rotateAngleX = 0.125F - (float)Math.cos(var35) * 0.2F;
         this.wing.rotateAngleY = 0.25F;
         this.wing.rotateAngleZ = (float)(Math.sin(var35) + 0.125) * 0.8F;
         this.wingTip.rotateAngleZ = -((float)(Math.sin(var35 + 2.0F) + 0.5)) * 0.75F;
         this.rearLeg.rotateAngleX = 1.0F + var13 * 0.1F;
         this.rearLegTip.rotateAngleX = 0.5F + var13 * 0.1F;
         this.rearFoot.rotateAngleX = 0.75F + var13 * 0.1F;
         this.frontLeg.rotateAngleX = 1.3F + var13 * 0.1F;
         this.frontLegTip.rotateAngleX = -0.5F - var13 * 0.1F;
         this.frontFoot.rotateAngleX = 0.75F + var13 * 0.1F;
         this.wing.render(float7);
         this.frontLeg.render(float7);
         this.rearLeg.render(float7);
         GlStateManager.scale(-1.0F, 1.0F, 1.0F);
         if (var33 == 0) {
            GlStateManager.cullFace(1028);
         }
      }

      GlStateManager.popMatrix();
      GlStateManager.cullFace(1029);
      GlStateManager.disableCull();
      float var34 = -((float)Math.sin(var12 * (float) Math.PI * 2.0F)) * 0.0F;
      var20 = var12 * (float) Math.PI * 2.0F;
      var14 = 10.0F;
      var21 = 60.0F;
      var15 = 0.0F;
      var17 = var11.getMovementOffsets(11, this.partialTicks);

      for (int var36 = 0; var36 < 12; var36++) {
         var31 = var11.getMovementOffsets(12 + var36, this.partialTicks);
         var34 = (float)(var34 + Math.sin(var36 * 0.45F + var20) * 0.05F);
         this.spine.rotateAngleY = (this.updateRotations(var31[0] - var17[0]) * 1.5F + 180.0F) * (float) Math.PI / 180.0F;
         this.spine.rotateAngleX = var34 + (float)(var31[1] - var17[1]) * (float) Math.PI / 180.0F * 1.5F * 5.0F;
         this.spine.rotateAngleZ = this.updateRotations(var31[0] - var19) * (float) Math.PI / 180.0F * 1.5F;
         this.spine.rotationPointY = var14;
         this.spine.rotationPointZ = var21;
         this.spine.rotationPointX = var15;
         var14 = (float)(var14 + Math.sin(this.spine.rotateAngleX) * 10.0);
         var21 = (float)(var21 - Math.cos(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0);
         var15 = (float)(var15 - Math.sin(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0);
         this.spine.render(float7);
      }

      GlStateManager.popMatrix();
   }

   private float updateRotations(double double1) {
      while (double1 >= 180.0) {
         double1 -= 360.0;
      }

      while (double1 < -180.0) {
         double1 += 360.0;
      }

      return (float)double1;
   }
}
