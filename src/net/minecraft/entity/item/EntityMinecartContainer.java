package net.minecraft.entity.item;

import java.util.Arrays;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;

public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer {
   private ItemStack[] minecartContainerItems = new ItemStack[36];
   private boolean dropContentsWhenDead = true;

   public EntityMinecartContainer(World world) {
      super(world);
   }

   public EntityMinecartContainer(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4);
   }

   @Override
   public void killMinecart(DamageSource damageSource) {
      super.killMinecart(damageSource);
      if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         InventoryHelper.dropInventoryItems(this.worldObj, this, this);
      }
   }

   @Override
   public ItemStack getStackInSlot(int integer) {
      return this.minecartContainerItems[integer];
   }

   @Override
   public ItemStack decrStackSize(int integer1, int integer2) {
      if (this.minecartContainerItems[integer1] != null) {
         if (this.minecartContainerItems[integer1].stackSize <= integer2) {
            ItemStack var7 = this.minecartContainerItems[integer1];
            this.minecartContainerItems[integer1] = null;
            return var7;
         }

         ItemStack var6 = this.minecartContainerItems[integer1].splitStack(integer2);
         if (this.minecartContainerItems[integer1].stackSize == 0) {
            this.minecartContainerItems[integer1] = null;
         }

         return var6;
      } else {
         return null;
      }
   }

   @Override
   public ItemStack removeStackFromSlot(int integer) {
      if (this.minecartContainerItems[integer] != null) {
         ItemStack var5 = this.minecartContainerItems[integer];
         this.minecartContainerItems[integer] = null;
         return var5;
      } else {
         return null;
      }
   }

   @Override
   public void setInventorySlotContents(int integer, ItemStack itemStack) {
      this.minecartContainerItems[integer] = itemStack;
      if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
         itemStack.stackSize = this.getInventoryStackLimit();
      }
   }

   @Override
   public void markDirty() {
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
      return !this.isDead && entityPlayer.getDistanceSqToEntity(this) <= 64.0;
   }

   @Override
   public void openInventory(EntityPlayer entityPlayer) {
   }

   @Override
   public void closeInventory(EntityPlayer entityPlayer) {
   }

   @Override
   public boolean isItemValidForSlot(int integer, ItemStack itemStack) {
      return true;
   }

   @Override
   public String getName() {
      return this.hasCustomName() ? this.getCustomNameTag() : "container.minecart";
   }

   @Override
   public int getInventoryStackLimit() {
      return 64;
   }

   @Override
   public void travelToDimension(int integer) {
      this.dropContentsWhenDead = false;
      super.travelToDimension(integer);
   }

   @Override
   public void setDead() {
      if (this.dropContentsWhenDead) {
         InventoryHelper.dropInventoryItems(this.worldObj, this, this);
      }

      super.setDead();
   }

   @Override
   protected void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      NBTTagList var5 = new NBTTagList();

      for (int var6 = 0; var6 < this.minecartContainerItems.length; var6++) {
         if (this.minecartContainerItems[var6] != null) {
            NBTTagCompound var7 = new NBTTagCompound();
            var7.setByte("Slot", (byte)var6);
            this.minecartContainerItems[var6].writeToNBT(var7);
            var5.appendTag(var7);
         }
      }

      nBTTagCompound.setTag("Items", var5);
   }

   @Override
   protected void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      NBTTagList var5 = nBTTagCompound.getTagList("Items", 10);
      this.minecartContainerItems = new ItemStack[this.getSizeInventory()];

      for (int var6 = 0; var6 < var5.tagCount(); var6++) {
         NBTTagCompound var7 = var5.getCompoundTagAt(var6);
         int var8 = var7.getByte("Slot") & 255;
         if (var8 >= 0 && var8 < this.minecartContainerItems.length) {
            this.minecartContainerItems[var8] = ItemStack.loadItemStackFromNBT(var7);
         }
      }
   }

   @Override
   public boolean interactFirst(EntityPlayer entityPlayer) {
      if (!this.worldObj.isRemote) {
         entityPlayer.displayGUIChest(this);
      }

      return true;
   }

   @Override
   protected void applyDrag() {
      int var3 = 15 - Container.calcRedstoneFromInventory(this);
      float var4 = 0.98F + var3 * 0.001F;
      this.motionX *= var4;
      this.motionY *= 0.0;
      this.motionZ *= var4;
   }

   @Override
   public int getField(int integer) {
      return 0;
   }

   @Override
   public void setField(int integer1, int integer2) {
   }

   @Override
   public int getFieldCount() {
      return 0;
   }

   @Override
   public boolean isLocked() {
      return false;
   }

   @Override
   public void setLockCode(LockCode lockCode) {
   }

   @Override
   public LockCode getLockCode() {
      return LockCode.EMPTY_CODE;
   }

   @Override
   public void clear() {
      Arrays.fill(this.minecartContainerItems, null);
   }
}
