package com.moon.connection.protobuf.cloud;

import com.google.protobuf.MessageOrBuilder;

public interface ServerboundCloudQueryRequestOrBuilder extends MessageOrBuilder {
   int getTypeValue();

   CloudStorageType getType();
}
