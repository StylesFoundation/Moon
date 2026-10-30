package net.minecraft.network;

import java.io.IOException;

public interface Packet<T extends INetHandler> {
   void readPacketData(PacketBuffer buffer) throws IOException;

   void writePacketData(PacketBuffer buffer) throws IOException;

   void processPacket(T handler);
}
