package com.moon.connection.protobuf.cloud;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface CloudFileUploaderOrBuilder extends MessageOrBuilder {
   long getUid();

   String getName();

   ByteString getNameBytes();

   String getRank();

   ByteString getRankBytes();

   String getRankColor();

   ByteString getRankColorBytes();
}
