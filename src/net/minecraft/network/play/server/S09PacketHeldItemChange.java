package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S09PacketHeldItemChange implements Packet<INetHandlerPlayClient> {
   private int heldItemHotbarIndex;

   public S09PacketHeldItemChange() {
   }

   public S09PacketHeldItemChange(int integer) {
      this.heldItemHotbarIndex = integer;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.heldItemHotbarIndex = buffer.readByte();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.heldItemHotbarIndex);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleHeldItemChange(this);
   }

   public int getHeldItemHotbarIndex() {
      return this.heldItemHotbarIndex;
   }
}
