package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S2EPacketCloseWindow implements Packet<INetHandlerPlayClient> {
   private int windowId;

   public S2EPacketCloseWindow() {
   }

   public S2EPacketCloseWindow(int integer) {
      this.windowId = integer;
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleCloseWindow(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.windowId = buffer.readUnsignedByte();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.windowId);
   }
}
