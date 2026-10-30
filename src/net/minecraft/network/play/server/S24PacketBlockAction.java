package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S24PacketBlockAction implements Packet<INetHandlerPlayClient> {
   private BlockPos blockPosition;
   private int instrument;
   private int pitch;
   private Block block;

   public S24PacketBlockAction() {
   }

   public S24PacketBlockAction(BlockPos blockPos, Block block, int integer3, int integer4) {
      this.blockPosition = blockPos;
      this.instrument = integer3;
      this.pitch = integer4;
      this.block = block;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.blockPosition = buffer.readBlockPos();
      this.instrument = buffer.readUnsignedByte();
      this.pitch = buffer.readUnsignedByte();
      this.block = Block.getBlockById(buffer.readVarIntFromBuffer() & 4095);
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeBlockPos(this.blockPosition);
      buffer.writeByte(this.instrument);
      buffer.writeByte(this.pitch);
      buffer.writeVarIntToBuffer(Block.getIdFromBlock(this.block) & 4095);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleBlockAction(this);
   }

   public BlockPos getBlockPosition() {
      return this.blockPosition;
   }

   public int getData1() {
      return this.instrument;
   }

   public int getData2() {
      return this.pitch;
   }

   public Block getBlockType() {
      return this.block;
   }
}
