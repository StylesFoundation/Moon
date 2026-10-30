package net.minecraft.network.status.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusServer;

public class C01PacketPing implements Packet<INetHandlerStatusServer> {
   private long clientTime;

   public C01PacketPing() {
   }

   public C01PacketPing(long long1) {
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

   public void processPacket(INetHandlerStatusServer handler) {
      handler.processPing(this);
   }

   public long getClientTime() {
      return this.clientTime;
   }
}
