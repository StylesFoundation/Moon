package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S0BPacketAnimation implements Packet<INetHandlerPlayClient> {
   private int entityId;
   private int type;

   public S0BPacketAnimation() {
   }

   public S0BPacketAnimation(Entity entity, int integer) {
      this.entityId = entity.getEntityId();
      this.type = integer;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readVarIntFromBuffer();
      this.type = buffer.readUnsignedByte();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityId);
      buffer.writeByte(this.type);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleAnimation(this);
   }

   public int getEntityID() {
      return this.entityId;
   }

   public int getAnimationType() {
      return this.type;
   }
}
