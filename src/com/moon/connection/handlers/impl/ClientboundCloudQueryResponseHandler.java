package com.moon.connection.handlers.impl;

import com.moon.connection.Gateway;
import com.moon.connection.handlers.PacketHandler;
import com.moon.connection.protobuf.cloud.ClientboundCloudQueryResponse;
import java.nio.ByteBuffer;

public class ClientboundCloudQueryResponseHandler extends PacketHandler<ClientboundCloudQueryResponse> {
   public ClientboundCloudQueryResponseHandler(Gateway gateway) {
      super(gateway, (byte)7);
   }

   @Override
   public void handle(ByteBuffer buffer) {
   }
}
