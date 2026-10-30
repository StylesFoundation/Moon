package net.minecraft.entity.projectile;

import java.util.List;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityThrowable extends Entity implements IProjectile {
   private int xTile = -1;
   private int yTile = -1;
   private int zTile = -1;
   private Block inTile;
   protected boolean inGround;
   public int throwableShake;
   private EntityLivingBase thrower;
   private String throwerName;
   private int ticksInGround;
   private int ticksInAir;

   public EntityThrowable(World world) {
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

   public EntityThrowable(World world, EntityLivingBase entityLivingBase) {
      super(world);
      this.thrower = entityLivingBase;
      this.setSize(0.25F, 0.25F);
      this.setLocationAndAngles(
         entityLivingBase.posX,
         entityLivingBase.posY + entityLivingBase.getEyeHeight(),
         entityLivingBase.posZ,
         entityLivingBase.rotationYaw,
         entityLivingBase.rotationPitch
      );
      this.posX = this.posX - MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
      this.posY -= 0.1F;
      this.posZ = this.posZ - MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
      this.setPosition(this.posX, this.posY, this.posZ);
      this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * 0.4F;
      this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * 0.4F;
      this.motionY = -MathHelper.sin((this.rotationPitch + this.getInaccuracy()) / 180.0F * (float) Math.PI) * 0.4F;
      this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.getVelocity(), 1.0F);
   }

   public EntityThrowable(World world, double double2, double double3, double double4) {
      super(world);
      this.ticksInGround = 0;
      this.setSize(0.25F, 0.25F);
      this.setPosition(double2, double3, double4);
   }

   protected float getVelocity() {
      return 1.5F;
   }

   protected float getInaccuracy() {
      return 0.0F;
   }

   @Override
   public void setThrowableHeading(double double1, double double2, double double3, float float4, float float5) {
      float var9 = MathHelper.sqrt_double(double1 * double1 + double2 * double2 + double3 * double3);
      double1 /= var9;
      double2 /= var9;
      double3 /= var9;
      double1 += this.rand.nextGaussian() * 0.0075F * float5;
      double2 += this.rand.nextGaussian() * 0.0075F * float5;
      double3 += this.rand.nextGaussian() * 0.0075F * float5;
      double1 *= float4;
      double2 *= float4;
      double3 *= float4;
      this.motionX = double1;
      this.motionY = double2;
      this.motionZ = double3;
      float var10 = MathHelper.sqrt_double(double1 * double1 + double3 * double3);
      this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(double1, double3) * 180.0 / Math.PI);
      this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(double2, var10) * 180.0 / Math.PI);
      this.ticksInGround = 0;
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
      if (this.throwableShake > 0) {
         this.throwableShake--;
      }

      if (this.inGround) {
         if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
            this.ticksInGround++;
            if (this.ticksInGround == 1200) {
               this.setDead();
            }

            return;
         }

         this.inGround = false;
         this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
         this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
         this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
         this.ticksInGround = 0;
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

      if (!this.worldObj.isRemote) {
         Entity var7 = null;
         List<Entity> var8 = this.worldObj
            .getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
         double var9 = 0.0;
         EntityLivingBase var11 = this.getThrower();

         for (Entity var13 : var8) {
            if (var13.canBeCollidedWith() && (var13 != var11 || this.ticksInAir >= 5)) {
               AxisAlignedBB var15 = var13.getEntityBoundingBox().expand(0.3F, 0.3F, 0.3F);
               MovingObjectPosition var16 = var15.calculateIntercept(var4, var5);
               if (var16 != null) {
                  double var17 = var4.squareDistanceTo(var16.hitVec);
                  if (var17 < var9 || var9 == 0.0) {
                     var7 = var13;
                     var9 = var17;
                  }
               }
            }
         }

         if (var7 != null) {
            var6 = new MovingObjectPosition(var7);
         }
      }

      if (var6 != null) {
         if (var6.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlockState(var6.getBlockPos()).getBlock() == Blocks.portal) {
            this.setPortal(var6.getBlockPos());
         } else {
            this.onImpact(var6);
         }
      }

      this.posX = this.posX + this.motionX;
      this.posY = this.posY + this.motionY;
      this.posZ = this.posZ + this.motionZ;
      float var21 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
      this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
      this.rotationPitch = (float)(MathHelper.atan2(this.motionY, var21) * 180.0 / Math.PI);

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
      float var22 = 0.99F;
      float var23 = this.getGravityVelocity();
      if (this.isInWater()) {
         for (int var10 = 0; var10 < 4; var10++) {
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

         var22 = 0.8F;
      }

      this.motionX *= var22;
      this.motionY *= var22;
      this.motionZ *= var22;
      this.motionY -= var23;
      this.setPosition(this.posX, this.posY, this.posZ);
   }

   protected float getGravityVelocity() {
      return 0.03F;
   }

   protected abstract void onImpact(MovingObjectPosition movingObjectPosition);

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setShort("xTile", (short)this.xTile);
      nBTTagCompound.setShort("yTile", (short)this.yTile);
      nBTTagCompound.setShort("zTile", (short)this.zTile);
      ResourceLocation var5 = Block.blockRegistry.getNameForObject(this.inTile);
      nBTTagCompound.setString("inTile", var5 == null ? "" : var5.toString());
      nBTTagCompound.setByte("shake", (byte)this.throwableShake);
      nBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
      if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower instanceof EntityPlayer) {
         this.throwerName = this.thrower.getName();
      }

      nBTTagCompound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
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

      this.throwableShake = nBTTagCompound.getByte("shake") & 255;
      this.inGround = nBTTagCompound.getByte("inGround") == 1;
      this.thrower = null;
      this.throwerName = nBTTagCompound.getString("ownerName");
      if (this.throwerName != null && this.throwerName.length() == 0) {
         this.throwerName = null;
      }

      this.thrower = this.getThrower();
   }

   public EntityLivingBase getThrower() {
      if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
         this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
         if (this.thrower == null && this.worldObj instanceof WorldServer) {
            try {
               Entity var4 = ((WorldServer)this.worldObj).getEntityFromUuid(UUID.fromString(this.throwerName));
               if (var4 instanceof EntityLivingBase) {
                  this.thrower = (EntityLivingBase)var4;
               }
            } catch (Throwable var5) {
               this.thrower = null;
            }
         }
      }

      return this.thrower;
   }
}
