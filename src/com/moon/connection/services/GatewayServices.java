package com.moon.connection.services;

import com.moon.connection.Gateway;

public class GatewayServices {
   private final ChatService chatService;
   private final MarkerService markerService;
   private final UserSyncService userSyncService;
   private final CloudService cloudService;

   public GatewayServices(Gateway gateway) {
      this.chatService = new ChatService(gateway);
      this.markerService = new MarkerService(gateway);
      this.userSyncService = new UserSyncService(gateway);
      this.cloudService = new CloudService(gateway);
   }

   public ChatService chat() {
      return this.chatService;
   }

   public MarkerService markers() {
      return this.markerService;
   }

   public UserSyncService userSync() {
      return this.userSyncService;
   }

   public CloudService cloud() {
      return this.cloudService;
   }

   public static boolean dummy() {
      return false;
   }

   static {
      if (dummy()) {
      }
   }
}
