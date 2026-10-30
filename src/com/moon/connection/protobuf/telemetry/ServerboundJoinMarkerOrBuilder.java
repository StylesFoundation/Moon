package com.moon.connection.protobuf.telemetry;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ServerboundJoinMarkerOrBuilder extends MessageOrBuilder {
   String getServer();

   ByteString getServerBytes();
}
