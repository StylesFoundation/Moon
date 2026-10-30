package com.moon.connection.protobuf.cloud;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ServerboundCloudDownloadRequestOrBuilder extends MessageOrBuilder {
   String getId();

   ByteString getIdBytes();
}
