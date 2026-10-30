package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class S10PacketSpawnPainting implements Packet<INetHandlerPlayClient> {
   private int entityID;
   private BlockPos position;
   private EnumFacing facing;
   private String title;

   public S10PacketSpawnPainting() {
   }

   public S10PacketSpawnPainting(EntityPainting entityPainting) {
      this.entityID = entityPainting.getEntityId();
      this.position = entityPainting.getHangingPosition();
      this.facing = entityPainting.facingDirection;
      this.title = entityPainting.art.title;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityID = buffer.readVarIntFromBuffer();
      this.title = buffer.readStringFromBuffer(EntityPainting.EnumArt.field_180001_A);
      this.position = buffer.readBlockPos();
      this.facing = EnumFacing.getHorizontal(buffer.readUnsignedByte());
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityID);
      buffer.writeString(this.title);
      buffer.writeBlockPos(this.position);
      buffer.writeByte(this.facing.getHorizontalIndex());
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleSpawnPainting(this);
   }

   public int getEntityID() {
      return this.entityID;
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public EnumFacing getFacing() {
      return this.facing;
   }

   public String getTitle() {
      return this.title;
   }
}
