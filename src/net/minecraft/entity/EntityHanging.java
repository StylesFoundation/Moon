package net.minecraft.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

public abstract class EntityHanging extends Entity {
   private int tickCounter1;
   protected BlockPos hangingPosition;
   public EnumFacing facingDirection;

   public EntityHanging(World world) {
      super(world);
      this.setSize(0.5F, 0.5F);
   }

   public EntityHanging(World world, BlockPos blockPos) {
      this(world);
      this.hangingPosition = blockPos;
   }

   @Override
   protected void entityInit() {
   }

   protected void updateFacingWithBoundingBox(EnumFacing enumFacing) {
      Validate.notNull(enumFacing);
      Validate.isTrue(enumFacing.getAxis().isHorizontal());
      this.facingDirection = enumFacing;
      this.prevRotationYaw = this.rotationYaw = this.facingDirection.getHorizontalIndex() * 90;
      this.updateBoundingBox();
   }

   private void updateBoundingBox() {
      if (this.facingDirection != null) {
         double var4 = this.hangingPosition.getX() + 0.5;
         double var6 = this.hangingPosition.getY() + 0.5;
         double var8 = this.hangingPosition.getZ() + 0.5;
         double var12 = this.func_174858_a(this.getWidthPixels());
         double var14 = this.func_174858_a(this.getHeightPixels());
         var4 -= this.facingDirection.getFrontOffsetX() * 0.46875;
         var8 -= this.facingDirection.getFrontOffsetZ() * 0.46875;
         var6 += var14;
         EnumFacing var16 = this.facingDirection.rotateYCCW();
         var4 += var12 * var16.getFrontOffsetX();
         var8 += var12 * var16.getFrontOffsetZ();
         this.posX = var4;
         this.posY = var6;
         this.posZ = var8;
         double var17 = this.getWidthPixels();
         double var19 = this.getHeightPixels();
         double var21 = this.getWidthPixels();
         if (this.facingDirection.getAxis() == EnumFacing.Axis.Z) {
            var21 = 1.0;
         } else {
            var17 = 1.0;
         }

         var17 /= 32.0;
         var19 /= 32.0;
         var21 /= 32.0;
         this.setEntityBoundingBox(new AxisAlignedBB(var4 - var17, var6 - var19, var8 - var21, var4 + var17, var6 + var19, var8 + var21));
      }
   }

   private double func_174858_a(int integer) {
      return integer % 32 == 0 ? 0.5 : 0.0;
   }

   @Override
   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.tickCounter1++ == 100 && !this.worldObj.isRemote) {
         this.tickCounter1 = 0;
         if (!this.isDead && !this.onValidSurface()) {
            this.setDead();
            this.onBroken(null);
         }
      }
   }

   public boolean onValidSurface() {
      if (!this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
         return false;
      }

      int var4 = Math.max(1, this.getWidthPixels() / 16);
      int var5 = Math.max(1, this.getHeightPixels() / 16);
      BlockPos var6 = this.hangingPosition.offset(this.facingDirection.getOpposite());
      EnumFacing var7 = this.facingDirection.rotateYCCW();

      for (int var8 = 0; var8 < var4; var8++) {
         for (int var9 = 0; var9 < var5; var9++) {
            BlockPos var10 = var6.offset(var7, var8).up(var9);
            Block var11 = this.worldObj.getBlockState(var10).getBlock();
            if (!var11.getMaterial().isSolid() && !BlockRedstoneDiode.isRedstoneRepeaterBlockID(var11)) {
               return false;
            }
         }
      }

      for (Entity var13 : this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox())) {
         if (var13 instanceof EntityHanging) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean canBeCollidedWith() {
      return true;
   }

   @Override
   public boolean hitByEntity(Entity entity) {
      return entity instanceof EntityPlayer && this.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entity), 0.0F);
   }

   @Override
   public EnumFacing getHorizontalFacing() {
      return this.facingDirection;
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      }

      if (!this.isDead && !this.worldObj.isRemote) {
         this.setDead();
         this.setBeenAttacked();
         this.onBroken(damageSource.getEntity());
      }

      return true;
   }

   @Override
   public void moveEntity(double double1, double double2, double double3) {
      if (!this.worldObj.isRemote && !this.isDead && double1 * double1 + double2 * double2 + double3 * double3 > 0.0) {
         this.setDead();
         this.onBroken(null);
      }
   }

   @Override
   public void addVelocity(double double1, double double2, double double3) {
      if (!this.worldObj.isRemote && !this.isDead && double1 * double1 + double2 * double2 + double3 * double3 > 0.0) {
         this.setDead();
         this.onBroken(null);
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setByte("Facing", (byte)this.facingDirection.getHorizontalIndex());
      nBTTagCompound.setInteger("TileX", this.getHangingPosition().getX());
      nBTTagCompound.setInteger("TileY", this.getHangingPosition().getY());
      nBTTagCompound.setInteger("TileZ", this.getHangingPosition().getZ());
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      this.hangingPosition = new BlockPos(nBTTagCompound.getInteger("TileX"), nBTTagCompound.getInteger("TileY"), nBTTagCompound.getInteger("TileZ"));
      EnumFacing var5;
      if (nBTTagCompound.hasKey("Direction", 99)) {
         var5 = EnumFacing.getHorizontal(nBTTagCompound.getByte("Direction"));
         this.hangingPosition = this.hangingPosition.offset(var5);
      } else if (nBTTagCompound.hasKey("Facing", 99)) {
         var5 = EnumFacing.getHorizontal(nBTTagCompound.getByte("Facing"));
      } else {
         var5 = EnumFacing.getHorizontal(nBTTagCompound.getByte("Dir"));
      }

      this.updateFacingWithBoundingBox(var5);
   }

   public abstract int getWidthPixels();

   public abstract int getHeightPixels();

   public abstract void onBroken(Entity entity);

   @Override
   protected boolean shouldSetPosAfterLoading() {
      return false;
   }

   @Override
   public void setPosition(double double1, double double2, double double3) {
      this.posX = double1;
      this.posY = double2;
      this.posZ = double3;
      BlockPos var10 = this.hangingPosition;
      this.hangingPosition = new BlockPos(double1, double2, double3);
      if (!this.hangingPosition.equals(var10)) {
         this.updateBoundingBox();
         this.isAirBorne = true;
      }
   }

   public BlockPos getHangingPosition() {
      return this.hangingPosition;
   }
}
