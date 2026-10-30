package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class C07PacketPlayerDigging implements Packet<INetHandlerPlayServer> {
   private BlockPos position;
   private EnumFacing facing;
   private C07PacketPlayerDigging.Action status;

   public C07PacketPlayerDigging() {
   }

   public C07PacketPlayerDigging(C07PacketPlayerDigging.Action action, BlockPos blockPos, EnumFacing enumFacing) {
      this.status = action;
      this.position = blockPos;
      this.facing = enumFacing;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.status = buffer.readEnumValue(C07PacketPlayerDigging.Action.class);
      this.position = buffer.readBlockPos();
      this.facing = EnumFacing.getFront(buffer.readUnsignedByte());
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeEnumValue(this.status);
      buffer.writeBlockPos(this.position);
      buffer.writeByte(this.facing.getIndex());
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processPlayerDigging(this);
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public EnumFacing getFacing() {
      return this.facing;
   }

   public C07PacketPlayerDigging.Action getStatus() {
      return this.status;
   }

   public BlockPos Method697() {
      return this.position;
   }

   public EnumFacing Method698() {
      return this.facing;
   }

   public C07PacketPlayerDigging.Action Method699() {
      return this.status;
   }

   public C07PacketPlayerDigging Method700(BlockPos blockPos) {
      this.position = blockPos;
      return this;
   }

   public C07PacketPlayerDigging Method701(EnumFacing enumFacing) {
      this.facing = enumFacing;
      return this;
   }

   public C07PacketPlayerDigging Method702(C07PacketPlayerDigging.Action action) {
      this.status = action;
      return this;
   }

   public enum Action {
      START_DESTROY_BLOCK,
      ABORT_DESTROY_BLOCK,
      STOP_DESTROY_BLOCK,
      DROP_ALL_ITEMS,
      DROP_ITEM,
      RELEASE_USE_ITEM;

      private static C07PacketPlayerDigging.Action[] Method904() {
         return new C07PacketPlayerDigging.Action[]{START_DESTROY_BLOCK, ABORT_DESTROY_BLOCK, STOP_DESTROY_BLOCK, DROP_ALL_ITEMS, DROP_ITEM, RELEASE_USE_ITEM};
      }
   }
}
