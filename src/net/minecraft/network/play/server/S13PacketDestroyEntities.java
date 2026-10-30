package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S13PacketDestroyEntities implements Packet<INetHandlerPlayClient> {
   private int[] entityIDs;

   public S13PacketDestroyEntities() {
   }

   public S13PacketDestroyEntities(int... arr) {
      this.entityIDs = arr;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityIDs = new int[buffer.readVarIntFromBuffer()];

      for (int var5 = 0; var5 < this.entityIDs.length; var5++) {
         this.entityIDs[var5] = buffer.readVarIntFromBuffer();
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityIDs.length);

      for (int var8 : this.entityIDs) {
         buffer.writeVarIntToBuffer(var8);
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleDestroyEntities(this);
   }

   public int[] getEntityIDs() {
      return this.entityIDs;
   }
}
