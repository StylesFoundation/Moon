package net.minecraft.network.status.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusClient;

public class S01PacketPong implements Packet<INetHandlerStatusClient> {
   private long clientTime;

   public S01PacketPong() {
   }

   public S01PacketPong(long long1) {
      this.clientTime = long1;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.clientTime = buffer.readLong();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeLong(this.clientTime);
   }

   public void processPacket(INetHandlerStatusClient handler) {
      handler.handlePong(this);
   }
}
