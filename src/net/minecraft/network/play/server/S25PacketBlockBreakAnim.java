package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S25PacketBlockBreakAnim implements Packet<INetHandlerPlayClient> {
   private int breakerId;
   private BlockPos position;
   private int progress;

   public S25PacketBlockBreakAnim() {
   }

   public S25PacketBlockBreakAnim(int integer1, BlockPos blockPos, int integer3) {
      this.breakerId = integer1;
      this.position = blockPos;
      this.progress = integer3;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.breakerId = buffer.readVarIntFromBuffer();
      this.position = buffer.readBlockPos();
      this.progress = buffer.readUnsignedByte();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.breakerId);
      buffer.writeBlockPos(this.position);
      buffer.writeByte(this.progress);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleBlockBreakAnim(this);
   }

   public int getBreakerId() {
      return this.breakerId;
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public int getProgress() {
      return this.progress;
   }
}
