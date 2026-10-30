package net.minecraft.entity;

import net.minecraft.block.BlockFence;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityLeashKnot extends EntityHanging {
   public EntityLeashKnot(World world) {
      super(world);
   }

   public EntityLeashKnot(World world, BlockPos blockPos) {
      super(world, blockPos);
      this.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
      this.setEntityBoundingBox(
         new AxisAlignedBB(this.posX - 0.1875, this.posY - 0.25 + 0.125, this.posZ - 0.1875, this.posX + 0.1875, this.posY + 0.25 + 0.125, this.posZ + 0.1875)
      );
   }

   @Override
   protected void entityInit() {
      super.entityInit();
   }

   @Override
   public void updateFacingWithBoundingBox(EnumFacing enumFacing) {
   }

   @Override
   public int getWidthPixels() {
      return 9;
   }

   @Override
   public int getHeightPixels() {
      return 9;
   }

   @Override
   public float getEyeHeight() {
      return -0.0625F;
   }

   @Override
   public boolean isInRangeToRenderDist(double double1) {
      return double1 < 1024.0;
   }

   @Override
   public void onBroken(Entity entity) {
   }

   @Override
   public boolean writeToNBTOptional(NBTTagCompound nBTTagCompound) {
      return false;
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
   }

   @Override
   public boolean interactFirst(EntityPlayer entityPlayer) {
      ItemStack var5 = entityPlayer.getHeldItem();
      boolean var6 = false;
      if (var5 != null && var5.getItem() == Items.lead && !this.worldObj.isRemote) {
         for (EntityLiving var10 : this.worldObj
            .getEntitiesWithinAABB(
               EntityLiving.class, new AxisAlignedBB(this.posX - 7.0, this.posY - 7.0, this.posZ - 7.0, this.posX + 7.0, this.posY + 7.0, this.posZ + 7.0)
            )) {
            if (var10.getLeashed() && var10.getLeashedToEntity() == entityPlayer) {
               var10.setLeashedToEntity(this, true);
               var6 = true;
            }
         }
      }

      if (!this.worldObj.isRemote && !var6) {
         this.setDead();
         if (entityPlayer.capabilities.isCreativeMode) {
            for (EntityLiving var12 : this.worldObj
               .getEntitiesWithinAABB(
                  EntityLiving.class, new AxisAlignedBB(this.posX - 7.0, this.posY - 7.0, this.posZ - 7.0, this.posX + 7.0, this.posY + 7.0, this.posZ + 7.0)
               )) {
               if (var12.getLeashed() && var12.getLeashedToEntity() == this) {
                  var12.clearLeashed(true, false);
               }
            }
         }
      }

      return true;
   }

   @Override
   public boolean onValidSurface() {
      return this.worldObj.getBlockState(this.hangingPosition).getBlock() instanceof BlockFence;
   }

   public static EntityLeashKnot createKnot(World world, BlockPos blockPos) {
      EntityLeashKnot var2 = new EntityLeashKnot(world, blockPos);
      var2.forceSpawn = true;
      world.spawnEntityInWorld(var2);
      return var2;
   }

   public static EntityLeashKnot getKnotForPosition(World world, BlockPos blockPos) {
      int var5 = blockPos.getX();
      int var6 = blockPos.getY();
      int var7 = blockPos.getZ();

      for (EntityLeashKnot var9 : world.getEntitiesWithinAABB(
         EntityLeashKnot.class, new AxisAlignedBB(var5 - 1.0, var6 - 1.0, var7 - 1.0, var5 + 1.0, var6 + 1.0, var7 + 1.0)
      )) {
         if (var9.getHangingPosition().equals(blockPos)) {
            return var9;
         }
      }

      return null;
   }
}
