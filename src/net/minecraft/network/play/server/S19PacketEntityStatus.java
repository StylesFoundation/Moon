package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S19PacketEntityStatus implements Packet<INetHandlerPlayClient> {
   private int entityId;
   private byte logicOpcode;

   public S19PacketEntityStatus() {
   }

   public S19PacketEntityStatus(Entity entity, byte byte2) {
      this.entityId = entity.getEntityId();
      this.logicOpcode = byte2;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readInt();
      this.logicOpcode = buffer.readByte();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeInt(this.entityId);
      buffer.writeByte(this.logicOpcode);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleEntityStatus(this);
   }

   public Entity getEntity(World world) {
      return world.getEntityByID(this.entityId);
   }

   public byte getOpCode() {
      return this.logicOpcode;
   }
}
