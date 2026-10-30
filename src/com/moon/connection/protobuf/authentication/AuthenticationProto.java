package com.moon.connection.protobuf.authentication;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;

public final class AuthenticationProto {
   static final Descriptor internal_static_ServerboundAuthenticationRequest_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(0);
   static final FieldAccessorTable internal_static_ServerboundAuthenticationRequest_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundAuthenticationRequest_descriptor, new String[]{"Secret"}
   );
   static final Descriptor internal_static_ClientboundAuthenticationResponse_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(1);
   static final FieldAccessorTable internal_static_ClientboundAuthenticationResponse_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ClientboundAuthenticationResponse_descriptor, new String[]{"Success", "TrafficKey", "TrafficIv", "Error", "Error"}
   );
   private static FileDescriptor descriptor = FileDescriptor.internalBuildGeneratedFileFrom(
      new String[]{
         "\n\u0014authentication.proto\"2\n ServerboundAuthenticationRequest\u0012\u000e\n\u0006secret\u0018\u0001 \u0001(\t\"{\n!ClientboundAuthenticationResponse\u0012\u000f\n\u0007success\u0018\u0001 \u0001(\b\u0012\u0013\n\u000btraffic_key\u0018\u0002 \u0001(\t\u0012\u0012\n\ntraffic_iv\u0018\u0003 \u0001(\t\u0012\u0012\n\u0005error\u0018\u0004 \u0001(\tH\u0000\u0088\u0001\u0001B\b\n\u0006_errorB\u001d\n\u0019com.moon.gateway.protocolP\u0001b\u0006proto3"
      },
      new FileDescriptor[0]
   );

   private AuthenticationProto() {
   }

   public static void registerAllExtensions(ExtensionRegistryLite extensionRegistry) {
   }

   public static void registerAllExtensions(ExtensionRegistry extensionRegistry) {
      registerAllExtensions(extensionRegistry);
   }

   public static FileDescriptor getDescriptor() {
      return descriptor;
   }
}
