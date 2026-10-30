package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class S23PacketBlockChange implements Packet<INetHandlerPlayClient> {
   private BlockPos blockPosition;
   private IBlockState blockState;

   public S23PacketBlockChange() {
   }

   public S23PacketBlockChange(World world, BlockPos blockPos) {
      this.blockPosition = blockPos;
      this.blockState = world.getBlockState(blockPos);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.blockPosition = buffer.readBlockPos();
      this.blockState = Block.BLOCK_STATE_IDS.getByValue(buffer.readVarIntFromBuffer());
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeBlockPos(this.blockPosition);
      buffer.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(this.blockState));
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleBlockChange(this);
   }

   public IBlockState getBlockState() {
      return this.blockState;
   }

   public BlockPos getBlockPosition() {
      return this.blockPosition;
   }
}
