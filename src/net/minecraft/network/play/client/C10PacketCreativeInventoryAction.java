package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C10PacketCreativeInventoryAction implements Packet<INetHandlerPlayServer> {
   private int slotId;
   private ItemStack stack;

   public C10PacketCreativeInventoryAction() {
   }

   public C10PacketCreativeInventoryAction(int integer, ItemStack itemStack) {
      this.slotId = integer;
      this.stack = itemStack != null ? itemStack.copy() : null;
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processCreativeInventoryAction(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.slotId = buffer.readShort();
      this.stack = buffer.readItemStackFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeShort(this.slotId);
      buffer.writeItemStackToBuffer(this.stack);
   }

   public int getSlotId() {
      return this.slotId;
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public int Method208() {
      return this.slotId;
   }

   public ItemStack Method209() {
      return this.stack;
   }

   public C10PacketCreativeInventoryAction Method210(int integer) {
      this.slotId = integer;
      return this;
   }

   public C10PacketCreativeInventoryAction Method211(ItemStack itemStack) {
      this.stack = itemStack;
      return this;
   }
}
