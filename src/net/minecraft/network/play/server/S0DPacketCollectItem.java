package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S0DPacketCollectItem implements Packet<INetHandlerPlayClient> {
   private int collectedItemEntityId;
   private int entityId;

   public S0DPacketCollectItem() {
   }

   public S0DPacketCollectItem(int integer1, int integer2) {
      this.collectedItemEntityId = integer1;
      this.entityId = integer2;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.collectedItemEntityId = buffer.readVarIntFromBuffer();
      this.entityId = buffer.readVarIntFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.collectedItemEntityId);
      buffer.writeVarIntToBuffer(this.entityId);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleCollectItem(this);
   }

   public int getCollectedItemEntityID() {
      return this.collectedItemEntityId;
   }

   public int getEntityID() {
      return this.entityId;
   }
}
