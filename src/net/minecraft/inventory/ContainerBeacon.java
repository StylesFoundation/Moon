package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ContainerBeacon extends Container {
   private final IInventory tileBeacon;
   private final ContainerBeacon.BeaconSlot beaconSlot;

   public ContainerBeacon(IInventory iInventory1, IInventory iInventory2) {
      this.tileBeacon = iInventory2;
      this.addSlotToContainer(this.beaconSlot = new ContainerBeacon.BeaconSlot(iInventory2, 0, 136, 110));
      byte var6 = 36;
      short var7 = 137;

      for (int var8 = 0; var8 < 3; var8++) {
         for (int var9 = 0; var9 < 9; var9++) {
            this.addSlotToContainer(new Slot(iInventory1, var9 + var8 * 9 + 9, var6 + var9 * 18, var7 + var8 * 18));
         }
      }

      for (int var10 = 0; var10 < 9; var10++) {
         this.addSlotToContainer(new Slot(iInventory1, var10, var6 + var10 * 18, 195));
      }
   }

   @Override
   public void onCraftGuiOpened(ICrafting iCrafting) {
      super.onCraftGuiOpened(iCrafting);
      iCrafting.sendAllWindowProperties(this, this.tileBeacon);
   }

   @Override
   public void updateProgressBar(int integer1, int integer2) {
      this.tileBeacon.setField(integer1, integer2);
   }

   public IInventory func_180611_e() {
      return this.tileBeacon;
   }

   @Override
   public void onContainerClosed(EntityPlayer entityPlayer) {
      super.onContainerClosed(entityPlayer);
      if (entityPlayer != null && !entityPlayer.worldObj.isRemote) {
         ItemStack var5 = this.beaconSlot.decrStackSize(this.beaconSlot.getSlotStackLimit());
         if (var5 != null) {
            entityPlayer.dropPlayerItemWithRandomChoice(var5, false);
         }
      }
   }

   @Override
   public boolean canInteractWith(EntityPlayer entityPlayer) {
      return this.tileBeacon.isUseableByPlayer(entityPlayer);
   }

   @Override
   public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int integer) {
      ItemStack var6 = null;
      Slot var7 = this.inventorySlots.get(integer);
      if (var7 != null && var7.getHasStack()) {
         ItemStack var8 = var7.getStack();
         var6 = var8.copy();
         if (integer == 0) {
            if (!this.mergeItemStack(var8, 1, 37, true)) {
               return null;
            }

            var7.onSlotChange(var8, var6);
         } else if (!this.beaconSlot.getHasStack() && this.beaconSlot.isItemValid(var8) && var8.stackSize == 1) {
            if (!this.mergeItemStack(var8, 0, 1, false)) {
               return null;
            }
         } else if (integer >= 1 && integer < 28) {
            if (!this.mergeItemStack(var8, 28, 37, false)) {
               return null;
            }
         } else if (integer >= 28 && integer < 37) {
            if (!this.mergeItemStack(var8, 1, 28, false)) {
               return null;
            }
         } else if (!this.mergeItemStack(var8, 1, 37, false)) {
            return null;
         }

         if (var8.stackSize == 0) {
            var7.putStack(null);
         } else {
            var7.onSlotChanged();
         }

         if (var8.stackSize == var6.stackSize) {
            return null;
         }

         var7.onPickupFromSlot(entityPlayer, var8);
      }

      return var6;
   }

   static class BeaconSlot extends Slot {
      public BeaconSlot(IInventory iInventory, int integer2, int integer3, int integer4) {
         super(iInventory, integer2, integer3, integer4);
      }

      @Override
      public boolean isItemValid(ItemStack itemStack) {
         return itemStack != null
            && (
               itemStack.getItem() == Items.emerald
                  || itemStack.getItem() == Items.diamond
                  || itemStack.getItem() == Items.gold_ingot
                  || itemStack.getItem() == Items.iron_ingot
            );
      }

      @Override
      public int getSlotStackLimit() {
         return 1;
      }
   }
}
