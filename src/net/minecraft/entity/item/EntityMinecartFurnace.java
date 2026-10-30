package net.minecraft.entity.item;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartFurnace extends EntityMinecart {
   private int fuel;
   public double pushX;
   public double pushZ;

   public EntityMinecartFurnace(World world) {
      super(world);
   }

   public EntityMinecartFurnace(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4);
   }

   @Override
   public EntityMinecart.EnumMinecartType getMinecartType() {
      return EntityMinecart.EnumMinecartType.FURNACE;
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, (byte)0);
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.fuel > 0) {
         this.fuel--;
      }

      if (this.fuel <= 0) {
         this.pushX = this.pushZ = 0.0;
      }

      this.setMinecartPowered(this.fuel > 0);
      if (this.isMinecartPowered() && this.rand.nextInt(4) == 0) {
         this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.8, this.posZ, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected double getMaximumSpeed() {
      return 0.2;
   }

   @Override
   public void killMinecart(DamageSource damageSource) {
      super.killMinecart(damageSource);
      if (!damageSource.isExplosion() && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         this.entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0F);
      }
   }

   @Override
   protected void func_180460_a(BlockPos blockPos, IBlockState iBlockState) {
      super.func_180460_a(blockPos, iBlockState);
      double var6 = this.pushX * this.pushX + this.pushZ * this.pushZ;
      if (var6 > 1.0E-4 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001) {
         var6 = MathHelper.sqrt_double(var6);
         this.pushX /= var6;
         this.pushZ /= var6;
         if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0) {
            this.pushX = 0.0;
            this.pushZ = 0.0;
         } else {
            double var8 = var6 / this.getMaximumSpeed();
            this.pushX *= var8;
            this.pushZ *= var8;
         }
      }
   }

   @Override
   protected void applyDrag() {
      double var4 = this.pushX * this.pushX + this.pushZ * this.pushZ;
      if (var4 > 1.0E-4) {
         var4 = MathHelper.sqrt_double(var4);
         this.pushX /= var4;
         this.pushZ /= var4;
         this.motionX *= 0.8F;
         this.motionY *= 0.0;
         this.motionZ *= 0.8F;
         this.motionX = this.motionX + this.pushX * 1.0;
         this.motionZ = this.motionZ + this.pushZ * 1.0;
      } else {
         this.motionX *= 0.98F;
         this.motionY *= 0.0;
         this.motionZ *= 0.98F;
      }

      super.applyDrag();
   }

   @Override
   public boolean interactFirst(EntityPlayer entityPlayer) {
      ItemStack var5 = entityPlayer.inventory.getCurrentItem();
      if (var5 != null && var5.getItem() == Items.coal) {
         if (!entityPlayer.capabilities.isCreativeMode && --var5.stackSize == 0) {
            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
         }

         this.fuel += 3600;
      }

      this.pushX = this.posX - entityPlayer.posX;
      this.pushZ = this.posZ - entityPlayer.posZ;
      return true;
   }

   @Override
   protected void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setDouble("PushX", this.pushX);
      nBTTagCompound.setDouble("PushZ", this.pushZ);
      nBTTagCompound.setShort("Fuel", (short)this.fuel);
   }

   @Override
   protected void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      this.pushX = nBTTagCompound.getDouble("PushX");
      this.pushZ = nBTTagCompound.getDouble("PushZ");
      this.fuel = nBTTagCompound.getShort("Fuel");
   }

   protected boolean isMinecartPowered() {
      return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
   }

   protected void setMinecartPowered(boolean boolean1) {
      if (boolean1) {
         this.dataWatcher.updateObject(16, (byte)(this.dataWatcher.getWatchableObjectByte(16) | 1));
      } else {
         this.dataWatcher.updateObject(16, (byte)(this.dataWatcher.getWatchableObjectByte(16) & -2));
      }
   }

   @Override
   public IBlockState getDefaultDisplayTile() {
      return (this.isMinecartPowered() ? Blocks.lit_furnace : Blocks.furnace).getDefaultState().withProperty(BlockFurnace.FACING, EnumFacing.NORTH);
   }
}
