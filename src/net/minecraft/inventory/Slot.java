package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class Slot {
   private final int slotIndex;
   public final IInventory inventory;
   public int slotNumber;
   public int xDisplayPosition;
   public int yDisplayPosition;

   public Slot(IInventory iInventory, int integer2, int integer3, int integer4) {
      this.inventory = iInventory;
      this.slotIndex = integer2;
      this.xDisplayPosition = integer3;
      this.yDisplayPosition = integer4;
   }

   public void onSlotChange(ItemStack itemStack1, ItemStack itemStack2) {
      if (itemStack1 != null && itemStack2 != null && itemStack1.getItem() == itemStack2.getItem()) {
         int var6 = itemStack2.stackSize - itemStack1.stackSize;
         if (var6 > 0) {
            this.onCrafting(itemStack1, var6);
         }
      }
   }

   protected void onCrafting(ItemStack itemStack, int integer) {
   }

   protected void onCrafting(ItemStack itemStack) {
   }

   public void onPickupFromSlot(EntityPlayer entityPlayer, ItemStack itemStack) {
      this.onSlotChanged();
   }

   public boolean isItemValid(ItemStack itemStack) {
      return true;
   }

   public ItemStack getStack() {
      return this.inventory.getStackInSlot(this.slotIndex);
   }

   public boolean getHasStack() {
      return this.getStack() != null;
   }

   public void putStack(ItemStack itemStack) {
      this.inventory.setInventorySlotContents(this.slotIndex, itemStack);
      this.onSlotChanged();
   }

   public void onSlotChanged() {
      this.inventory.markDirty();
   }

   public int getSlotStackLimit() {
      return this.inventory.getInventoryStackLimit();
   }

   public int getItemStackLimit(ItemStack itemStack) {
      return this.getSlotStackLimit();
   }

   public String getSlotTexture() {
      return null;
   }

   public ItemStack decrStackSize(int integer) {
      return this.inventory.decrStackSize(this.slotIndex, integer);
   }

   public boolean isHere(IInventory iInventory, int integer) {
      return iInventory == this.inventory && integer == this.slotIndex;
   }

   public boolean canTakeStack(EntityPlayer entityPlayer) {
      return true;
   }

   public boolean canBeHovered() {
      return true;
   }
}
