package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S3FPacketCustomPayload implements Packet<INetHandlerPlayClient> {
   private String channel;
   private PacketBuffer data;

   public S3FPacketCustomPayload() {
   }

   public S3FPacketCustomPayload(String string, PacketBuffer packetBuffer) {
      this.channel = string;
      this.data = packetBuffer;
      if (packetBuffer.writerIndex() > 1048576) {
         throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.channel = buffer.readStringFromBuffer(20);
      int var5 = buffer.readableBytes();
      if (var5 >= 0 && var5 <= 1048576) {
         this.data = new PacketBuffer(buffer.readBytes(var5));
      } else {
         throw new IOException("Payload may not be larger than 1048576 bytes");
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeString(this.channel);
      buffer.writeBytes(this.data);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleCustomPayload(this);
   }

   public String getChannelName() {
      return this.channel;
   }

   public PacketBuffer getBufferData() {
      return this.data;
   }
}
