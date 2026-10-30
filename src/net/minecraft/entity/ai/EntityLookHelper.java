package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class EntityLookHelper {
   private final EntityLiving entity;
   private float deltaLookYaw;
   private float deltaLookPitch;
   private boolean isLooking;
   private double posX;
   private double posY;
   private double posZ;

   public EntityLookHelper(EntityLiving entityLiving) {
      this.entity = entityLiving;
   }

   public void setLookPositionWithEntity(Entity entity, float float2, float float3) {
      this.posX = entity.posX;
      if (entity instanceof EntityLivingBase) {
         this.posY = entity.posY + entity.getEyeHeight();
      } else {
         this.posY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0;
      }

      this.posZ = entity.posZ;
      this.deltaLookYaw = float2;
      this.deltaLookPitch = float3;
      this.isLooking = true;
   }

   public void setLookPosition(double double1, double double2, double double3, float float4, float float5) {
      this.posX = double1;
      this.posY = double2;
      this.posZ = double3;
      this.deltaLookYaw = float4;
      this.deltaLookPitch = float5;
      this.isLooking = true;
   }

   public void onUpdateLook() {
      this.entity.rotationPitch = 0.0F;
      if (this.isLooking) {
         this.isLooking = false;
         double var4 = this.posX - this.entity.posX;
         double var6 = this.posY - (this.entity.posY + this.entity.getEyeHeight());
         double var8 = this.posZ - this.entity.posZ;
         double var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
         float var12 = (float)(MathHelper.atan2(var8, var4) * 180.0 / Math.PI) - 90.0F;
         float var13 = (float)(-(MathHelper.atan2(var6, var10) * 180.0 / Math.PI));
         this.entity.rotationPitch = this.updateRotation(this.entity.rotationPitch, var13, this.deltaLookPitch);
         this.entity.rotationYawHead = this.updateRotation(this.entity.rotationYawHead, var12, this.deltaLookYaw);
      } else {
         this.entity.rotationYawHead = this.updateRotation(this.entity.rotationYawHead, this.entity.renderYawOffset, 10.0F);
      }

      float var14 = MathHelper.wrapAngleTo180_float(this.entity.rotationYawHead - this.entity.renderYawOffset);
      if (!this.entity.getNavigator().noPath()) {
         if (var14 < -75.0F) {
            this.entity.rotationYawHead = this.entity.renderYawOffset - 75.0F;
         }

         if (var14 > 75.0F) {
            this.entity.rotationYawHead = this.entity.renderYawOffset + 75.0F;
         }
      }
   }

   private float updateRotation(float float1, float float2, float float3) {
      float var7 = MathHelper.wrapAngleTo180_float(float2 - float1);
      if (var7 > float3) {
         var7 = float3;
      }

      if (var7 < -float3) {
         var7 = -float3;
      }

      return float1 + var7;
   }

   public boolean getIsLooking() {
      return this.isLooking;
   }

   public double Method685() {
      return this.posX;
   }

   public double Method686() {
      return this.posY;
   }

   public double Method687() {
      return this.posZ;
   }
}
