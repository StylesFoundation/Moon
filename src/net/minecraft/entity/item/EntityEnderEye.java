package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityEnderEye extends Entity {
   private double targetX;
   private double targetY;
   private double targetZ;
   private int despawnTimer;
   private boolean shatterOrDrop;

   public EntityEnderEye(World world) {
      super(world);
      this.setSize(0.25F, 0.25F);
   }

   @Override
   protected void entityInit() {
   }

   @Override
   public boolean isInRangeToRenderDist(double double1) {
      double var6 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
      if (Double.isNaN(var6)) {
         var6 = 4.0;
      }

      var6 *= 64.0;
      return double1 < var6 * var6;
   }

   public EntityEnderEye(World world, double double2, double double3, double double4) {
      super(world);
      this.despawnTimer = 0;
      this.setSize(0.25F, 0.25F);
      this.setPosition(double2, double3, double4);
   }

   public void moveTowards(BlockPos blockPos) {
      double var5 = blockPos.getX();
      int var7 = blockPos.getY();
      double var8 = blockPos.getZ();
      double var10 = var5 - this.posX;
      double var12 = var8 - this.posZ;
      float var14 = MathHelper.sqrt_double(var10 * var10 + var12 * var12);
      if (var14 > 12.0F) {
         this.targetX = this.posX + var10 / var14 * 12.0;
         this.targetZ = this.posZ + var12 / var14 * 12.0;
         this.targetY = this.posY + 8.0;
      } else {
         this.targetX = var5;
         this.targetY = var7;
         this.targetZ = var8;
      }

      this.despawnTimer = 0;
      this.shatterOrDrop = this.rand.nextInt(5) > 0;
   }

   @Override
   public void setVelocity(double double1, double double2, double double3) {
      this.motionX = double1;
      this.motionY = double2;
      this.motionZ = double3;
      if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
         float var10 = MathHelper.sqrt_double(double1 * double1 + double3 * double3);
         this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(double1, double3) * 180.0 / Math.PI);
         this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(double2, var10) * 180.0 / Math.PI);
      }
   }

   @Override
   public void onUpdate() {
      this.lastTickPosX = this.posX;
      this.lastTickPosY = this.posY;
      this.lastTickPosZ = this.posZ;
      super.onUpdate();
      this.posX = this.posX + this.motionX;
      this.posY = this.posY + this.motionY;
      this.posZ = this.posZ + this.motionZ;
      float var4 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
      this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
      this.rotationPitch = (float)(MathHelper.atan2(this.motionY, var4) * 180.0 / Math.PI);

      while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
         this.prevRotationPitch -= 360.0F;
      }

      while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
         this.prevRotationPitch += 360.0F;
      }

      while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
         this.prevRotationYaw -= 360.0F;
      }

      while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
         this.prevRotationYaw += 360.0F;
      }

      this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
      this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
      if (!this.worldObj.isRemote) {
         double var5 = this.targetX - this.posX;
         double var7 = this.targetZ - this.posZ;
         float var9 = (float)Math.sqrt(var5 * var5 + var7 * var7);
         float var10 = (float)MathHelper.atan2(var7, var5);
         double var11 = var4 + (var9 - var4) * 0.0025;
         if (var9 < 1.0F) {
            var11 *= 0.8;
            this.motionY *= 0.8;
         }

         this.motionX = Math.cos(var10) * var11;
         this.motionZ = Math.sin(var10) * var11;
         if (this.posY < this.targetY) {
            this.motionY = this.motionY + (1.0 - this.motionY) * 0.015F;
         } else {
            this.motionY = this.motionY + (-1.0 - this.motionY) * 0.015F;
         }
      }

      if (this.isInWater()) {
         for (int var6 = 0; var6 < 4; var6++) {
            this.worldObj
               .spawnParticle(
                  EnumParticleTypes.WATER_BUBBLE,
                  this.posX - this.motionX * 0.25,
                  this.posY - this.motionY * 0.25,
                  this.posZ - this.motionZ * 0.25,
                  this.motionX,
                  this.motionY,
                  this.motionZ
               );
         }
      } else {
         this.worldObj
            .spawnParticle(
               EnumParticleTypes.PORTAL,
               this.posX - this.motionX * 0.25 + this.rand.nextDouble() * 0.6 - 0.3,
               this.posY - this.motionY * 0.25 - 0.5,
               this.posZ - this.motionZ * 0.25 + this.rand.nextDouble() * 0.6 - 0.3,
               this.motionX,
               this.motionY,
               this.motionZ
            );
      }

      if (!this.worldObj.isRemote) {
         this.setPosition(this.posX, this.posY, this.posZ);
         this.despawnTimer++;
         if (this.despawnTimer > 80 && !this.worldObj.isRemote) {
            this.setDead();
            if (this.shatterOrDrop) {
               this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.ender_eye)));
            } else {
               this.worldObj.playAuxSFX(2003, new BlockPos(this), 0);
            }
         }
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
   }

   @Override
   public float getBrightness(float float1) {
      return 1.0F;
   }

   @Override
   public int getBrightnessForRender(float float1) {
      return 15728880;
   }

   @Override
   public boolean canAttackWithItem() {
      return false;
   }
}
