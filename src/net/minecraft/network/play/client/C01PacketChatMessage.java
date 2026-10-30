package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C01PacketChatMessage implements Packet<INetHandlerPlayServer> {
   private String message;

   public C01PacketChatMessage() {
   }

   public C01PacketChatMessage(String string) {
      if (string.length() > 100) {
         string = string.substring(0, 100);
      }

      this.message = string;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.message = buffer.readStringFromBuffer(100);
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeString(this.message);
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processChatMessage(this);
   }

   public String getMessage() {
      return this.message;
   }

   public C01PacketChatMessage Method354(String string) {
      this.message = string;
      return this;
   }
}
