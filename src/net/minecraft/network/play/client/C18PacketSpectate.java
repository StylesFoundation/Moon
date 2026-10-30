package net.minecraft.network.play.client;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.world.WorldServer;

public class C18PacketSpectate implements Packet<INetHandlerPlayServer> {
   private UUID id;

   public C18PacketSpectate() {
   }

   public C18PacketSpectate(UUID uUID) {
      this.id = uUID;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.id = buffer.readUuid();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeUuid(this.id);
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.handleSpectate(this);
   }

   public Entity getEntity(WorldServer worldServer) {
      return worldServer.getEntityFromUuid(this.id);
   }

   public UUID Method232() {
      return this.id;
   }

   public C18PacketSpectate Method233(UUID uUID) {
      this.id = uUID;
      return this;
   }
}
