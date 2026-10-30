package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelBoat extends ModelBase {
   public ModelRenderer[] boatSides = new ModelRenderer[5];

   public ModelBoat() {
      this.boatSides[0] = new ModelRenderer(this, 0, 8);
      this.boatSides[1] = new ModelRenderer(this, 0, 0);
      this.boatSides[2] = new ModelRenderer(this, 0, 0);
      this.boatSides[3] = new ModelRenderer(this, 0, 0);
      this.boatSides[4] = new ModelRenderer(this, 0, 0);
      byte var3 = 24;
      byte var4 = 6;
      byte var5 = 20;
      this.boatSides[0].addBox(-12.0F, -8.0F, -3.0F, var3, 16, 4, 0.0F);
      this.boatSides[0].setRotationPoint(0.0F, 4.0F, 0.0F);
      this.boatSides[1].addBox(-10.0F, -7.0F, -1.0F, 20, var4, 2, 0.0F);
      this.boatSides[1].setRotationPoint(-11.0F, 4.0F, 0.0F);
      this.boatSides[2].addBox(-10.0F, -7.0F, -1.0F, 20, var4, 2, 0.0F);
      this.boatSides[2].setRotationPoint(11.0F, 4.0F, 0.0F);
      this.boatSides[3].addBox(-10.0F, -7.0F, -1.0F, 20, var4, 2, 0.0F);
      this.boatSides[3].setRotationPoint(0.0F, 4.0F, -9.0F);
      this.boatSides[4].addBox(-10.0F, -7.0F, -1.0F, 20, var4, 2, 0.0F);
      this.boatSides[4].setRotationPoint(0.0F, 4.0F, 9.0F);
      this.boatSides[0].rotateAngleX = (float) (Math.PI / 2);
      this.boatSides[1].rotateAngleY = (float) (Math.PI * 3.0 / 2.0);
      this.boatSides[2].rotateAngleY = (float) (Math.PI / 2);
      this.boatSides[3].rotateAngleY = (float) Math.PI;
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      for (int var11 = 0; var11 < 5; var11++) {
         this.boatSides[var11].render(float7);
      }
   }
}
