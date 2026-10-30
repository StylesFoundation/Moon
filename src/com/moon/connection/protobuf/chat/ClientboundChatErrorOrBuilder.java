package com.moon.connection.protobuf.chat;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ClientboundChatErrorOrBuilder extends MessageOrBuilder {
   String getMessage();

   ByteString getMessageBytes();
}
