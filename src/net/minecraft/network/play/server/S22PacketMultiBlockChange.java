package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

public class S22PacketMultiBlockChange implements Packet<INetHandlerPlayClient> {
   private ChunkCoordIntPair chunkPosCoord;
   private S22PacketMultiBlockChange.BlockUpdateData[] changedBlocks;

   public S22PacketMultiBlockChange() {
   }

   public S22PacketMultiBlockChange(int integer, short[] arr, Chunk chunk) {
      this.chunkPosCoord = new ChunkCoordIntPair(chunk.xPosition, chunk.zPosition);
      this.changedBlocks = new S22PacketMultiBlockChange.BlockUpdateData[integer];

      for (int var7 = 0; var7 < this.changedBlocks.length; var7++) {
         this.changedBlocks[var7] = new S22PacketMultiBlockChange.BlockUpdateData(arr[var7], chunk);
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.chunkPosCoord = new ChunkCoordIntPair(buffer.readInt(), buffer.readInt());
      this.changedBlocks = new S22PacketMultiBlockChange.BlockUpdateData[buffer.readVarIntFromBuffer()];

      for (int var5 = 0; var5 < this.changedBlocks.length; var5++) {
         this.changedBlocks[var5] = new S22PacketMultiBlockChange.BlockUpdateData(
            buffer.readShort(), Block.BLOCK_STATE_IDS.getByValue(buffer.readVarIntFromBuffer())
         );
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeInt(this.chunkPosCoord.chunkXPos);
      buffer.writeInt(this.chunkPosCoord.chunkZPos);
      buffer.writeVarIntToBuffer(this.changedBlocks.length);

      for (S22PacketMultiBlockChange.BlockUpdateData var8 : this.changedBlocks) {
         buffer.writeShort(var8.func_180089_b());
         buffer.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(var8.getBlockState()));
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleMultiBlockChange(this);
   }

   public S22PacketMultiBlockChange.BlockUpdateData[] getChangedBlocks() {
      return this.changedBlocks;
   }

   public class BlockUpdateData {
      private final short chunkPosCrammed;
      private final IBlockState blockState;

      public BlockUpdateData(short short2, IBlockState iBlockState) {
         this.chunkPosCrammed = short2;
         this.blockState = iBlockState;
      }

      public BlockUpdateData(short short2, Chunk chunk) {
         this.chunkPosCrammed = short2;
         this.blockState = chunk.getBlockState(this.getPos());
      }

      public BlockPos getPos() {
         return new BlockPos(
            S22PacketMultiBlockChange.this.chunkPosCoord.getBlock(this.chunkPosCrammed >> 12 & 15, this.chunkPosCrammed & 255, this.chunkPosCrammed >> 8 & 15)
         );
      }

      public short func_180089_b() {
         return this.chunkPosCrammed;
      }

      public IBlockState getBlockState() {
         return this.blockState;
      }
   }
}
