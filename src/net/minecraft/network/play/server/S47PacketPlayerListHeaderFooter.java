package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S47PacketPlayerListHeaderFooter implements Packet<INetHandlerPlayClient> {
   private IChatComponent header;
   private IChatComponent footer;

   public S47PacketPlayerListHeaderFooter() {
   }

   public S47PacketPlayerListHeaderFooter(IChatComponent iChatComponent) {
      this.header = iChatComponent;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.header = buffer.readChatComponent();
      this.footer = buffer.readChatComponent();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeChatComponent(this.header);
      buffer.writeChatComponent(this.footer);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handlePlayerListHeaderFooter(this);
   }

   public IChatComponent getHeader() {
      return this.header;
   }

   public IChatComponent getFooter() {
      return this.footer;
   }
}
