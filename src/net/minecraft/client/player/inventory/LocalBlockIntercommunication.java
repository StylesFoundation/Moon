package net.minecraft.client.player.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;

public class LocalBlockIntercommunication implements IInteractionObject {
   private final String guiID;
   private final IChatComponent displayName;

   public LocalBlockIntercommunication(String string, IChatComponent iChatComponent) {
      this.guiID = string;
      this.displayName = iChatComponent;
   }

   @Override
   public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
      throw new UnsupportedOperationException();
   }

   @Override
   public String getName() {
      return this.displayName.getUnformattedText();
   }

   @Override
   public boolean hasCustomName() {
      return true;
   }

   @Override
   public String getGuiID() {
      return this.guiID;
   }

   @Override
   public IChatComponent getDisplayName() {
      return this.displayName;
   }
}
