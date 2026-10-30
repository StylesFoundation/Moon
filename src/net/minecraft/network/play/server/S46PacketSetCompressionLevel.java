package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S46PacketSetCompressionLevel implements Packet<INetHandlerPlayClient> {
   private int threshold;

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.threshold = buffer.readVarIntFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.threshold);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleSetCompressionLevel(this);
   }

   public int getThreshold() {
      return this.threshold;
   }
}
