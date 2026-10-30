package com.moon.connection.protobuf.cloud;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ServerboundCloudUpdateDisplayNameRequestOrBuilder extends MessageOrBuilder {
   String getId();

   ByteString getIdBytes();

   String getNewName();

   ByteString getNewNameBytes();
}
