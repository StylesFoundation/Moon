package com.moon.connection.protobuf.cloud;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ClientboundCloudUpdateResponseOrBuilder extends MessageOrBuilder {
   String getId();

   ByteString getIdBytes();

   String getMessage();

   ByteString getMessageBytes();
}
