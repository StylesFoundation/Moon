package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C00PacketKeepAlive implements Packet<INetHandlerPlayServer> {
   private int key;

   public C00PacketKeepAlive() {
   }

   public C00PacketKeepAlive(int integer) {
      this.key = integer;
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processKeepAlive(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.key = buffer.readVarIntFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.key);
   }

   public int Method657() {
      return this.key;
   }

   public C00PacketKeepAlive Method658(int integer) {
      this.key = integer;
      return this;
   }
}
