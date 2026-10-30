package com.moon.connection.protobuf.cloud;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ServerboundCloudUploadRequestOrBuilder extends MessageOrBuilder {
   int getTypeValue();

   CloudStorageType getType();

   String getDisplayName();

   ByteString getDisplayNameBytes();

   String getContentEncoded();

   ByteString getContentEncodedBytes();
}
