package com.moon.connection.handlers.impl;

import com.moon.connection.Gateway;
import com.moon.connection.handlers.PacketHandler;
import com.moon.connection.protobuf.chat.ClientboundChatError;
import java.nio.ByteBuffer;

public class ClientboundChatErrorHandler extends PacketHandler<ClientboundChatError> {
   public ClientboundChatErrorHandler(Gateway gateway) {
      super(gateway, (byte)2);
   }

   @Override
   public void handle(ByteBuffer buffer) {
      ClientboundChatError chatError = this.parseMessage(buffer, ClientboundChatError.getDefaultInstance());
      this.getGateway().getConfig().getChatCallback().accept("<red>" + chatError.getMessage());
   }
}
