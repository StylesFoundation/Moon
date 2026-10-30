package com.moon.connection.protobuf.cloud;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ServerboundCloudVotingRequestOrBuilder extends MessageOrBuilder {
   String getId();

   ByteString getIdBytes();

   boolean getPositive();
}
