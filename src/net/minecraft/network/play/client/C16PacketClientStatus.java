package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C16PacketClientStatus implements Packet<INetHandlerPlayServer> {
   private C16PacketClientStatus.EnumState status;

   public C16PacketClientStatus() {
   }

   public C16PacketClientStatus(C16PacketClientStatus.EnumState enumState) {
      this.status = enumState;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.status = buffer.readEnumValue(C16PacketClientStatus.EnumState.class);
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeEnumValue(this.status);
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processClientStatus(this);
   }

   public C16PacketClientStatus.EnumState getStatus() {
      return this.status;
   }

   public enum EnumState {
      PERFORM_RESPAWN,
      REQUEST_STATS,
      OPEN_INVENTORY_ACHIEVEMENT;

      private static C16PacketClientStatus.EnumState[] Method368() {
         return new C16PacketClientStatus.EnumState[]{PERFORM_RESPAWN, REQUEST_STATS, OPEN_INVENTORY_ACHIEVEMENT};
      }
   }
}
