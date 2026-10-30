package net.minecraft.inventory;

import java.util.Arrays;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class InventoryMerchant implements IInventory {
   private final IMerchant theMerchant;
   private final ItemStack[] theInventory = new ItemStack[3];
   private final EntityPlayer thePlayer;
   private MerchantRecipe currentRecipe;
   private int currentRecipeIndex;

   public InventoryMerchant(EntityPlayer entityPlayer, IMerchant iMerchant) {
      this.thePlayer = entityPlayer;
      this.theMerchant = iMerchant;
   }

   @Override
   public int getSizeInventory() {
      return this.theInventory.length;
   }

   @Override
   public ItemStack getStackInSlot(int integer) {
      return this.theInventory[integer];
   }

   @Override
   public ItemStack decrStackSize(int integer1, int integer2) {
      if (this.theInventory[integer1] != null) {
         if (integer1 == 2) {
            ItemStack var8 = this.theInventory[integer1];
            this.theInventory[integer1] = null;
            return var8;
         }

         if (this.theInventory[integer1].stackSize <= integer2) {
            ItemStack var7 = this.theInventory[integer1];
            this.theInventory[integer1] = null;
            if (this.inventoryResetNeededOnSlotChange(integer1)) {
               this.resetRecipeAndSlots();
            }

            return var7;
         } else {
            ItemStack var6 = this.theInventory[integer1].splitStack(integer2);
            if (this.theInventory[integer1].stackSize == 0) {
               this.theInventory[integer1] = null;
            }

            if (this.inventoryResetNeededOnSlotChange(integer1)) {
               this.resetRecipeAndSlots();
            }

            return var6;
         }
      } else {
         return null;
      }
   }

   private boolean inventoryResetNeededOnSlotChange(int integer) {
      return integer == 0 || integer == 1;
   }

   @Override
   public ItemStack removeStackFromSlot(int integer) {
      if (this.theInventory[integer] != null) {
         ItemStack var5 = this.theInventory[integer];
         this.theInventory[integer] = null;
         return var5;
      } else {
         return null;
      }
   }

   @Override
   public void setInventorySlotContents(int integer, ItemStack itemStack) {
      this.theInventory[integer] = itemStack;
      if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
         itemStack.stackSize = this.getInventoryStackLimit();
      }

      if (this.inventoryResetNeededOnSlotChange(integer)) {
         this.resetRecipeAndSlots();
      }
   }

   @Override
   public String getName() {
      return "mob.villager";
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
   public int getInventoryStackLimit() {
      return 64;
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
      return this.theMerchant.getCustomer() == entityPlayer;
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
   public void markDirty() {
      this.resetRecipeAndSlots();
   }

   public void resetRecipeAndSlots() {
      this.currentRecipe = null;
      ItemStack var4 = this.theInventory[0];
      ItemStack var5 = this.theInventory[1];
      if (var4 == null) {
         var4 = var5;
         var5 = null;
      }

      if (var4 == null) {
         this.setInventorySlotContents(2, null);
      } else {
         MerchantRecipeList var6 = this.theMerchant.getRecipes(this.thePlayer);
         if (var6 != null) {
            MerchantRecipe var7 = var6.canRecipeBeUsed(var4, var5, this.currentRecipeIndex);
            if (var7 != null && !var7.isRecipeDisabled()) {
               this.currentRecipe = var7;
               this.setInventorySlotContents(2, var7.getItemToSell().copy());
            } else if (var5 != null) {
               var7 = var6.canRecipeBeUsed(var5, var4, this.currentRecipeIndex);
               if (var7 != null && !var7.isRecipeDisabled()) {
                  this.currentRecipe = var7;
                  this.setInventorySlotContents(2, var7.getItemToSell().copy());
               } else {
                  this.setInventorySlotContents(2, null);
               }
            } else {
               this.setInventorySlotContents(2, null);
            }
         }
      }

      this.theMerchant.verifySellingItem(this.getStackInSlot(2));
   }

   public MerchantRecipe getCurrentRecipe() {
      return this.currentRecipe;
   }

   public void setCurrentRecipeIndex(int integer) {
      this.currentRecipeIndex = integer;
      this.resetRecipeAndSlots();
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
      Arrays.fill(this.theInventory, null);
   }
}
