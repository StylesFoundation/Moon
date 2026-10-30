package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S00PacketKeepAlive implements Packet<INetHandlerPlayClient> {
   private int id;

   public S00PacketKeepAlive() {
   }

   public S00PacketKeepAlive(int integer) {
      this.id = integer;
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleKeepAlive(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.id = buffer.readVarIntFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.id);
   }

   public int func_149134_c() {
      return this.id;
   }

   public int Method894() {
      return this.id;
   }

   public S00PacketKeepAlive Method895(int integer) {
      this.id = integer;
      return this;
   }
}
