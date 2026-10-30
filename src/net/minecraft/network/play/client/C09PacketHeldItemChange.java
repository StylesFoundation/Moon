package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C09PacketHeldItemChange implements Packet<INetHandlerPlayServer> {
   private int slotId;

   public C09PacketHeldItemChange() {
   }

   public C09PacketHeldItemChange(int integer) {
      this.slotId = integer;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.slotId = buffer.readShort();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeShort(this.slotId);
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processHeldItemChange(this);
   }

   public int getSlotId() {
      return this.slotId;
   }

   public int Method896() {
      return this.slotId;
   }

   public C09PacketHeldItemChange Method897(int integer) {
      this.slotId = integer;
      return this;
   }
}
