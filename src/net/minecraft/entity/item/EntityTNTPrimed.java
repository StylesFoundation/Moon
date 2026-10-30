package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityTNTPrimed extends Entity {
   public int fuse;
   private EntityLivingBase tntPlacedBy;

   public EntityTNTPrimed(World world) {
      super(world);
      this.preventEntitySpawning = true;
      this.setSize(0.98F, 0.98F);
   }

   public EntityTNTPrimed(World world, double double2, double double3, double double4, EntityLivingBase entityLivingBase) {
      this(world);
      this.setPosition(double2, double3, double4);
      float var11 = (float)(Math.random() * Math.PI * 2.0);
      this.motionX = -((float)Math.sin(var11)) * 0.02F;
      this.motionY = 0.2F;
      this.motionZ = -((float)Math.cos(var11)) * 0.02F;
      this.fuse = 80;
      this.prevPosX = double2;
      this.prevPosY = double3;
      this.prevPosZ = double4;
      this.tntPlacedBy = entityLivingBase;
   }

   @Override
   protected void entityInit() {
   }

   @Override
   protected boolean canTriggerWalking() {
      return false;
   }

   @Override
   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   @Override
   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.motionY -= 0.04F;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.98F;
      this.motionY *= 0.98F;
      this.motionZ *= 0.98F;
      if (this.onGround) {
         this.motionX *= 0.7F;
         this.motionZ *= 0.7F;
         this.motionY *= -0.5;
      }

      if (this.fuse-- <= 0) {
         this.setDead();
         if (!this.worldObj.isRemote) {
            this.explode();
         }
      } else {
         this.handleWaterMovement();
         this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
      }
   }

   private void explode() {
      this.worldObj.createExplosion(this, this.posX, this.posY + this.height / 16.0F, this.posZ, 4.0F, true);
   }

   @Override
   protected void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setByte("Fuse", (byte)this.fuse);
   }

   @Override
   protected void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      this.fuse = nBTTagCompound.getByte("Fuse");
   }

   public EntityLivingBase getTntPlacedBy() {
      return this.tntPlacedBy;
   }

   @Override
   public float getEyeHeight() {
      return 0.0F;
   }
}
