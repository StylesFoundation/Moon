package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C17PacketCustomPayload implements Packet<INetHandlerPlayServer> {
   private String channel;
   private PacketBuffer data;

   public C17PacketCustomPayload() {
   }

   public C17PacketCustomPayload(String string, PacketBuffer packetBuffer) {
      this.channel = string;
      this.data = packetBuffer;
      if (packetBuffer.writerIndex() > 32767) {
         throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.channel = buffer.readStringFromBuffer(20);
      int var5 = buffer.readableBytes();
      if (var5 >= 0 && var5 <= 32767) {
         this.data = new PacketBuffer(buffer.readBytes(var5));
      } else {
         throw new IOException("Payload may not be larger than 32767 bytes");
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeString(this.channel);
      buffer.writeBytes(this.data);
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processVanilla250Packet(this);
   }

   public String getChannelName() {
      return this.channel;
   }

   public PacketBuffer getBufferData() {
      return this.data;
   }
}
