package net.minecraft.inventory;

import java.util.Arrays;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class InventoryCraftResult implements IInventory {
   private final ItemStack[] stackResult = new ItemStack[1];

   @Override
   public int getSizeInventory() {
      return 1;
   }

   @Override
   public ItemStack getStackInSlot(int integer) {
      return this.stackResult[0];
   }

   @Override
   public String getName() {
      return "Result";
   }

   @Override
   public boolean hasCustomName() {
      return false;
   }

   @Override
   public IChatComponent getDisplayName() {
      return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
   }

   @Override
   public ItemStack decrStackSize(int integer1, int integer2) {
      if (this.stackResult[0] != null) {
         ItemStack var6 = this.stackResult[0];
         this.stackResult[0] = null;
         return var6;
      } else {
         return null;
      }
   }

   @Override
   public ItemStack removeStackFromSlot(int integer) {
      if (this.stackResult[0] != null) {
         ItemStack var5 = this.stackResult[0];
         this.stackResult[0] = null;
         return var5;
      } else {
         return null;
      }
   }

   @Override
   public void setInventorySlotContents(int integer, ItemStack itemStack) {
      this.stackResult[0] = itemStack;
   }

   @Override
   public int getInventoryStackLimit() {
      return 64;
   }

   @Override
   public void markDirty() {
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
      return true;
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
      Arrays.fill(this.stackResult, null);
   }
}
