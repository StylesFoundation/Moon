package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0DPacketCloseWindow implements Packet<INetHandlerPlayServer> {
   private int windowId;

   public C0DPacketCloseWindow() {
   }

   public C0DPacketCloseWindow(int integer) {
      this.windowId = integer;
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processCloseWindow(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.windowId = buffer.readByte();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.windowId);
   }

   public int Method775() {
      return this.windowId;
   }

   public C0DPacketCloseWindow Method776(int integer) {
      this.windowId = integer;
      return this;
   }
}
