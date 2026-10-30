package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0APacketAnimation implements Packet<INetHandlerPlayServer> {
   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.handleAnimation(this);
   }
}
