package com.moon.connection.handlers;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.moon.connection.Gateway;
import java.nio.ByteBuffer;

public abstract class PacketHandler<T> {
   private final Gateway gateway;
   private final byte packetId;

   public abstract void handle(ByteBuffer buffer);

   public T parseMessage(ByteBuffer buffer, GeneratedMessageV3 defaultInstance) {
      try {
         return (T)defaultInstance.getParserForType().parseFrom(buffer);
      } catch (InvalidProtocolBufferException e) {
         e.printStackTrace();
         return null;
      }
   }

   public Gateway getGateway() {
      return this.gateway;
   }

   public byte getPacketId() {
      return this.packetId;
   }

   public PacketHandler(Gateway gateway, byte packetId) {
      this.gateway = gateway;
      this.packetId = packetId;
   }
}
