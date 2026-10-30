package com.moon.connection.protobuf.chat;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;

public final class ChatProto {
   static final Descriptor internal_static_ServerboundChatMessage_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(0);
   static final FieldAccessorTable internal_static_ServerboundChatMessage_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundChatMessage_descriptor, new String[]{"Message"}
   );
   static final Descriptor internal_static_ClientboundChatError_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(1);
   static final FieldAccessorTable internal_static_ClientboundChatError_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ClientboundChatError_descriptor, new String[]{"Message"}
   );
   static final Descriptor internal_static_ClientboundChatBroadcast_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(2);
   static final FieldAccessorTable internal_static_ClientboundChatBroadcast_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ClientboundChatBroadcast_descriptor, new String[]{"SenderId", "SenderName", "SenderColor", "Message"}
   );
   private static FileDescriptor descriptor = FileDescriptor.internalBuildGeneratedFileFrom(
      new String[]{
         "\n\nchat.proto\")\n\u0016ServerboundChatMessage\u0012\u000f\n\u0007message\u0018\u0001 \u0001(\t\"'\n\u0014ClientboundChatError\u0012\u000f\n\u0007message\u0018\u0001 \u0001(\t\"i\n\u0018ClientboundChatBroadcast\u0012\u0011\n\tsender_id\u0018\u0001 \u0001(\u0003\u0012\u0013\n\u000bsender_name\u0018\u0002 \u0001(\t\u0012\u0014\n\fsender_color\u0018\u0003 \u0001(\t\u0012\u000f\n\u0007message\u0018\u0004 \u0001(\tB\u001d\n\u0019com.moon.gateway.protocolP\u0001b\u0006proto3"
      },
      new FileDescriptor[0]
   );

   private ChatProto() {
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
