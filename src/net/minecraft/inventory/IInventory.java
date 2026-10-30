package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IWorldNameable;

public interface IInventory extends IWorldNameable {
   int getSizeInventory();

   ItemStack getStackInSlot(int integer);

   ItemStack decrStackSize(int integer1, int integer2);

   ItemStack removeStackFromSlot(int integer);

   void setInventorySlotContents(int integer, ItemStack itemStack);

   int getInventoryStackLimit();

   void markDirty();

   boolean isUseableByPlayer(EntityPlayer entityPlayer);

   void openInventory(EntityPlayer entityPlayer);

   void closeInventory(EntityPlayer entityPlayer);

   boolean isItemValidForSlot(int integer, ItemStack itemStack);

   int getField(int integer);

   void setField(int integer1, int integer2);

   int getFieldCount();

   void clear();
}
