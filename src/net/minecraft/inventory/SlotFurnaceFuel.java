package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotFurnaceFuel extends Slot {
   public SlotFurnaceFuel(IInventory iInventory, int integer2, int integer3, int integer4) {
      super(iInventory, integer2, integer3, integer4);
   }

   @Override
   public boolean isItemValid(ItemStack itemStack) {
      return TileEntityFurnace.isItemFuel(itemStack) || isBucket(itemStack);
   }

   @Override
   public int getItemStackLimit(ItemStack itemStack) {
      return isBucket(itemStack) ? 1 : super.getItemStackLimit(itemStack);
   }

   public static boolean isBucket(ItemStack itemStack) {
      return itemStack != null && itemStack.getItem() != null && itemStack.getItem() == Items.bucket;
   }
}
