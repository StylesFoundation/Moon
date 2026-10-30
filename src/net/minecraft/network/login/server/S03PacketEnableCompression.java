package net.minecraft.network.login.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

public class S03PacketEnableCompression implements Packet<INetHandlerLoginClient> {
   private int compressionTreshold;

   public S03PacketEnableCompression() {
   }

   public S03PacketEnableCompression(int integer) {
      this.compressionTreshold = integer;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.compressionTreshold = buffer.readVarIntFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.compressionTreshold);
   }

   public void processPacket(INetHandlerLoginClient handler) {
      handler.handleEnableCompression(this);
   }

   public int getCompressionTreshold() {
      return this.compressionTreshold;
   }
}
