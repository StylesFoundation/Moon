package net.minecraft.client.gui.inventory;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class CreativeCrafting implements ICrafting {
   private final Minecraft mc;

   public CreativeCrafting(Minecraft minecraft) {
      this.mc = minecraft;
   }

   @Override
   public void updateCraftingInventory(Container container, List<ItemStack> list) {
   }

   @Override
   public void sendSlotContents(Container container, int integer, ItemStack itemStack) {
      this.mc.playerController.sendSlotPacket(itemStack, integer);
   }

   @Override
   public void sendProgressBarUpdate(Container container, int integer2, int integer3) {
   }

   @Override
   public void sendAllWindowProperties(Container container, IInventory iInventory) {
   }
}
