package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C11PacketEnchantItem implements Packet<INetHandlerPlayServer> {
   private int windowId;
   private int button;

   public C11PacketEnchantItem() {
   }

   public C11PacketEnchantItem(int integer1, int integer2) {
      this.windowId = integer1;
      this.button = integer2;
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processEnchantItem(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.windowId = buffer.readByte();
      this.button = buffer.readByte();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.windowId);
      buffer.writeByte(this.button);
   }

   public int getWindowId() {
      return this.windowId;
   }

   public int getButton() {
      return this.button;
   }

   public int Method500() {
      return this.windowId;
   }

   public int Method501() {
      return this.button;
   }

   public C11PacketEnchantItem Method502(int integer) {
      this.windowId = integer;
      return this;
   }

   public C11PacketEnchantItem Method503(int integer) {
      this.button = integer;
      return this;
   }
}
