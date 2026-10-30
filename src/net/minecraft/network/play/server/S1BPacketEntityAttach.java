package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S1BPacketEntityAttach implements Packet<INetHandlerPlayClient> {
   private int leash;
   private int entityId;
   private int vehicleEntityId;

   public S1BPacketEntityAttach() {
   }

   public S1BPacketEntityAttach(int integer, Entity entity2, Entity entity3) {
      this.leash = integer;
      this.entityId = entity2.getEntityId();
      this.vehicleEntityId = entity3 != null ? entity3.getEntityId() : -1;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readInt();
      this.vehicleEntityId = buffer.readInt();
      this.leash = buffer.readUnsignedByte();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.vehicleEntityId);
      buffer.writeByte(this.leash);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleEntityAttach(this);
   }

   public int getLeash() {
      return this.leash;
   }

   public int getEntityId() {
      return this.entityId;
   }

   public int getVehicleEntityId() {
      return this.vehicleEntityId;
   }
}
