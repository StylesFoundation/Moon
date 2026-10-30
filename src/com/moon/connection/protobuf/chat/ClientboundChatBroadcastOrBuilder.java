package com.moon.connection.protobuf.chat;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ClientboundChatBroadcastOrBuilder extends MessageOrBuilder {
   long getSenderId();

   String getSenderName();

   ByteString getSenderNameBytes();

   String getSenderColor();

   ByteString getSenderColorBytes();

   String getMessage();

   ByteString getMessageBytes();
}
