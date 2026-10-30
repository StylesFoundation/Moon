package net.minecraft.inventory;

import java.util.Arrays;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class InventoryCrafting implements IInventory {
   private final ItemStack[] stackList;
   private final int inventoryWidth;
   private final int inventoryHeight;
   private final Container eventHandler;

   public InventoryCrafting(Container container, int integer2, int integer3) {
      int var4 = integer2 * integer3;
      this.stackList = new ItemStack[var4];
      this.eventHandler = container;
      this.inventoryWidth = integer2;
      this.inventoryHeight = integer3;
   }

   @Override
   public int getSizeInventory() {
      return this.stackList.length;
   }

   @Override
   public ItemStack getStackInSlot(int integer) {
      return integer >= this.getSizeInventory() ? null : this.stackList[integer];
   }

   public ItemStack getStackInRowAndColumn(int integer1, int integer2) {
      return integer1 >= 0 && integer1 < this.inventoryWidth && integer2 >= 0 && integer2 <= this.inventoryHeight
         ? this.getStackInSlot(integer1 + integer2 * this.inventoryWidth)
         : null;
   }

   @Override
   public String getName() {
      return "container.crafting";
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
   public ItemStack removeStackFromSlot(int integer) {
      if (this.stackList[integer] != null) {
         ItemStack var5 = this.stackList[integer];
         this.stackList[integer] = null;
         return var5;
      } else {
         return null;
      }
   }

   @Override
   public ItemStack decrStackSize(int integer1, int integer2) {
      if (this.stackList[integer1] != null) {
         if (this.stackList[integer1].stackSize <= integer2) {
            ItemStack var7 = this.stackList[integer1];
            this.stackList[integer1] = null;
            this.eventHandler.onCraftMatrixChanged(this);
            return var7;
         }

         ItemStack var6 = this.stackList[integer1].splitStack(integer2);
         if (this.stackList[integer1].stackSize == 0) {
            this.stackList[integer1] = null;
         }

         this.eventHandler.onCraftMatrixChanged(this);
         return var6;
      } else {
         return null;
      }
   }

   @Override
   public void setInventorySlotContents(int integer, ItemStack itemStack) {
      this.stackList[integer] = itemStack;
      this.eventHandler.onCraftMatrixChanged(this);
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
      Arrays.fill(this.stackList, null);
   }

   public int getHeight() {
      return this.inventoryHeight;
   }

   public int getWidth() {
      return this.inventoryWidth;
   }
}
