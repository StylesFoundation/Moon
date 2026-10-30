package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S19PacketEntityHeadLook implements Packet<INetHandlerPlayClient> {
   private int entityId;
   private byte yaw;

   public S19PacketEntityHeadLook() {
   }

   public S19PacketEntityHeadLook(Entity entity, byte byte2) {
      this.entityId = entity.getEntityId();
      this.yaw = byte2;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readVarIntFromBuffer();
      this.yaw = buffer.readByte();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityId);
      buffer.writeByte(this.yaw);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleEntityHeadLook(this);
   }

   public Entity getEntity(World world) {
      return world.getEntityByID(this.entityId);
   }

   public byte getYaw() {
      return this.yaw;
   }

   public int Method991() {
      return this.entityId;
   }

   public byte Method992() {
      return this.yaw;
   }
}
