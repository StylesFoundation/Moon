package net.minecraft.entity.projectile;

import java.util.List;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityArrow extends Entity implements IProjectile {
   private int xTile = -1;
   private int yTile = -1;
   private int zTile = -1;
   private Block inTile;
   private int inData;
   private boolean inGround;
   public int canBePickedUp;
   public int arrowShake;
   public Entity shootingEntity;
   private int ticksInGround;
   private int ticksInAir;
   private double damage = 2.0;
   private int knockbackStrength;

   public EntityArrow(World world) {
      super(world);
      this.renderDistanceWeight = 10.0;
      this.setSize(0.5F, 0.5F);
   }

   public EntityArrow(World world, double double2, double double3, double double4) {
      super(world);
      this.renderDistanceWeight = 10.0;
      this.setSize(0.5F, 0.5F);
      this.setPosition(double2, double3, double4);
   }

   public EntityArrow(World world, EntityLivingBase entityLivingBase2, EntityLivingBase entityLivingBase3, float float4, float float5) {
      super(world);
      this.renderDistanceWeight = 10.0;
      this.shootingEntity = entityLivingBase2;
      if (entityLivingBase2 instanceof EntityPlayer) {
         this.canBePickedUp = 1;
      }

      this.posY = entityLivingBase2.posY + entityLivingBase2.getEyeHeight() - 0.1F;
      double var9 = entityLivingBase3.posX - entityLivingBase2.posX;
      double var11 = entityLivingBase3.getEntityBoundingBox().minY + entityLivingBase3.height / 3.0F - this.posY;
      double var13 = entityLivingBase3.posZ - entityLivingBase2.posZ;
      double var15 = MathHelper.sqrt_double(var9 * var9 + var13 * var13);
      if (var15 >= 1.0E-7) {
         float var17 = (float)(MathHelper.atan2(var13, var9) * 180.0 / Math.PI) - 90.0F;
         float var18 = (float)(-(MathHelper.atan2(var11, var15) * 180.0 / Math.PI));
         double var19 = var9 / var15;
         double var21 = var13 / var15;
         this.setLocationAndAngles(entityLivingBase2.posX + var19, this.posY, entityLivingBase2.posZ + var21, var17, var18);
         float var23 = (float)(var15 * 0.2F);
         this.setThrowableHeading(var9, var11 + var23, var13, float4, float5);
      }
   }

   public EntityArrow(World world, EntityLivingBase entityLivingBase, float float3) {
      super(world);
      this.renderDistanceWeight = 10.0;
      this.shootingEntity = entityLivingBase;
      if (entityLivingBase instanceof EntityPlayer) {
         this.canBePickedUp = 1;
      }

      this.setSize(0.5F, 0.5F);
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
      this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
      this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
      this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI);
      this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, float3 * 1.5F, 1.0F);
   }

   @Override
   protected void entityInit() {
      this.dataWatcher.addObject(16, (byte)0);
   }

   @Override
   public void setThrowableHeading(double double1, double double2, double double3, float float4, float float5) {
      float var12 = MathHelper.sqrt_double(double1 * double1 + double2 * double2 + double3 * double3);
      double1 /= var12;
      double2 /= var12;
      double3 /= var12;
      double1 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.0075F * float5;
      double2 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.0075F * float5;
      double3 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.0075F * float5;
      double1 *= float4;
      double2 *= float4;
      double3 *= float4;
      this.motionX = double1;
      this.motionY = double2;
      this.motionZ = double3;
      float var13 = MathHelper.sqrt_double(double1 * double1 + double3 * double3);
      this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(double1, double3) * 180.0 / Math.PI);
      this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(double2, var13) * 180.0 / Math.PI);
      this.ticksInGround = 0;
   }

   @Override
   public void setPositionAndRotation2(double double1, double double2, double double3, float float4, float float5, int integer, boolean boolean7) {
      this.setPosition(double1, double2, double3);
      this.setRotation(float4, float5);
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
         this.prevRotationPitch = this.rotationPitch;
         this.prevRotationYaw = this.rotationYaw;
         this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
         this.ticksInGround = 0;
      }
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
         float var4 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
         this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(this.motionY, var4) * 180.0 / Math.PI);
      }

      BlockPos var21 = new BlockPos(this.xTile, this.yTile, this.zTile);
      IBlockState var5 = this.worldObj.getBlockState(var21);
      Block var6 = var5.getBlock();
      if (var6.getMaterial() != Material.air) {
         var6.setBlockBoundsBasedOnState(this.worldObj, var21);
         AxisAlignedBB var7 = var6.getCollisionBoundingBox(this.worldObj, var21, var5);
         if (var7 != null && var7.isVecInside(new Vec3(this.posX, this.posY, this.posZ))) {
            this.inGround = true;
         }
      }

      if (this.arrowShake > 0) {
         this.arrowShake--;
      }

      if (this.inGround) {
         int var22 = var6.getMetaFromState(var5);
         if (var6 == this.inTile && var22 == this.inData) {
            this.ticksInGround++;
            if (this.ticksInGround >= 1200) {
               this.setDead();
            }
         } else {
            this.inGround = false;
            this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
            this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
            this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
            this.ticksInGround = 0;
            this.ticksInAir = 0;
         }
      } else {
         this.ticksInAir++;
         Vec3 var23 = new Vec3(this.posX, this.posY, this.posZ);
         Vec3 var8 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         MovingObjectPosition var9 = this.worldObj.rayTraceBlocks(var23, var8, false, true, false);
         var23 = new Vec3(this.posX, this.posY, this.posZ);
         var8 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         if (var9 != null) {
            var8 = new Vec3(var9.hitVec.xCoord, var9.hitVec.yCoord, var9.hitVec.zCoord);
         }

         Entity var10 = null;
         List<Entity> var11 = this.worldObj
            .getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
         double var12 = 0.0;

         for (Entity var15 : var11) {
            if (var15.canBeCollidedWith() && (var15 != this.shootingEntity || this.ticksInAir >= 5)) {
               AxisAlignedBB var17 = var15.getEntityBoundingBox().expand(0.3F, 0.3F, 0.3F);
               MovingObjectPosition var18 = var17.calculateIntercept(var23, var8);
               if (var18 != null) {
                  double var19 = var23.squareDistanceTo(var18.hitVec);
                  if (var19 < var12 || var12 == 0.0) {
                     var10 = var15;
                     var12 = var19;
                  }
               }
            }
         }

         if (var10 != null) {
            var9 = new MovingObjectPosition(var10);
         }

         if (var9 != null
            && var9.entityHit != null
            && var9.entityHit instanceof EntityPlayer var26
            && (var26.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(var26))
            )
          {
            var9 = null;
         }

         if (var9 != null) {
            if (var9.entityHit != null) {
               int var31 = MathHelper.ceiling_double_int(
                  MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ) * this.damage
               );
               if (this.getIsCritical()) {
                  var31 += this.rand.nextInt(var31 / 2 + 2);
               }

               DamageSource var16 = DamageSource.causeArrowDamage(this, Objects.requireNonNullElse(this.shootingEntity, this));
               if (this.isBurning() && !(var9.entityHit instanceof EntityEnderman)) {
                  var9.entityHit.setFire(5);
               }

               if (var9.entityHit.attackEntityFrom(var16, var31)) {
                  if (var9.entityHit instanceof EntityLivingBase var35) {
                     if (!this.worldObj.isRemote) {
                        var35.setArrowCountInEntity(var35.getArrowCountInEntity() + 1);
                     }

                     if (this.knockbackStrength > 0) {
                        float var38 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                        if (var38 > 0.0F) {
                           var9.entityHit
                              .addVelocity(this.motionX * this.knockbackStrength * 0.6F / var38, 0.1, this.motionZ * this.knockbackStrength * 0.6F / var38);
                        }
                     }

                     if (this.shootingEntity instanceof EntityLivingBase) {
                        EnchantmentHelper.applyThornEnchantments(var35, this.shootingEntity);
                        EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, var35);
                     }

                     if (this.shootingEntity != null
                        && var9.entityHit != this.shootingEntity
                        && var9.entityHit instanceof EntityPlayer
                        && this.shootingEntity instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
                     }
                  }

                  this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                  if (!(var9.entityHit instanceof EntityEnderman)) {
                     this.setDead();
                  }
               } else {
                  this.motionX *= -0.1F;
                  this.motionY *= -0.1F;
                  this.motionZ *= -0.1F;
                  this.rotationYaw += 180.0F;
                  this.prevRotationYaw += 180.0F;
                  this.ticksInAir = 0;
               }
            } else {
               BlockPos var27 = var9.getBlockPos();
               this.xTile = var27.getX();
               this.yTile = var27.getY();
               this.zTile = var27.getZ();
               IBlockState var32 = this.worldObj.getBlockState(var27);
               this.inTile = var32.getBlock();
               this.inData = this.inTile.getMetaFromState(var32);
               this.motionX = (float)(var9.hitVec.xCoord - this.posX);
               this.motionY = (float)(var9.hitVec.yCoord - this.posY);
               this.motionZ = (float)(var9.hitVec.zCoord - this.posZ);
               float var34 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
               this.posX = this.posX - this.motionX / var34 * 0.05F;
               this.posY = this.posY - this.motionY / var34 * 0.05F;
               this.posZ = this.posZ - this.motionZ / var34 * 0.05F;
               this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
               this.inGround = true;
               this.arrowShake = 7;
               this.setIsCritical(false);
               if (this.inTile.getMaterial() != Material.air) {
                  this.inTile.onEntityCollidedWithBlock(this.worldObj, var27, var32, this);
               }
            }
         }

         if (this.getIsCritical()) {
            for (int var28 = 0; var28 < 4; var28++) {
               this.worldObj
                  .spawnParticle(
                     EnumParticleTypes.CRIT,
                     this.posX + this.motionX * var28 / 4.0,
                     this.posY + this.motionY * var28 / 4.0,
                     this.posZ + this.motionZ * var28 / 4.0,
                     -this.motionX,
                     -this.motionY + 0.2,
                     -this.motionZ
                  );
            }
         }

         this.posX = this.posX + this.motionX;
         this.posY = this.posY + this.motionY;
         this.posZ = this.posZ + this.motionZ;
         float var29 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
         this.rotationPitch = (float)(MathHelper.atan2(this.motionY, var29) * 180.0 / Math.PI);

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
         float var33 = 0.99F;
         if (this.isInWater()) {
            for (int var36 = 0; var36 < 4; var36++) {
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

            var33 = 0.6F;
         }

         if (this.isWet()) {
            this.extinguish();
         }

         this.motionX *= var33;
         this.motionY *= var33;
         this.motionZ *= var33;
         this.motionY -= 0.05F;
         this.setPosition(this.posX, this.posY, this.posZ);
         this.doBlockCollisions();
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setShort("xTile", (short)this.xTile);
      nBTTagCompound.setShort("yTile", (short)this.yTile);
      nBTTagCompound.setShort("zTile", (short)this.zTile);
      nBTTagCompound.setShort("life", (short)this.ticksInGround);
      ResourceLocation var5 = Block.blockRegistry.getNameForObject(this.inTile);
      nBTTagCompound.setString("inTile", var5 == null ? "" : var5.toString());
      nBTTagCompound.setByte("inData", (byte)this.inData);
      nBTTagCompound.setByte("shake", (byte)this.arrowShake);
      nBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
      nBTTagCompound.setByte("pickup", (byte)this.canBePickedUp);
      nBTTagCompound.setDouble("damage", this.damage);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      this.xTile = nBTTagCompound.getShort("xTile");
      this.yTile = nBTTagCompound.getShort("yTile");
      this.zTile = nBTTagCompound.getShort("zTile");
      this.ticksInGround = nBTTagCompound.getShort("life");
      if (nBTTagCompound.hasKey("inTile", 8)) {
         this.inTile = Block.getBlockFromName(nBTTagCompound.getString("inTile"));
      } else {
         this.inTile = Block.getBlockById(nBTTagCompound.getByte("inTile") & 255);
      }

      this.inData = nBTTagCompound.getByte("inData") & 255;
      this.arrowShake = nBTTagCompound.getByte("shake") & 255;
      this.inGround = nBTTagCompound.getByte("inGround") == 1;
      if (nBTTagCompound.hasKey("damage", 99)) {
         this.damage = nBTTagCompound.getDouble("damage");
      }

      if (nBTTagCompound.hasKey("pickup", 99)) {
         this.canBePickedUp = nBTTagCompound.getByte("pickup");
      } else if (nBTTagCompound.hasKey("player", 99)) {
         this.canBePickedUp = nBTTagCompound.getBoolean("player") ? 1 : 0;
      }
   }

   @Override
   public void onCollideWithPlayer(EntityPlayer entityPlayer) {
      if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
         boolean var5 = this.canBePickedUp == 1 || this.canBePickedUp == 2 && entityPlayer.capabilities.isCreativeMode;
         if (this.canBePickedUp == 1 && !entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.arrow, 1))) {
            var5 = false;
         }

         if (var5) {
            this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityPlayer.onItemPickup(this, 1);
            this.setDead();
         }
      }
   }

   @Override
   protected boolean canTriggerWalking() {
      return false;
   }

   public void setDamage(double double1) {
      this.damage = double1;
   }

   public double getDamage() {
      return this.damage;
   }

   public void setKnockbackStrength(int integer) {
      this.knockbackStrength = integer;
   }

   @Override
   public boolean canAttackWithItem() {
      return false;
   }

   @Override
   public float getEyeHeight() {
      return 0.0F;
   }

   public void setIsCritical(boolean boolean1) {
      byte var5 = this.dataWatcher.getWatchableObjectByte(16);
      if (boolean1) {
         this.dataWatcher.updateObject(16, (byte)(var5 | 1));
      } else {
         this.dataWatcher.updateObject(16, (byte)(var5 & -2));
      }
   }

   public boolean getIsCritical() {
      byte var4 = this.dataWatcher.getWatchableObjectByte(16);
      return (var4 & 1) != 0;
   }
}
