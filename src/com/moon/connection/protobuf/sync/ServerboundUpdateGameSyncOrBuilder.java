package com.moon.connection.protobuf.sync;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ServerboundUpdateGameSyncOrBuilder extends MessageOrBuilder {
   String getGameName();

   ByteString getGameNameBytes();

   String getGameServer();

   ByteString getGameServerBytes();
}
