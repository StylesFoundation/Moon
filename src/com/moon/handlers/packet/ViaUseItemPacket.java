package com.moon.handlers.packet;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;

public class ViaUseItemPacket extends MoonPacket {
   private final int sequence;
   private final int hand;

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.sequence);
      buffer.writeVarIntToBuffer(this.hand);
   }

   @Override
   public void processPacket(INetHandler handler) {
   }

   public ViaUseItemPacket(int sequence, int hand) {
      this.sequence = sequence;
      this.hand = hand;
   }
}
