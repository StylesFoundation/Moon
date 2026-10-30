package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;

public class ContainerBrewingStand extends Container {
   private final IInventory tileBrewingStand;
   private final Slot theSlot;
   private int brewTime;

   public ContainerBrewingStand(InventoryPlayer inventoryPlayer, IInventory iInventory) {
      this.tileBrewingStand = iInventory;
      this.addSlotToContainer(new ContainerBrewingStand.Potion(inventoryPlayer.player, iInventory, 0, 56, 46));
      this.addSlotToContainer(new ContainerBrewingStand.Potion(inventoryPlayer.player, iInventory, 1, 79, 53));
      this.addSlotToContainer(new ContainerBrewingStand.Potion(inventoryPlayer.player, iInventory, 2, 102, 46));
      this.theSlot = this.addSlotToContainer(new ContainerBrewingStand.Ingredient(iInventory, 3, 79, 17));

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
      iCrafting.sendAllWindowProperties(this, this.tileBrewingStand);
   }

   @Override
   public void detectAndSendChanges() {
      super.detectAndSendChanges();

      for (ICrafting var5 : this.crafters) {
         if (this.brewTime != this.tileBrewingStand.getField(0)) {
            var5.sendProgressBarUpdate(this, 0, this.tileBrewingStand.getField(0));
         }
      }

      this.brewTime = this.tileBrewingStand.getField(0);
   }

   @Override
   public void updateProgressBar(int integer1, int integer2) {
      this.tileBrewingStand.setField(integer1, integer2);
   }

   @Override
   public boolean canInteractWith(EntityPlayer entityPlayer) {
      return this.tileBrewingStand.isUseableByPlayer(entityPlayer);
   }

   @Override
   public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int integer) {
      ItemStack var6 = null;
      Slot var7 = this.inventorySlots.get(integer);
      if (var7 != null && var7.getHasStack()) {
         ItemStack var8 = var7.getStack();
         var6 = var8.copy();
         if ((integer < 0 || integer > 2) && integer != 3) {
            if (!this.theSlot.getHasStack() && this.theSlot.isItemValid(var8)) {
               if (!this.mergeItemStack(var8, 3, 4, false)) {
                  return null;
               }
            } else if (ContainerBrewingStand.Potion.canHoldPotion(var6)) {
               if (!this.mergeItemStack(var8, 0, 3, false)) {
                  return null;
               }
            } else if (integer >= 4 && integer < 31) {
               if (!this.mergeItemStack(var8, 31, 40, false)) {
                  return null;
               }
            } else if (integer >= 31 && integer < 40) {
               if (!this.mergeItemStack(var8, 4, 31, false)) {
                  return null;
               }
            } else if (!this.mergeItemStack(var8, 4, 40, false)) {
               return null;
            }
         } else {
            if (!this.mergeItemStack(var8, 4, 40, true)) {
               return null;
            }

            var7.onSlotChange(var8, var6);
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

   static class Ingredient extends Slot {
      public Ingredient(IInventory iInventory, int integer2, int integer3, int integer4) {
         super(iInventory, integer2, integer3, integer4);
      }

      @Override
      public boolean isItemValid(ItemStack itemStack) {
         return itemStack != null && itemStack.getItem().isPotionIngredient(itemStack);
      }

      @Override
      public int getSlotStackLimit() {
         return 64;
      }
   }

   static class Potion extends Slot {
      private final EntityPlayer player;

      public Potion(EntityPlayer entityPlayer, IInventory iInventory, int integer3, int integer4, int integer5) {
         super(iInventory, integer3, integer4, integer5);
         this.player = entityPlayer;
      }

      @Override
      public boolean isItemValid(ItemStack itemStack) {
         return canHoldPotion(itemStack);
      }

      @Override
      public int getSlotStackLimit() {
         return 1;
      }

      @Override
      public void onPickupFromSlot(EntityPlayer entityPlayer, ItemStack itemStack) {
         if (itemStack.getItem() == Items.potionitem && itemStack.getMetadata() > 0) {
            this.player.triggerAchievement(AchievementList.potion);
         }

         super.onPickupFromSlot(entityPlayer, itemStack);
      }

      public static boolean canHoldPotion(ItemStack itemStack) {
         return itemStack != null && (itemStack.getItem() == Items.potionitem || itemStack.getItem() == Items.glass_bottle);
      }
   }
}
