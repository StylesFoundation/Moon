package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S02PacketChat implements Packet<INetHandlerPlayClient> {
   private IChatComponent chatComponent;
   private byte type;

   public S02PacketChat() {
   }

   public S02PacketChat(IChatComponent iChatComponent) {
      this(iChatComponent, (byte)1);
   }

   public S02PacketChat(IChatComponent iChatComponent, byte byte2) {
      this.chatComponent = iChatComponent;
      this.type = byte2;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.chatComponent = buffer.readChatComponent();
      this.type = buffer.readByte();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeChatComponent(this.chatComponent);
      buffer.writeByte(this.type);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleChat(this);
   }

   public IChatComponent getChatComponent() {
      return this.chatComponent;
   }

   public boolean isChat() {
      return this.type == 1 || this.type == 2;
   }

   public byte getType() {
      return this.type;
   }
}
