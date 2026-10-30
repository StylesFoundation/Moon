package net.minecraft.entity.passive;

import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBat extends EntityAmbientCreature {
   private BlockPos spawnPosition;

   public EntityBat(World world) {
      super(world);
      this.setSize(0.5F, 0.9F);
      this.setIsBatHanging(true);
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, (byte)0);
   }

   @Override
   protected float getSoundVolume() {
      return 0.1F;
   }

   @Override
   protected float getSoundPitch() {
      return super.getSoundPitch() * 0.95F;
   }

   @Override
   protected String getLivingSound() {
      return this.getIsBatHanging() && this.rand.nextInt(4) != 0 ? null : "mob.bat.idle";
   }

   @Override
   protected String getHurtSound() {
      return "mob.bat.hurt";
   }

   @Override
   protected String getDeathSound() {
      return "mob.bat.death";
   }

   @Override
   public boolean canBePushed() {
      return false;
   }

   @Override
   protected void collideWithEntity(Entity entity) {
   }

   @Override
   protected void collideWithNearbyEntities() {
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0);
   }

   public boolean getIsBatHanging() {
      return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
   }

   public void setIsBatHanging(boolean boolean1) {
      byte var5 = this.dataWatcher.getWatchableObjectByte(16);
      if (boolean1) {
         this.dataWatcher.updateObject(16, (byte)(var5 | 1));
      } else {
         this.dataWatcher.updateObject(16, (byte)(var5 & -2));
      }
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.getIsBatHanging()) {
         this.motionX = this.motionY = this.motionZ = 0.0;
         this.posY = MathHelper.floor_double(this.posY) + 1.0 - this.height;
      } else {
         this.motionY *= 0.6F;
      }
   }

   @Override
   protected void updateAITasks() {
      super.updateAITasks();
      BlockPos var4 = new BlockPos(this);
      BlockPos var5 = var4.up();
      if (this.getIsBatHanging()) {
         if (!this.worldObj.getBlockState(var5).getBlock().isNormalCube()) {
            this.setIsBatHanging(false);
            this.worldObj.playAuxSFXAtEntity(null, 1015, var4, 0);
         } else {
            if (this.rand.nextInt(200) == 0) {
               this.rotationYawHead = this.rand.nextInt(360);
            }

            if (this.worldObj.getClosestPlayerToEntity(this, 4.0) != null) {
               this.setIsBatHanging(false);
               this.worldObj.playAuxSFXAtEntity(null, 1015, var4, 0);
            }
         }
      } else {
         if (this.spawnPosition != null && (!this.worldObj.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
            this.spawnPosition = null;
         }

         if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0) {
            this.spawnPosition = new BlockPos(
               (int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7),
               (int)this.posY + this.rand.nextInt(6) - 2,
               (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7)
            );
         }

         double var6 = this.spawnPosition.getX() + 0.5 - this.posX;
         double var8 = this.spawnPosition.getY() + 0.1 - this.posY;
         double var10 = this.spawnPosition.getZ() + 0.5 - this.posZ;
         this.motionX = this.motionX + (Math.signum(var6) * 0.5 - this.motionX) * 0.1F;
         this.motionY = this.motionY + (Math.signum(var8) * 0.7F - this.motionY) * 0.1F;
         this.motionZ = this.motionZ + (Math.signum(var10) * 0.5 - this.motionZ) * 0.1F;
         float var13 = MathHelper.wrapAngleTo180_float((float)(MathHelper.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F - this.rotationYaw);
         this.moveForward = 0.5F;
         this.rotationYaw += var13;
         if (this.rand.nextInt(100) == 0 && this.worldObj.getBlockState(var5).getBlock().isNormalCube()) {
            this.setIsBatHanging(true);
         }
      }
   }

   @Override
   protected boolean canTriggerWalking() {
      return false;
   }

   @Override
   public void fall(float float1, float float2) {
   }

   @Override
   protected void updateFallState(double double1, boolean boolean2, Block block, BlockPos blockPos) {
   }

   @Override
   public boolean doesEntityNotTriggerPressurePlate() {
      return true;
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      }

      if (!this.worldObj.isRemote && this.getIsBatHanging()) {
         this.setIsBatHanging(false);
      }

      return super.attackEntityFrom(damageSource, float2);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      this.dataWatcher.updateObject(16, nBTTagCompound.getByte("BatFlags"));
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setByte("BatFlags", this.dataWatcher.getWatchableObjectByte(16));
   }

   @Override
   public boolean getCanSpawnHere() {
      BlockPos var4 = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
      if (var4.getY() >= this.worldObj.getSeaLevel()) {
         return false;
      }

      int var5 = this.worldObj.getLightFromNeighbors(var4);
      byte var6 = 4;
      if (this.isDateAroundHalloween(this.worldObj.getCurrentDate())) {
         var6 = 7;
      } else if (this.rand.nextBoolean()) {
         return false;
      }

      return var5 <= this.rand.nextInt(var6) && super.getCanSpawnHere();
   }

   private boolean isDateAroundHalloween(Calendar calendar) {
      return calendar.get(2) + 1 == 10 && calendar.get(5) >= 20 || calendar.get(2) + 1 == 11 && calendar.get(5) <= 3;
   }

   @Override
   public float getEyeHeight() {
      return this.height / 2.0F;
   }
}
