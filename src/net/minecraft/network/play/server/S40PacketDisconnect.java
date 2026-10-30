package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S40PacketDisconnect implements Packet<INetHandlerPlayClient> {
   private IChatComponent reason;

   public S40PacketDisconnect() {
   }

   public S40PacketDisconnect(IChatComponent iChatComponent) {
      this.reason = iChatComponent;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.reason = buffer.readChatComponent();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeChatComponent(this.reason);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleDisconnect(this);
   }

   public IChatComponent getReason() {
      return this.reason;
   }
}
