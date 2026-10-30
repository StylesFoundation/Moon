package com.moon.connection.protobuf.sync;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface SynchronizedUserOrBuilder extends MessageOrBuilder {
   String getName();

   ByteString getNameBytes();

   String getGameName();

   ByteString getGameNameBytes();

   String getServer();

   ByteString getServerBytes();

   String getColor();

   ByteString getColorBytes();
}
