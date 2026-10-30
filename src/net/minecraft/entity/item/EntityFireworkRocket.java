package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFireworkRocket extends Entity {
   private int fireworkAge;
   private int lifetime;

   public EntityFireworkRocket(World world) {
      super(world);
      this.setSize(0.25F, 0.25F);
   }

   @Override
   protected void entityInit() {
      this.dataWatcher.addObjectByDataType(8, 5);
   }

   @Override
   public boolean isInRangeToRenderDist(double double1) {
      return double1 < 4096.0;
   }

   public EntityFireworkRocket(World world, double double2, double double3, double double4, ItemStack itemStack) {
      super(world);
      this.fireworkAge = 0;
      this.setSize(0.25F, 0.25F);
      this.setPosition(double2, double3, double4);
      int var12 = 1;
      if (itemStack != null && itemStack.hasTagCompound()) {
         this.dataWatcher.updateObject(8, itemStack);
         NBTTagCompound var14 = itemStack.getTagCompound().getCompoundTag("Fireworks");
         if (var14 != null) {
            var12 = 1 + var14.getByte("Flight");
         }
      }

      this.motionX = this.rand.nextGaussian() * 0.001;
      this.motionZ = this.rand.nextGaussian() * 0.001;
      this.motionY = 0.05;
      this.lifetime = 10 * var12 + this.rand.nextInt(6) + this.rand.nextInt(7);
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
      this.motionX *= 1.15;
      this.motionZ *= 1.15;
      this.motionY += 0.04;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
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
      if (this.fireworkAge == 0 && !this.isSilent()) {
         this.worldObj.playSoundAtEntity(this, "fireworks.launch", 3.0F, 1.0F);
      }

      this.fireworkAge++;
      if (this.worldObj.isRemote && this.fireworkAge % 2 < 2) {
         this.worldObj
            .spawnParticle(
               EnumParticleTypes.FIREWORKS_SPARK,
               this.posX,
               this.posY - 0.3,
               this.posZ,
               this.rand.nextGaussian() * 0.05,
               -this.motionY * 0.5,
               this.rand.nextGaussian() * 0.05
            );
      }

      if (!this.worldObj.isRemote && this.fireworkAge > this.lifetime) {
         this.worldObj.setEntityState(this, (byte)17);
         this.setDead();
      }
   }

   @Override
   public void handleStatusUpdate(byte byte1) {
      if (byte1 == 17 && this.worldObj.isRemote) {
         ItemStack var5 = this.dataWatcher.getWatchableObjectItemStack(8);
         NBTTagCompound var6 = null;
         if (var5 != null && var5.hasTagCompound()) {
            var6 = var5.getTagCompound().getCompoundTag("Fireworks");
         }

         this.worldObj.makeFireworks(this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, var6);
      }

      super.handleStatusUpdate(byte1);
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setInteger("Life", this.fireworkAge);
      nBTTagCompound.setInteger("LifeTime", this.lifetime);
      ItemStack var4 = this.dataWatcher.getWatchableObjectItemStack(8);
      if (var4 != null) {
         NBTTagCompound var5 = new NBTTagCompound();
         var4.writeToNBT(var5);
         nBTTagCompound.setTag("FireworksItem", var5);
      }
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      this.fireworkAge = nBTTagCompound.getInteger("Life");
      this.lifetime = nBTTagCompound.getInteger("LifeTime");
      NBTTagCompound var5 = nBTTagCompound.getCompoundTag("FireworksItem");
      if (var5 != null) {
         ItemStack var6 = ItemStack.loadItemStackFromNBT(var5);
         if (var6 != null) {
            this.dataWatcher.updateObject(8, var6);
         }
      }
   }

   @Override
   public float getBrightness(float float1) {
      return super.getBrightness(float1);
   }

   @Override
   public int getBrightnessForRender(float float1) {
      return super.getBrightnessForRender(float1);
   }

   @Override
   public boolean canAttackWithItem() {
      return false;
   }
}
