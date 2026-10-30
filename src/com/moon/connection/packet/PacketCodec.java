package com.moon.connection.packet;

import com.google.protobuf.GeneratedMessageV3;
import com.moon.connection.protobuf.authentication.ServerboundAuthenticationRequest;
import com.moon.connection.protobuf.chat.ServerboundChatMessage;
import com.moon.connection.protobuf.cloud.ServerboundCloudDeleteRequest;
import com.moon.connection.protobuf.cloud.ServerboundCloudDownloadRequest;
import com.moon.connection.protobuf.cloud.ServerboundCloudFeaturedRequest;
import com.moon.connection.protobuf.cloud.ServerboundCloudQueryRequest;
import com.moon.connection.protobuf.cloud.ServerboundCloudUpdateContentRequest;
import com.moon.connection.protobuf.cloud.ServerboundCloudUpdateDisplayNameRequest;
import com.moon.connection.protobuf.cloud.ServerboundCloudUploadRequest;
import com.moon.connection.protobuf.cloud.ServerboundCloudVotingRequest;
import com.moon.connection.protobuf.sync.ServerboundUpdateGameSync;
import com.moon.connection.protobuf.telemetry.ServerboundAccountLoginMarker;
import com.moon.connection.protobuf.telemetry.ServerboundConfigLoadMarker;
import com.moon.connection.protobuf.telemetry.ServerboundConfigSaveMarker;
import com.moon.connection.protobuf.telemetry.ServerboundJoinMarker;
import com.moon.connection.protobuf.telemetry.ServerboundPlaytimeMarker;
import com.moon.connection.protobuf.telemetry.ServerboundScriptLoadMarker;
import com.moon.connection.protobuf.telemetry.ServerboundServerPlaytimeMarker;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class PacketCodec {
   private static final Map<Class<? extends GeneratedMessageV3>, Byte> PACKET_IDS = new HashMap<>();

   public static byte getPacketId(Class<? extends GeneratedMessageV3> packetClass) {
      return PACKET_IDS.getOrDefault(packetClass, (byte)-1);
   }

   public static ByteBuffer encode(GeneratedMessageV3 message) {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      try {
         message.writeTo(outputStream);
      } catch (IOException e) {
         return ByteBuffer.allocate(0);
      }

      ByteBuffer buffer = ByteBuffer.allocate(1 + outputStream.size());
      buffer.put(getPacketId((Class<? extends GeneratedMessageV3>)message.getClass()));
      buffer.put(outputStream.toByteArray());
      buffer.flip();
      return buffer;
   }

   private static void registerPackets() {
      PACKET_IDS.put(ServerboundAuthenticationRequest.class, (byte)1);
      PACKET_IDS.put(ServerboundChatMessage.class, (byte)2);
      PACKET_IDS.put(ServerboundPlaytimeMarker.class, (byte)3);
      PACKET_IDS.put(ServerboundServerPlaytimeMarker.class, (byte)4);
      PACKET_IDS.put(ServerboundJoinMarker.class, (byte)5);
      PACKET_IDS.put(ServerboundAccountLoginMarker.class, (byte)6);
      PACKET_IDS.put(ServerboundScriptLoadMarker.class, (byte)7);
      PACKET_IDS.put(ServerboundConfigLoadMarker.class, (byte)8);
      PACKET_IDS.put(ServerboundConfigSaveMarker.class, (byte)9);
      PACKET_IDS.put(ServerboundUpdateGameSync.class, (byte)10);
      PACKET_IDS.put(ServerboundCloudUploadRequest.class, (byte)11);
      PACKET_IDS.put(ServerboundCloudUpdateContentRequest.class, (byte)12);
      PACKET_IDS.put(ServerboundCloudUpdateDisplayNameRequest.class, (byte)13);
      PACKET_IDS.put(ServerboundCloudDeleteRequest.class, (byte)14);
      PACKET_IDS.put(ServerboundCloudDownloadRequest.class, (byte)15);
      PACKET_IDS.put(ServerboundCloudQueryRequest.class, (byte)16);
      PACKET_IDS.put(ServerboundCloudVotingRequest.class, (byte)17);
      PACKET_IDS.put(ServerboundCloudFeaturedRequest.class, (byte)18);
   }

   static {
      registerPackets();
   }
}
