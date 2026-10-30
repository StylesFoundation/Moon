package net.minecraft.tileentity;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityDispenser extends TileEntityLockable implements IInventory {
   private static final Random RNG = new Random();
   private ItemStack[] stacks = new ItemStack[9];
   protected String customName;

   @Override
   public int getSizeInventory() {
      return 9;
   }

   @Override
   public ItemStack getStackInSlot(int integer) {
      return this.stacks[integer];
   }

   @Override
   public ItemStack decrStackSize(int integer1, int integer2) {
      if (this.stacks[integer1] != null) {
         if (this.stacks[integer1].stackSize <= integer2) {
            ItemStack var7 = this.stacks[integer1];
            this.stacks[integer1] = null;
            this.markDirty();
            return var7;
         }

         ItemStack var6 = this.stacks[integer1].splitStack(integer2);
         if (this.stacks[integer1].stackSize == 0) {
            this.stacks[integer1] = null;
         }

         this.markDirty();
         return var6;
      } else {
         return null;
      }
   }

   @Override
   public ItemStack removeStackFromSlot(int integer) {
      if (this.stacks[integer] != null) {
         ItemStack var5 = this.stacks[integer];
         this.stacks[integer] = null;
         return var5;
      } else {
         return null;
      }
   }

   public int getDispenseSlot() {
      int var4 = -1;
      int var5 = 1;

      for (int var6 = 0; var6 < this.stacks.length; var6++) {
         if (this.stacks[var6] != null && RNG.nextInt(var5++) == 0) {
            var4 = var6;
         }
      }

      return var4;
   }

   @Override
   public void setInventorySlotContents(int integer, ItemStack itemStack) {
      this.stacks[integer] = itemStack;
      if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
         itemStack.stackSize = this.getInventoryStackLimit();
      }

      this.markDirty();
   }

   public int addItemStack(ItemStack itemStack) {
      for (int var5 = 0; var5 < this.stacks.length; var5++) {
         if (this.stacks[var5] == null || this.stacks[var5].getItem() == null) {
            this.setInventorySlotContents(var5, itemStack);
            return var5;
         }
      }

      return -1;
   }

   @Override
   public String getName() {
      return this.hasCustomName() ? this.customName : "container.dispenser";
   }

   public void setCustomName(String string) {
      this.customName = string;
   }

   @Override
   public boolean hasCustomName() {
      return this.customName != null;
   }

   @Override
   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      super.readFromNBT(nBTTagCompound);
      NBTTagList var5 = nBTTagCompound.getTagList("Items", 10);
      this.stacks = new ItemStack[this.getSizeInventory()];

      for (int var6 = 0; var6 < var5.tagCount(); var6++) {
         NBTTagCompound var7 = var5.getCompoundTagAt(var6);
         int var8 = var7.getByte("Slot") & 255;
         if (var8 >= 0 && var8 < this.stacks.length) {
            this.stacks[var8] = ItemStack.loadItemStackFromNBT(var7);
         }
      }

      if (nBTTagCompound.hasKey("CustomName", 8)) {
         this.customName = nBTTagCompound.getString("CustomName");
      }
   }

   @Override
   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      super.writeToNBT(nBTTagCompound);
      NBTTagList var5 = new NBTTagList();

      for (int var6 = 0; var6 < this.stacks.length; var6++) {
         if (this.stacks[var6] != null) {
            NBTTagCompound var7 = new NBTTagCompound();
            var7.setByte("Slot", (byte)var6);
            this.stacks[var6].writeToNBT(var7);
            var5.appendTag(var7);
         }
      }

      nBTTagCompound.setTag("Items", var5);
      if (this.hasCustomName()) {
         nBTTagCompound.setString("CustomName", this.customName);
      }
   }

   @Override
   public int getInventoryStackLimit() {
      return 64;
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
      return this.worldObj.getTileEntity(this.pos) == this
         && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
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
   public String getGuiID() {
      return "minecraft:dispenser";
   }

   @Override
   public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
      return new ContainerDispenser(inventoryPlayer, this);
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
   public void clear() {
      Arrays.fill(this.stacks, null);
   }
}
