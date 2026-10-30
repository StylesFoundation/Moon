package com.moon.connection.packet;

import com.moon.connection.handlers.PacketHandler;
import java.util.HashMap;
import java.util.Map;

public class PacketManager {
   private final Map<Byte, PacketHandler> handlers = new HashMap<>();

   public void register(PacketHandler handler) {
      this.handlers.put(handler.getPacketId(), handler);
   }

   public PacketHandler getHandler(byte packetId) {
      return this.handlers.get(packetId);
   }
}
