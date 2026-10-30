package com.moon.connection.handlers.impl;

import com.moon.connection.Gateway;
import com.moon.connection.handlers.PacketHandler;
import com.moon.connection.protobuf.cloud.ClientboundCloudDownloadResponse;
import java.nio.ByteBuffer;

public class ClientboundCloudDownloadResponseHandler extends PacketHandler<ClientboundCloudDownloadResponse> {
   public ClientboundCloudDownloadResponseHandler(Gateway gateway) {
      super(gateway, (byte)6);
   }

   @Override
   public void handle(ByteBuffer buffer) {
   }
}
