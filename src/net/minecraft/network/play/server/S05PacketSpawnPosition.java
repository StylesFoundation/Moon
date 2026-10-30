package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S05PacketSpawnPosition implements Packet<INetHandlerPlayClient> {
   private BlockPos spawnBlockPos;

   public S05PacketSpawnPosition() {
   }

   public S05PacketSpawnPosition(BlockPos blockPos) {
      this.spawnBlockPos = blockPos;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.spawnBlockPos = buffer.readBlockPos();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeBlockPos(this.spawnBlockPos);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleSpawnPosition(this);
   }

   public BlockPos getSpawnPos() {
      return this.spawnBlockPos;
   }
}
