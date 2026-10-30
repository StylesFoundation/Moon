package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S35PacketUpdateTileEntity implements Packet<INetHandlerPlayClient> {
   private BlockPos blockPos;
   private int metadata;
   private NBTTagCompound nbt;

   public S35PacketUpdateTileEntity() {
   }

   public S35PacketUpdateTileEntity(BlockPos blockPos, int integer, NBTTagCompound nBTTagCompound) {
      this.blockPos = blockPos;
      this.metadata = integer;
      this.nbt = nBTTagCompound;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.blockPos = buffer.readBlockPos();
      this.metadata = buffer.readUnsignedByte();
      this.nbt = buffer.readNBTTagCompoundFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeBlockPos(this.blockPos);
      buffer.writeByte((byte)this.metadata);
      buffer.writeNBTTagCompoundToBuffer(this.nbt);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleUpdateTileEntity(this);
   }

   public BlockPos getPos() {
      return this.blockPos;
   }

   public int getTileEntityType() {
      return this.metadata;
   }

   public NBTTagCompound getNbtCompound() {
      return this.nbt;
   }
}
