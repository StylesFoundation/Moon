package com.moon.connection.protobuf.telemetry;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;

public final class TelemetryProto {
   static final Descriptor internal_static_ServerboundPlaytimeMarker_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(0);
   static final FieldAccessorTable internal_static_ServerboundPlaytimeMarker_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundPlaytimeMarker_descriptor, new String[0]
   );
   static final Descriptor internal_static_ServerboundServerPlaytimeMarker_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(1);
   static final FieldAccessorTable internal_static_ServerboundServerPlaytimeMarker_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundServerPlaytimeMarker_descriptor, new String[]{"Server"}
   );
   static final Descriptor internal_static_ServerboundJoinMarker_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(2);
   static final FieldAccessorTable internal_static_ServerboundJoinMarker_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundJoinMarker_descriptor, new String[]{"Server"}
   );
   static final Descriptor internal_static_ServerboundAccountLoginMarker_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(3);
   static final FieldAccessorTable internal_static_ServerboundAccountLoginMarker_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundAccountLoginMarker_descriptor, new String[]{"Name", "Uuid"}
   );
   static final Descriptor internal_static_ServerboundScriptLoadMarker_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(4);
   static final FieldAccessorTable internal_static_ServerboundScriptLoadMarker_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundScriptLoadMarker_descriptor, new String[]{"Name"}
   );
   static final Descriptor internal_static_ServerboundConfigLoadMarker_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(5);
   static final FieldAccessorTable internal_static_ServerboundConfigLoadMarker_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundConfigLoadMarker_descriptor, new String[]{"Name"}
   );
   static final Descriptor internal_static_ServerboundConfigSaveMarker_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(6);
   static final FieldAccessorTable internal_static_ServerboundConfigSaveMarker_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundConfigSaveMarker_descriptor, new String[]{"Name"}
   );
   private static FileDescriptor descriptor = FileDescriptor.internalBuildGeneratedFileFrom(
      new String[]{
         "\n\u000ftelemetry.proto\"\u001b\n\u0019ServerboundPlaytimeMarker\"1\n\u001fServerboundServerPlaytimeMarker\u0012\u000e\n\u0006server\u0018\u0001 \u0001(\t\"'\n\u0015ServerboundJoinMarker\u0012\u000e\n\u0006server\u0018\u0001 \u0001(\t\";\n\u001dServerboundAccountLoginMarker\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\f\n\u0004uuid\u0018\u0002 \u0001(\t\"+\n\u001bServerboundScriptLoadMarker\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\"+\n\u001bServerboundConfigLoadMarker\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\"+\n\u001bServerboundConfigSaveMarker\u0012\f\n\u0004name\u0018\u0001 \u0001(\tB\u001d\n\u0019com.moon.gateway.protocolP\u0001b\u0006proto3"
      },
      new FileDescriptor[0]
   );

   private TelemetryProto() {
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
