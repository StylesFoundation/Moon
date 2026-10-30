package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

public class ModelArmorStandArmor extends ModelBiped {
   public ModelArmorStandArmor() {
      this(0.0F);
   }

   public ModelArmorStandArmor(float float1) {
      this(float1, 64, 32);
   }

   protected ModelArmorStandArmor(float float1, int integer2, int integer3) {
      super(float1, 0.0F, integer2, integer3);
   }

   @Override
   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
      if (entity instanceof EntityArmorStand var11) {
         this.bipedHead.rotateAngleX = (float) (Math.PI / 180.0) * var11.getHeadRotation().getX();
         this.bipedHead.rotateAngleY = (float) (Math.PI / 180.0) * var11.getHeadRotation().getY();
         this.bipedHead.rotateAngleZ = (float) (Math.PI / 180.0) * var11.getHeadRotation().getZ();
         this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
         this.bipedBody.rotateAngleX = (float) (Math.PI / 180.0) * var11.getBodyRotation().getX();
         this.bipedBody.rotateAngleY = (float) (Math.PI / 180.0) * var11.getBodyRotation().getY();
         this.bipedBody.rotateAngleZ = (float) (Math.PI / 180.0) * var11.getBodyRotation().getZ();
         this.bipedLeftArm.rotateAngleX = (float) (Math.PI / 180.0) * var11.getLeftArmRotation().getX();
         this.bipedLeftArm.rotateAngleY = (float) (Math.PI / 180.0) * var11.getLeftArmRotation().getY();
         this.bipedLeftArm.rotateAngleZ = (float) (Math.PI / 180.0) * var11.getLeftArmRotation().getZ();
         this.bipedRightArm.rotateAngleX = (float) (Math.PI / 180.0) * var11.getRightArmRotation().getX();
         this.bipedRightArm.rotateAngleY = (float) (Math.PI / 180.0) * var11.getRightArmRotation().getY();
         this.bipedRightArm.rotateAngleZ = (float) (Math.PI / 180.0) * var11.getRightArmRotation().getZ();
         this.bipedLeftLeg.rotateAngleX = (float) (Math.PI / 180.0) * var11.getLeftLegRotation().getX();
         this.bipedLeftLeg.rotateAngleY = (float) (Math.PI / 180.0) * var11.getLeftLegRotation().getY();
         this.bipedLeftLeg.rotateAngleZ = (float) (Math.PI / 180.0) * var11.getLeftLegRotation().getZ();
         this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
         this.bipedRightLeg.rotateAngleX = (float) (Math.PI / 180.0) * var11.getRightLegRotation().getX();
         this.bipedRightLeg.rotateAngleY = (float) (Math.PI / 180.0) * var11.getRightLegRotation().getY();
         this.bipedRightLeg.rotateAngleZ = (float) (Math.PI / 180.0) * var11.getRightLegRotation().getZ();
         this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
         copyModelAngles(this.bipedHead, this.bipedHeadwear);
      }
   }
}
