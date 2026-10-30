package com.moon.connection.protobuf.authentication;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ServerboundAuthenticationRequestOrBuilder extends MessageOrBuilder {
   String getSecret();

   ByteString getSecretBytes();
}
