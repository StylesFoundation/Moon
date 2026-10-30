package net.minecraft.client.player.inventory;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class ContainerLocalMenu extends InventoryBasic implements ILockableContainer {
   private final String guiID;
   private final Map<Integer, Integer> field_174895_b = Maps.newHashMap();

   public ContainerLocalMenu(String string, IChatComponent iChatComponent, int integer) {
      super(iChatComponent, integer);
      this.guiID = string;
   }

   @Override
   public int getField(int integer) {
      return this.field_174895_b.getOrDefault(integer, 0);
   }

   @Override
   public void setField(int integer1, int integer2) {
      this.field_174895_b.put(integer1, integer2);
   }

   @Override
   public int getFieldCount() {
      return this.field_174895_b.size();
   }

   @Override
   public boolean isLocked() {
      return false;
   }

   @Override
   public void setLockCode(LockCode lockCode) {
   }

   @Override
   public LockCode getLockCode() {
      return LockCode.EMPTY_CODE;
   }

   @Override
   public String getGuiID() {
      return this.guiID;
   }

   @Override
   public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
      throw new UnsupportedOperationException();
   }
}
