package net.minecraft.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface ISidedInventory extends IInventory {
   int[] getSlotsForFace(EnumFacing enumFacing);

   boolean canInsertItem(int integer, ItemStack itemStack, EnumFacing enumFacing);

   boolean canExtractItem(int integer, ItemStack itemStack, EnumFacing enumFacing);
}
