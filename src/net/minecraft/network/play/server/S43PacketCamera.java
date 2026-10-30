package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S43PacketCamera implements Packet<INetHandlerPlayClient> {
   public int entityId;

   public S43PacketCamera() {
   }

   public S43PacketCamera(Entity entity) {
      this.entityId = entity.getEntityId();
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readVarIntFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityId);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleCamera(this);
   }

   public Entity getEntity(World world) {
      return world.getEntityByID(this.entityId);
   }
}
