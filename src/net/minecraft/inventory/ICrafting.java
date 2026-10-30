package net.minecraft.inventory;

import java.util.List;
import net.minecraft.item.ItemStack;

public interface ICrafting {
   void updateCraftingInventory(Container container, List<ItemStack> list);

   void sendSlotContents(Container container, int integer, ItemStack itemStack);

   void sendProgressBarUpdate(Container container, int integer2, int integer3);

   void sendAllWindowProperties(Container container, IInventory iInventory);
}
