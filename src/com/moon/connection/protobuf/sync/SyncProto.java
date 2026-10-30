package com.moon.connection.protobuf.sync;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;

public final class SyncProto {
   static final Descriptor internal_static_SynchronizedUser_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(0);
   static final FieldAccessorTable internal_static_SynchronizedUser_fieldAccessorTable = new FieldAccessorTable(
      internal_static_SynchronizedUser_descriptor, new String[]{"Name", "GameName", "Server", "Color"}
   );
   static final Descriptor internal_static_ServerboundUpdateGameSync_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(1);
   static final FieldAccessorTable internal_static_ServerboundUpdateGameSync_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundUpdateGameSync_descriptor, new String[]{"GameName", "GameServer"}
   );
   static final Descriptor internal_static_ClientboundUpdateUserSync_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(2);
   static final FieldAccessorTable internal_static_ClientboundUpdateUserSync_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ClientboundUpdateUserSync_descriptor, new String[]{"Users"}
   );
   private static FileDescriptor descriptor = FileDescriptor.internalBuildGeneratedFileFrom(
      new String[]{
         "\n\nsync.proto\"R\n\u0010SynchronizedUser\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u0011\n\tgame_name\u0018\u0002 \u0001(\t\u0012\u000e\n\u0006server\u0018\u0003 \u0001(\t\u0012\r\n\u0005color\u0018\u0004 \u0001(\t\"C\n\u0019ServerboundUpdateGameSync\u0012\u0011\n\tgame_name\u0018\u0001 \u0001(\t\u0012\u0013\n\u000bgame_server\u0018\u0002 \u0001(\t\"=\n\u0019ClientboundUpdateUserSync\u0012 \n\u0005users\u0018\u0001 \u0003(\u000b2\u0011.SynchronizedUserB\u001d\n\u0019com.moon.gateway.protocolP\u0001b\u0006proto3"
      },
      new FileDescriptor[0]
   );

   private SyncProto() {
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
