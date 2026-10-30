package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.village.MerchantRecipe;

public class SlotMerchantResult extends Slot {
   private final InventoryMerchant theMerchantInventory;
   private final EntityPlayer thePlayer;
   private int field_75231_g;
   private final IMerchant theMerchant;

   public SlotMerchantResult(EntityPlayer entityPlayer, IMerchant iMerchant, InventoryMerchant inventoryMerchant, int integer4, int integer5, int integer6) {
      super(inventoryMerchant, integer4, integer5, integer6);
      this.thePlayer = entityPlayer;
      this.theMerchant = iMerchant;
      this.theMerchantInventory = inventoryMerchant;
   }

   @Override
   public boolean isItemValid(ItemStack itemStack) {
      return false;
   }

   @Override
   public ItemStack decrStackSize(int integer) {
      if (this.getHasStack()) {
         this.field_75231_g = this.field_75231_g + Math.min(integer, this.getStack().stackSize);
      }

      return super.decrStackSize(integer);
   }

   @Override
   protected void onCrafting(ItemStack itemStack, int integer) {
      this.field_75231_g += integer;
      this.onCrafting(itemStack);
   }

   @Override
   protected void onCrafting(ItemStack itemStack) {
      itemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75231_g);
      this.field_75231_g = 0;
   }

   @Override
   public void onPickupFromSlot(EntityPlayer entityPlayer, ItemStack itemStack) {
      this.onCrafting(itemStack);
      MerchantRecipe var6 = this.theMerchantInventory.getCurrentRecipe();
      if (var6 != null) {
         ItemStack var7 = this.theMerchantInventory.getStackInSlot(0);
         ItemStack var8 = this.theMerchantInventory.getStackInSlot(1);
         if (this.doTrade(var6, var7, var8) || this.doTrade(var6, var8, var7)) {
            this.theMerchant.useRecipe(var6);
            entityPlayer.triggerAchievement(StatList.timesTradedWithVillagerStat);
            if (var7 != null && var7.stackSize <= 0) {
               var7 = null;
            }

            if (var8 != null && var8.stackSize <= 0) {
               var8 = null;
            }

            this.theMerchantInventory.setInventorySlotContents(0, var7);
            this.theMerchantInventory.setInventorySlotContents(1, var8);
         }
      }
   }

   private boolean doTrade(MerchantRecipe merchantRecipe, ItemStack itemStack2, ItemStack itemStack3) {
      ItemStack var7 = merchantRecipe.getItemToBuy();
      ItemStack var8 = merchantRecipe.getSecondItemToBuy();
      if (itemStack2 != null && itemStack2.getItem() == var7.getItem()) {
         if (var8 != null && itemStack3 != null && var8.getItem() == itemStack3.getItem()) {
            itemStack2.stackSize = itemStack2.stackSize - var7.stackSize;
            itemStack3.stackSize = itemStack3.stackSize - var8.stackSize;
            return true;
         }

         if (var8 == null && itemStack3 == null) {
            itemStack2.stackSize = itemStack2.stackSize - var7.stackSize;
            return true;
         }
      }

      return false;
   }
}
