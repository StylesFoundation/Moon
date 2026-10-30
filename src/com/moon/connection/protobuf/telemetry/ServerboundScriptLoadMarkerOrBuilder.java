package com.moon.connection.protobuf.telemetry;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ServerboundScriptLoadMarkerOrBuilder extends MessageOrBuilder {
   String getName();

   ByteString getNameBytes();
}
