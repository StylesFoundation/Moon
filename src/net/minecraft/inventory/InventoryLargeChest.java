package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class InventoryLargeChest implements ILockableContainer {
   private final String name;
   private final ILockableContainer upperChest;
   private final ILockableContainer lowerChest;

   public InventoryLargeChest(String string, ILockableContainer iLockableContainer2, ILockableContainer iLockableContainer3) {
      this.name = string;
      if (iLockableContainer2 == null) {
         iLockableContainer2 = iLockableContainer3;
      }

      if (iLockableContainer3 == null) {
         iLockableContainer3 = iLockableContainer2;
      }

      this.upperChest = iLockableContainer2;
      this.lowerChest = iLockableContainer3;
      if (iLockableContainer2.isLocked()) {
         iLockableContainer3.setLockCode(iLockableContainer2.getLockCode());
      } else if (iLockableContainer3.isLocked()) {
         iLockableContainer2.setLockCode(iLockableContainer3.getLockCode());
      }
   }

   @Override
   public int getSizeInventory() {
      return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
   }

   public boolean isPartOfLargeChest(IInventory iInventory) {
      return this.upperChest == iInventory || this.lowerChest == iInventory;
   }

   @Override
   public String getName() {
      return this.upperChest.hasCustomName() ? this.upperChest.getName() : (this.lowerChest.hasCustomName() ? this.lowerChest.getName() : this.name);
   }

   @Override
   public boolean hasCustomName() {
      return this.upperChest.hasCustomName() || this.lowerChest.hasCustomName();
   }

   @Override
   public IChatComponent getDisplayName() {
      return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
   }

   @Override
   public ItemStack getStackInSlot(int integer) {
      return integer >= this.upperChest.getSizeInventory()
         ? this.lowerChest.getStackInSlot(integer - this.upperChest.getSizeInventory())
         : this.upperChest.getStackInSlot(integer);
   }

   @Override
   public ItemStack decrStackSize(int integer1, int integer2) {
      return integer1 >= this.upperChest.getSizeInventory()
         ? this.lowerChest.decrStackSize(integer1 - this.upperChest.getSizeInventory(), integer2)
         : this.upperChest.decrStackSize(integer1, integer2);
   }

   @Override
   public ItemStack removeStackFromSlot(int integer) {
      return integer >= this.upperChest.getSizeInventory()
         ? this.lowerChest.removeStackFromSlot(integer - this.upperChest.getSizeInventory())
         : this.upperChest.removeStackFromSlot(integer);
   }

   @Override
   public void setInventorySlotContents(int integer, ItemStack itemStack) {
      if (integer >= this.upperChest.getSizeInventory()) {
         this.lowerChest.setInventorySlotContents(integer - this.upperChest.getSizeInventory(), itemStack);
      } else {
         this.upperChest.setInventorySlotContents(integer, itemStack);
      }
   }

   @Override
   public int getInventoryStackLimit() {
      return this.upperChest.getInventoryStackLimit();
   }

   @Override
   public void markDirty() {
      this.upperChest.markDirty();
      this.lowerChest.markDirty();
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
      return this.upperChest.isUseableByPlayer(entityPlayer) && this.lowerChest.isUseableByPlayer(entityPlayer);
   }

   @Override
   public void openInventory(EntityPlayer entityPlayer) {
      this.upperChest.openInventory(entityPlayer);
      this.lowerChest.openInventory(entityPlayer);
   }

   @Override
   public void closeInventory(EntityPlayer entityPlayer) {
      this.upperChest.closeInventory(entityPlayer);
      this.lowerChest.closeInventory(entityPlayer);
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
   public boolean isLocked() {
      return this.upperChest.isLocked() || this.lowerChest.isLocked();
   }

   @Override
   public void setLockCode(LockCode lockCode) {
      this.upperChest.setLockCode(lockCode);
      this.lowerChest.setLockCode(lockCode);
   }

   @Override
   public LockCode getLockCode() {
      return this.upperChest.getLockCode();
   }

   @Override
   public String getGuiID() {
      return this.upperChest.getGuiID();
   }

   @Override
   public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
      return new ContainerChest(inventoryPlayer, this, entityPlayer);
   }

   @Override
   public void clear() {
      this.upperChest.clear();
      this.lowerChest.clear();
   }
}
