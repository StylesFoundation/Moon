package com.moon.connection.handlers.impl;

import com.moon.connection.Gateway;
import com.moon.connection.handlers.PacketHandler;
import com.moon.connection.protobuf.chat.ClientboundChatBroadcast;
import java.nio.ByteBuffer;

public class ClientboundChatBroadcastHandler extends PacketHandler<ClientboundChatBroadcast> {
   public ClientboundChatBroadcastHandler(Gateway gateway) {
      super(gateway, (byte)3);
   }

   @Override
   public void handle(ByteBuffer buffer) {
      ClientboundChatBroadcast broadcast = this.parseMessage(buffer, ClientboundChatBroadcast.getDefaultInstance());
      String formattedMessage = String.format(
         "<%s>%s</%s> <gray>(%s) » <white>%s", broadcast.getSenderColor(), broadcast.getSenderName(), broadcast.getSenderColor(), broadcast.getSenderId(), broadcast.getMessage()
      );
      this.getGateway().getConfig().getChatCallback().accept(formattedMessage);
   }
}
