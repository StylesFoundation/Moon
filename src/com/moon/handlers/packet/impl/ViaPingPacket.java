package com.moon.handlers.packet.impl;

import com.moon.handlers.packet.MoonPacket;
import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;

public class ViaPingPacket extends MoonPacket {
   private final int protocolVersion;
   private final int transactionId;

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.protocolVersion);
      buffer.writeInt(this.transactionId);
   }

   @Override
   public void processPacket(INetHandler handler) {
   }

   public int getProtocolVersion() {
      return this.protocolVersion;
   }

   public int getTransactionId() {
      return this.transactionId;
   }

   public ViaPingPacket(int protocolVersion, int transactionId) {
      this.protocolVersion = protocolVersion;
      this.transactionId = transactionId;
   }
}
