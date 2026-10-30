package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S2FPacketSetSlot implements Packet<INetHandlerPlayClient> {
   private int windowId;
   private int slot;
   private ItemStack item;

   public S2FPacketSetSlot() {
   }

   public S2FPacketSetSlot(int integer1, int integer2, ItemStack itemStack) {
      this.windowId = integer1;
      this.slot = integer2;
      this.item = itemStack == null ? null : itemStack.copy();
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleSetSlot(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.windowId = buffer.readByte();
      this.slot = buffer.readShort();
      this.item = buffer.readItemStackFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.windowId);
      buffer.writeShort(this.slot);
      buffer.writeItemStackToBuffer(this.item);
   }

   public int func_149175_c() {
      return this.windowId;
   }

   public int func_149173_d() {
      return this.slot;
   }

   public ItemStack func_149174_e() {
      return this.item;
   }
}
