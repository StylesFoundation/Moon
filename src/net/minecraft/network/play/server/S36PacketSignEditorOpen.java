package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S36PacketSignEditorOpen implements Packet<INetHandlerPlayClient> {
   private BlockPos signPosition;

   public S36PacketSignEditorOpen() {
   }

   public S36PacketSignEditorOpen(BlockPos blockPos) {
      this.signPosition = blockPos;
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleSignEditorOpen(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.signPosition = buffer.readBlockPos();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeBlockPos(this.signPosition);
   }

   public BlockPos getSignPosition() {
      return this.signPosition;
   }
}
