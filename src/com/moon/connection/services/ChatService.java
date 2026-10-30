package com.moon.connection.services;

import com.moon.connection.Gateway;
import com.moon.connection.protobuf.chat.ServerboundChatMessage;

public class ChatService {
   private final Gateway gateway;

   public void sendMessage(String message) {
      this.gateway.getClient().send(ServerboundChatMessage.newBuilder().setMessage(message).build());
   }

   public ChatService(Gateway gateway) {
      this.gateway = gateway;
   }
}
