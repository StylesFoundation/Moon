package com.moon.events.impl.network;

import com.moon.events.EventCancellable;
import net.minecraft.network.Packet;

public class PacketEvent extends EventCancellable {
   private final boolean sending;
   private Packet packet;

   public boolean isSend() {
      return this.sending;
   }

   public Packet getPacket() {
      return this.packet;
   }

   public PacketEvent setPacket(Packet packet) {
      this.packet = packet;
      return this;
   }

   public PacketEvent(boolean sending, Packet packet) {
      this.sending = sending;
      this.packet = packet;
   }
}
