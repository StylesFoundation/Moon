package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S03PacketTimeUpdate implements Packet<INetHandlerPlayClient> {
   private long totalWorldTime;
   private long worldTime;

   public S03PacketTimeUpdate() {
   }

   public S03PacketTimeUpdate(long long1, long long2, boolean boolean3) {
      this.totalWorldTime = long1;
      this.worldTime = long2;
      if (!boolean3) {
         this.worldTime = -this.worldTime;
         if (this.worldTime == 0L) {
            this.worldTime = -1L;
         }
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.totalWorldTime = buffer.readLong();
      this.worldTime = buffer.readLong();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeLong(this.totalWorldTime);
      buffer.writeLong(this.worldTime);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleTimeUpdate(this);
   }

   public long getTotalWorldTime() {
      return this.totalWorldTime;
   }

   public long getWorldTime() {
      return this.worldTime;
   }
}
