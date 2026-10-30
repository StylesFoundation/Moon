package com.moon.connection.handlers.impl;

import com.moon.connection.Gateway;
import com.moon.connection.handlers.PacketHandler;
import com.moon.connection.protobuf.sync.ClientboundUpdateUserSync;
import java.nio.ByteBuffer;

public class ClientboundUpdateUserSyncHandler extends PacketHandler<ClientboundUpdateUserSync> {
   public ClientboundUpdateUserSyncHandler(Gateway gateway) {
      super(gateway, (byte)4);
   }

   @Override
   public void handle(ByteBuffer buffer) {
      ClientboundUpdateUserSync userSync = this.parseMessage(buffer, ClientboundUpdateUserSync.getDefaultInstance());
      this.getGateway().getConfig().getSynchronizer().accept(() -> this.getGateway().getServices().userSync().updateSynchronizedUsers(userSync.getUsersList()));
   }
}
