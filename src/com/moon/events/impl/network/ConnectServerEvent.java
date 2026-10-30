package com.moon.events.impl.network;

import com.moon.events.Event;

public class ConnectServerEvent extends Event {
   private String address;
   private boolean singleplayer;

   public String getAddress() {
      return this.address;
   }

   public boolean isLocalServer() {
      return this.singleplayer;
   }

   public ConnectServerEvent(String address, boolean singleplayer) {
      this.address = address;
      this.singleplayer = singleplayer;
   }
}
