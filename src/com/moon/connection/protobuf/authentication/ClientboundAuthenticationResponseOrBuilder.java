package com.moon.connection.protobuf.authentication;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ClientboundAuthenticationResponseOrBuilder extends MessageOrBuilder {
   boolean getSuccess();

   String getTrafficKey();

   ByteString getTrafficKeyBytes();

   String getTrafficIv();

   ByteString getTrafficIvBytes();

   boolean hasError();

   String getError();

   ByteString getErrorBytes();
}
