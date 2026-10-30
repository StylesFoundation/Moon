package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityFireball extends Entity {
   private int xTile = -1;
   private int yTile = -1;
   private int zTile = -1;
   private Block inTile;
   private boolean inGround;
   public EntityLivingBase shootingEntity;
   private int ticksAlive;
   private int ticksInAir;
   public double accelerationX;
   public double accelerationY;
   public double accelerationZ;

   public EntityFireball(World world) {
      super(world);
      this.setSize(1.0F, 1.0F);
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

   public EntityFireball(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      super(world);
      this.setSize(1.0F, 1.0F);
      this.setLocationAndAngles(double2, double3, double4, this.rotationYaw, this.rotationPitch);
      this.setPosition(double2, double3, double4);
      double var14 = MathHelper.sqrt_double(double5 * double5 + double6 * double6 + double7 * double7);
      this.accelerationX = double5 / var14 * 0.1;
      this.accelerationY = double6 / var14 * 0.1;
      this.accelerationZ = double7 / var14 * 0.1;
   }

   public EntityFireball(World world, EntityLivingBase entityLivingBase, double double3, double double4, double double5) {
      super(world);
      this.shootingEntity = entityLivingBase;
      this.setSize(1.0F, 1.0F);
      this.setLocationAndAngles(
         entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, entityLivingBase.rotationYaw, entityLivingBase.rotationPitch
      );
      this.setPosition(this.posX, this.posY, this.posZ);
      this.motionX = this.motionY = this.motionZ = 0.0;
      double3 += this.rand.nextGaussian() * 0.4;
      double4 += this.rand.nextGaussian() * 0.4;
      double5 += this.rand.nextGaussian() * 0.4;
      double var9 = MathHelper.sqrt_double(double3 * double3 + double4 * double4 + double5 * double5);
      this.accelerationX = double3 / var9 * 0.1;
      this.accelerationY = double4 / var9 * 0.1;
      this.accelerationZ = double5 / var9 * 0.1;
   }

   @Override
   public void onUpdate() {
      if (this.worldObj.isRemote || (this.shootingEntity == null || !this.shootingEntity.isDead) && this.worldObj.Method322(new BlockPos(this))) {
         super.onUpdate();
         this.setFire(1);
         if (this.inGround) {
            if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
               this.ticksAlive++;
               if (this.ticksAlive == 600) {
                  this.setDead();
               }

               return;
            }

            this.inGround = false;
            this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
            this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
            this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
            this.ticksAlive = 0;
            this.ticksInAir = 0;
         } else {
            this.ticksInAir++;
         }

         Vec3 var4 = new Vec3(this.posX, this.posY, this.posZ);
         Vec3 var5 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         MovingObjectPosition var6 = this.worldObj.rayTraceBlocks(var4, var5);
         var4 = new Vec3(this.posX, this.posY, this.posZ);
         var5 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         if (var6 != null) {
            var5 = new Vec3(var6.hitVec.xCoord, var6.hitVec.yCoord, var6.hitVec.zCoord);
         }

         Entity var7 = null;
         List<Entity> var8 = this.worldObj
            .getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
         double var9 = 0.0;

         for (Entity var12 : var8) {
            if (var12.canBeCollidedWith() && (!var12.isEntityEqual(this.shootingEntity) || this.ticksInAir >= 25)) {
               AxisAlignedBB var14 = var12.getEntityBoundingBox().expand(0.3F, 0.3F, 0.3F);
               MovingObjectPosition var15 = var14.calculateIntercept(var4, var5);
               if (var15 != null) {
                  double var16 = var4.squareDistanceTo(var15.hitVec);
                  if (var16 < var9 || var9 == 0.0) {
                     var7 = var12;
                     var9 = var16;
                  }
               }
            }
         }

         if (var7 != null) {
            var6 = new MovingObjectPosition(var7);
         }

         if (var6 != null) {
            this.onImpact(var6);
         }

         this.posX = this.posX + this.motionX;
         this.posY = this.posY + this.motionY;
         this.posZ = this.posZ + this.motionZ;
         float var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.rotationYaw = (float)(MathHelper.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) + 90.0F;
         this.rotationPitch = (float)(MathHelper.atan2(var20, this.motionY) * 180.0 / Math.PI) - 90.0F;

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
         float var21 = this.getMotionFactor();
         if (this.isInWater()) {
            for (int var13 = 0; var13 < 4; var13++) {
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

            var21 = 0.8F;
         }

         this.motionX = this.motionX + this.accelerationX;
         this.motionY = this.motionY + this.accelerationY;
         this.motionZ = this.motionZ + this.accelerationZ;
         this.motionX *= var21;
         this.motionY *= var21;
         this.motionZ *= var21;
         this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
         this.setPosition(this.posX, this.posY, this.posZ);
      } else {
         this.setDead();
      }
   }

   protected float getMotionFactor() {
      return 0.95F;
   }

   protected abstract void onImpact(MovingObjectPosition movingObjectPosition);

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setShort("xTile", (short)this.xTile);
      nBTTagCompound.setShort("yTile", (short)this.yTile);
      nBTTagCompound.setShort("zTile", (short)this.zTile);
      ResourceLocation var5 = Block.blockRegistry.getNameForObject(this.inTile);
      nBTTagCompound.setString("inTile", var5 == null ? "" : var5.toString());
      nBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
      nBTTagCompound.setTag("direction", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      this.xTile = nBTTagCompound.getShort("xTile");
      this.yTile = nBTTagCompound.getShort("yTile");
      this.zTile = nBTTagCompound.getShort("zTile");
      if (nBTTagCompound.hasKey("inTile", 8)) {
         this.inTile = Block.getBlockFromName(nBTTagCompound.getString("inTile"));
      } else {
         this.inTile = Block.getBlockById(nBTTagCompound.getByte("inTile") & 255);
      }

      this.inGround = nBTTagCompound.getByte("inGround") == 1;
      if (nBTTagCompound.hasKey("direction", 9)) {
         NBTTagList var5 = nBTTagCompound.getTagList("direction", 6);
         this.motionX = var5.getDoubleAt(0);
         this.motionY = var5.getDoubleAt(1);
         this.motionZ = var5.getDoubleAt(2);
      } else {
         this.setDead();
      }
   }

   @Override
   public boolean canBeCollidedWith() {
      return true;
   }

   @Override
   public float getCollisionBorderSize() {
      return 1.0F;
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      }

      this.setBeenAttacked();
      if (damageSource.getEntity() != null) {
         Vec3 var6 = damageSource.getEntity().getLookVec();
         if (var6 != null) {
            this.motionX = var6.xCoord;
            this.motionY = var6.yCoord;
            this.motionZ = var6.zCoord;
            this.accelerationX = this.motionX * 0.1;
            this.accelerationY = this.motionY * 0.1;
            this.accelerationZ = this.motionZ * 0.1;
         }

         if (damageSource.getEntity() instanceof EntityLivingBase) {
            this.shootingEntity = (EntityLivingBase)damageSource.getEntity();
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public float getBrightness(float float1) {
      return 1.0F;
   }

   @Override
   public int getBrightnessForRender(float float1) {
      return 15728880;
   }
}
