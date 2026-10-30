package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerFurnace extends Container {
   private final IInventory tileFurnace;
   private int cookTime;
   private int totalCookTime;
   private int furnaceBurnTime;
   private int currentItemBurnTime;

   public ContainerFurnace(InventoryPlayer inventoryPlayer, IInventory iInventory) {
      this.tileFurnace = iInventory;
      this.addSlotToContainer(new Slot(iInventory, 0, 56, 17));
      this.addSlotToContainer(new SlotFurnaceFuel(iInventory, 1, 56, 53));
      this.addSlotToContainer(new SlotFurnaceOutput(inventoryPlayer.player, iInventory, 2, 116, 35));

      for (int var6 = 0; var6 < 3; var6++) {
         for (int var7 = 0; var7 < 9; var7++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
         }
      }

      for (int var8 = 0; var8 < 9; var8++) {
         this.addSlotToContainer(new Slot(inventoryPlayer, var8, 8 + var8 * 18, 142));
      }
   }

   @Override
   public void onCraftGuiOpened(ICrafting iCrafting) {
      super.onCraftGuiOpened(iCrafting);
      iCrafting.sendAllWindowProperties(this, this.tileFurnace);
   }

   @Override
   public void detectAndSendChanges() {
      super.detectAndSendChanges();

      for (ICrafting var5 : this.crafters) {
         if (this.cookTime != this.tileFurnace.getField(2)) {
            var5.sendProgressBarUpdate(this, 2, this.tileFurnace.getField(2));
         }

         if (this.furnaceBurnTime != this.tileFurnace.getField(0)) {
            var5.sendProgressBarUpdate(this, 0, this.tileFurnace.getField(0));
         }

         if (this.currentItemBurnTime != this.tileFurnace.getField(1)) {
            var5.sendProgressBarUpdate(this, 1, this.tileFurnace.getField(1));
         }

         if (this.totalCookTime != this.tileFurnace.getField(3)) {
            var5.sendProgressBarUpdate(this, 3, this.tileFurnace.getField(3));
         }
      }

      this.cookTime = this.tileFurnace.getField(2);
      this.furnaceBurnTime = this.tileFurnace.getField(0);
      this.currentItemBurnTime = this.tileFurnace.getField(1);
      this.totalCookTime = this.tileFurnace.getField(3);
   }

   @Override
   public void updateProgressBar(int integer1, int integer2) {
      this.tileFurnace.setField(integer1, integer2);
   }

   @Override
   public boolean canInteractWith(EntityPlayer entityPlayer) {
      return this.tileFurnace.isUseableByPlayer(entityPlayer);
   }

   @Override
   public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int integer) {
      ItemStack var6 = null;
      Slot var7 = this.inventorySlots.get(integer);
      if (var7 != null && var7.getHasStack()) {
         ItemStack var8 = var7.getStack();
         var6 = var8.copy();
         if (integer == 2) {
            if (!this.mergeItemStack(var8, 3, 39, true)) {
               return null;
            }

            var7.onSlotChange(var8, var6);
         } else if (integer != 1 && integer != 0) {
            if (FurnaceRecipes.instance().getSmeltingResult(var8) != null) {
               if (!this.mergeItemStack(var8, 0, 1, false)) {
                  return null;
               }
            } else if (TileEntityFurnace.isItemFuel(var8)) {
               if (!this.mergeItemStack(var8, 1, 2, false)) {
                  return null;
               }
            } else if (integer >= 3 && integer < 30) {
               if (!this.mergeItemStack(var8, 30, 39, false)) {
                  return null;
               }
            } else if (integer >= 30 && integer < 39 && !this.mergeItemStack(var8, 3, 30, false)) {
               return null;
            }
         } else if (!this.mergeItemStack(var8, 3, 39, false)) {
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
}
