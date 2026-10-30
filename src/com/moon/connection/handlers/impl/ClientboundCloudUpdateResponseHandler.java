package com.moon.connection.handlers.impl;

import com.moon.connection.Gateway;
import com.moon.connection.handlers.PacketHandler;
import com.moon.connection.protobuf.cloud.ClientboundCloudUpdateResponse;
import java.nio.ByteBuffer;

public class ClientboundCloudUpdateResponseHandler extends PacketHandler<ClientboundCloudUpdateResponse> {
   public ClientboundCloudUpdateResponseHandler(Gateway gateway) {
      super(gateway, (byte)5);
   }

   @Override
   public void handle(ByteBuffer buffer) {
      ClientboundCloudUpdateResponse response = this.parseMessage(buffer, ClientboundCloudUpdateResponse.getDefaultInstance());
      System.out.println(response.getMessage() + " " + response.getId());
   }
}
