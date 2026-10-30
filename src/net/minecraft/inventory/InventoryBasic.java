package net.minecraft.inventory;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class InventoryBasic implements IInventory {
   private String inventoryTitle;
   private final int slotsCount;
   private final ItemStack[] inventoryContents;
   private List<IInvBasic> changeListeners;
   private boolean hasCustomName;

   public InventoryBasic(String string, boolean boolean2, int integer) {
      this.inventoryTitle = string;
      this.hasCustomName = boolean2;
      this.slotsCount = integer;
      this.inventoryContents = new ItemStack[integer];
   }

   public InventoryBasic(IChatComponent iChatComponent, int integer) {
      this(iChatComponent.getUnformattedText(), true, integer);
   }

   public void addInventoryChangeListener(IInvBasic iInvBasic) {
      if (this.changeListeners == null) {
         this.changeListeners = Lists.newArrayList();
      }

      this.changeListeners.add(iInvBasic);
   }

   public void removeInventoryChangeListener(IInvBasic iInvBasic) {
      this.changeListeners.remove(iInvBasic);
   }

   @Override
   public ItemStack getStackInSlot(int integer) {
      return integer >= 0 && integer < this.inventoryContents.length ? this.inventoryContents[integer] : null;
   }

   @Override
   public ItemStack decrStackSize(int integer1, int integer2) {
      if (this.inventoryContents[integer1] != null) {
         if (this.inventoryContents[integer1].stackSize <= integer2) {
            ItemStack var7 = this.inventoryContents[integer1];
            this.inventoryContents[integer1] = null;
            this.markDirty();
            return var7;
         }

         ItemStack var6 = this.inventoryContents[integer1].splitStack(integer2);
         if (this.inventoryContents[integer1].stackSize == 0) {
            this.inventoryContents[integer1] = null;
         }

         this.markDirty();
         return var6;
      } else {
         return null;
      }
   }

   public ItemStack func_174894_a(ItemStack itemStack) {
      ItemStack var5 = itemStack.copy();

      for (int var6 = 0; var6 < this.slotsCount; var6++) {
         ItemStack var7 = this.getStackInSlot(var6);
         if (var7 == null) {
            this.setInventorySlotContents(var6, var5);
            this.markDirty();
            return null;
         }

         if (ItemStack.areItemsEqual(var7, var5)) {
            int var8 = Math.min(this.getInventoryStackLimit(), var7.getMaxStackSize());
            int var9 = Math.min(var5.stackSize, var8 - var7.stackSize);
            if (var9 > 0) {
               var7.stackSize += var9;
               var5.stackSize -= var9;
               if (var5.stackSize <= 0) {
                  this.markDirty();
                  return null;
               }
            }
         }
      }

      if (var5.stackSize != itemStack.stackSize) {
         this.markDirty();
      }

      return var5;
   }

   @Override
   public ItemStack removeStackFromSlot(int integer) {
      if (this.inventoryContents[integer] != null) {
         ItemStack var5 = this.inventoryContents[integer];
         this.inventoryContents[integer] = null;
         return var5;
      } else {
         return null;
      }
   }

   @Override
   public void setInventorySlotContents(int integer, ItemStack itemStack) {
      this.inventoryContents[integer] = itemStack;
      if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
         itemStack.stackSize = this.getInventoryStackLimit();
      }

      this.markDirty();
   }

   @Override
   public int getSizeInventory() {
      return this.slotsCount;
   }

   @Override
   public String getName() {
      return this.inventoryTitle;
   }

   @Override
   public boolean hasCustomName() {
      return this.hasCustomName;
   }

   public void setCustomName(String string) {
      this.hasCustomName = true;
      this.inventoryTitle = string;
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
   public void markDirty() {
      if (this.changeListeners != null) {
         Iterator var4 = this.changeListeners.iterator();

         while (var4.hasNext()) {
            ((IInvBasic)var4.next()).onInventoryChanged(this);
         }
      }
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
      Arrays.fill(this.inventoryContents, null);
   }
}
