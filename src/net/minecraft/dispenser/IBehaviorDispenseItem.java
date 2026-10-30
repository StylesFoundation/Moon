package net.minecraft.dispenser;

import net.minecraft.item.ItemStack;

public interface IBehaviorDispenseItem {
   IBehaviorDispenseItem a = (iBlockSource, itemStack) -> itemStack;

   ItemStack dispense(IBlockSource iBlockSource, ItemStack itemStack);
}
