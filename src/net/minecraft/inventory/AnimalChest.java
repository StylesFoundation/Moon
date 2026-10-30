package net.minecraft.inventory;

import net.minecraft.util.IChatComponent;

public class AnimalChest extends InventoryBasic {
   public AnimalChest(String string, int integer) {
      super(string, false, integer);
   }

   public AnimalChest(IChatComponent iChatComponent, int integer) {
      super(iChatComponent, integer);
   }
}
