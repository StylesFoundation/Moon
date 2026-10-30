package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.entity.DataWatcher;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S1CPacketEntityMetadata implements Packet<INetHandlerPlayClient> {
   private int entityId;
   private List<DataWatcher.WatchableObject> field_149378_b;

   public S1CPacketEntityMetadata() {
   }

   public S1CPacketEntityMetadata(int integer, DataWatcher dataWatcher, boolean boolean3) {
      this.entityId = integer;
      if (boolean3) {
         this.field_149378_b = dataWatcher.getAllWatched();
      } else {
         this.field_149378_b = dataWatcher.getChanged();
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readVarIntFromBuffer();
      this.field_149378_b = DataWatcher.readWatchedListFromPacketBuffer(buffer);
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityId);
      DataWatcher.writeWatchedListToPacketBuffer(this.field_149378_b, buffer);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleEntityMetadata(this);
   }

   public List<DataWatcher.WatchableObject> func_149376_c() {
      return this.field_149378_b;
   }

   public int getEntityId() {
      return this.entityId;
   }
}
