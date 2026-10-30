package net.minecraft.network.handshake.client;

import java.io.IOException;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;

public class C00Handshake implements Packet<INetHandlerHandshakeServer> {
   private int protocolVersion;
   private String ip;
   private int port;
   private EnumConnectionState requestedState;

   public C00Handshake() {
   }

   public C00Handshake(int integer1, String string, int integer3, EnumConnectionState enumConnectionState) {
      this.protocolVersion = integer1;
      this.ip = string;
      this.port = integer3;
      this.requestedState = enumConnectionState;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.protocolVersion = buffer.readVarIntFromBuffer();
      this.ip = buffer.readStringFromBuffer(255);
      this.port = buffer.readUnsignedShort();
      this.requestedState = EnumConnectionState.getById(buffer.readVarIntFromBuffer());
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.protocolVersion);
      buffer.writeString(this.ip);
      buffer.writeShort(this.port);
      buffer.writeVarIntToBuffer(this.requestedState.getId());
   }

   public void processPacket(INetHandlerHandshakeServer handler) {
      handler.processHandshake(this);
   }

   public EnumConnectionState getRequestedState() {
      return this.requestedState;
   }

   public int getProtocolVersion() {
      return this.protocolVersion;
   }
}
