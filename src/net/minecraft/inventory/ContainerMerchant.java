package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerMerchant extends Container {
   private final IMerchant theMerchant;
   private final InventoryMerchant merchantInventory;
   private final World theWorld;

   public ContainerMerchant(InventoryPlayer inventoryPlayer, IMerchant iMerchant, World world) {
      this.theMerchant = iMerchant;
      this.theWorld = world;
      this.merchantInventory = new InventoryMerchant(inventoryPlayer.player, iMerchant);
      this.addSlotToContainer(new Slot(this.merchantInventory, 0, 36, 53));
      this.addSlotToContainer(new Slot(this.merchantInventory, 1, 62, 53));
      this.addSlotToContainer(new SlotMerchantResult(inventoryPlayer.player, iMerchant, this.merchantInventory, 2, 120, 53));

      for (int var7 = 0; var7 < 3; var7++) {
         for (int var8 = 0; var8 < 9; var8++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, var8 + var7 * 9 + 9, 8 + var8 * 18, 84 + var7 * 18));
         }
      }

      for (int var9 = 0; var9 < 9; var9++) {
         this.addSlotToContainer(new Slot(inventoryPlayer, var9, 8 + var9 * 18, 142));
      }
   }

   public InventoryMerchant getMerchantInventory() {
      return this.merchantInventory;
   }

   @Override
   public void onCraftGuiOpened(ICrafting iCrafting) {
      super.onCraftGuiOpened(iCrafting);
   }

   @Override
   public void detectAndSendChanges() {
      super.detectAndSendChanges();
   }

   @Override
   public void onCraftMatrixChanged(IInventory iInventory) {
      this.merchantInventory.resetRecipeAndSlots();
      super.onCraftMatrixChanged(iInventory);
   }

   public void setCurrentRecipeIndex(int integer) {
      this.merchantInventory.setCurrentRecipeIndex(integer);
   }

   @Override
   public void updateProgressBar(int integer1, int integer2) {
   }

   @Override
   public boolean canInteractWith(EntityPlayer entityPlayer) {
      return this.theMerchant.getCustomer() == entityPlayer;
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
         } else if (integer != 0 && integer != 1) {
            if (integer >= 3 && integer < 30) {
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

   @Override
   public void onContainerClosed(EntityPlayer entityPlayer) {
      super.onContainerClosed(entityPlayer);
      this.theMerchant.setCustomer(null);
      super.onContainerClosed(entityPlayer);
      if (!this.theWorld.isRemote) {
         ItemStack var5 = this.merchantInventory.removeStackFromSlot(0);
         if (var5 != null) {
            entityPlayer.dropPlayerItemWithRandomChoice(var5, false);
         }

         var5 = this.merchantInventory.removeStackFromSlot(1);
         if (var5 != null) {
            entityPlayer.dropPlayerItemWithRandomChoice(var5, false);
         }
      }
   }
}
