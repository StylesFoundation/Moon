package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class ContainerHopper extends Container {
   private final IInventory hopperInventory;

   public ContainerHopper(InventoryPlayer inventoryPlayer, IInventory iInventory, EntityPlayer entityPlayer) {
      this.hopperInventory = iInventory;
      iInventory.openInventory(entityPlayer);
      byte var7 = 51;

      for (int var8 = 0; var8 < iInventory.getSizeInventory(); var8++) {
         this.addSlotToContainer(new Slot(iInventory, var8, 44 + var8 * 18, 20));
      }

      for (int var10 = 0; var10 < 3; var10++) {
         for (int var9 = 0; var9 < 9; var9++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, var9 + var10 * 9 + 9, 8 + var9 * 18, var10 * 18 + var7));
         }
      }

      for (int var11 = 0; var11 < 9; var11++) {
         this.addSlotToContainer(new Slot(inventoryPlayer, var11, 8 + var11 * 18, 109));
      }
   }

   @Override
   public boolean canInteractWith(EntityPlayer entityPlayer) {
      return this.hopperInventory.isUseableByPlayer(entityPlayer);
   }

   @Override
   public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int integer) {
      ItemStack var6 = null;
      Slot var7 = this.inventorySlots.get(integer);
      if (var7 != null && var7.getHasStack()) {
         ItemStack var8 = var7.getStack();
         var6 = var8.copy();
         if (integer < this.hopperInventory.getSizeInventory()) {
            if (!this.mergeItemStack(var8, this.hopperInventory.getSizeInventory(), this.inventorySlots.size(), true)) {
               return null;
            }
         } else if (!this.mergeItemStack(var8, 0, this.hopperInventory.getSizeInventory(), false)) {
            return null;
         }

         if (var8.stackSize == 0) {
            var7.putStack(null);
         } else {
            var7.onSlotChanged();
         }
      }

      return var6;
   }

   @Override
   public void onContainerClosed(EntityPlayer entityPlayer) {
      super.onContainerClosed(entityPlayer);
      this.hopperInventory.closeInventory(entityPlayer);
   }
}
