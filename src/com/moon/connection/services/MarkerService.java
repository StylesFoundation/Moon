package com.moon.connection.services;

import com.moon.connection.Gateway;
import com.moon.connection.protobuf.telemetry.ServerboundAccountLoginMarker;
import com.moon.connection.protobuf.telemetry.ServerboundConfigLoadMarker;
import com.moon.connection.protobuf.telemetry.ServerboundConfigSaveMarker;
import com.moon.connection.protobuf.telemetry.ServerboundJoinMarker;
import com.moon.connection.protobuf.telemetry.ServerboundPlaytimeMarker;
import com.moon.connection.protobuf.telemetry.ServerboundScriptLoadMarker;
import com.moon.connection.protobuf.telemetry.ServerboundServerPlaytimeMarker;
import java.util.UUID;

public class MarkerService {
   private final Gateway gateway;

   public void sendPlaytimeMarker() {
      this.gateway.getClient().send(ServerboundPlaytimeMarker.newBuilder().build());
   }

   public void sendServerPlaytimeMarker(String server) {
      this.gateway.getClient().send(ServerboundServerPlaytimeMarker.newBuilder().setServer(server).build());
   }

   public void sendJoinMarker(String server) {
      this.gateway.getClient().send(ServerboundJoinMarker.newBuilder().setServer(server).build());
   }

   public void sendAccountLoginMarker(String name, UUID uuid) {
      this.gateway.getClient().send(ServerboundAccountLoginMarker.newBuilder().setName(name).setUuid(uuid.toString()).build());
   }

   public void sendScriptLoadMarker(String name) {
      this.gateway.getClient().send(ServerboundScriptLoadMarker.newBuilder().setName(name).build());
   }

   public void sendConfigLoadMarker(String name) {
      this.gateway.getClient().send(ServerboundConfigLoadMarker.newBuilder().setName(name).build());
   }

   public void sendConfigSaveMarker(String name) {
      this.gateway.getClient().send(ServerboundConfigSaveMarker.newBuilder().setName(name).build());
   }

   public MarkerService(Gateway gateway) {
      this.gateway = gateway;
   }
}
