package com.moon.connection.handlers.impl;

import com.moon.connection.Gateway;
import com.moon.connection.cipher.GatewayCipher;
import com.moon.connection.handlers.PacketHandler;
import com.moon.connection.protobuf.authentication.ClientboundAuthenticationResponse;
import java.nio.ByteBuffer;

public class ClientboundAuthenticationResponseHandler extends PacketHandler<ClientboundAuthenticationResponse> {
   public ClientboundAuthenticationResponseHandler(Gateway gateway) {
      super(gateway, (byte)1);
   }

   @Override
   public void handle(ByteBuffer buffer) {
      ClientboundAuthenticationResponse response = this.parseMessage(buffer, ClientboundAuthenticationResponse.getDefaultInstance());
      if (response.getSuccess()) {
         this.getGateway().cipher(new GatewayCipher(response.getTrafficKey(), response.getTrafficIv()));
         this.getGateway().getConfig().getReconnectionCallback().accept(null);
      }
   }

   public static int dummy() {
      return 39;
   }

   static {
      if (dummy() == 0) {
      }
   }
}
