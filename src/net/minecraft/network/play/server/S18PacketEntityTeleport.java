package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S18PacketEntityTeleport implements Packet<INetHandlerPlayClient> {
   private int entityId;
   private int posX;
   private int posY;
   private int posZ;
   private byte yaw;
   private byte pitch;
   private boolean onGround;

   public S18PacketEntityTeleport() {
   }

   public S18PacketEntityTeleport(Entity entity) {
      this.entityId = entity.getEntityId();
      this.posX = MathHelper.floor_double(entity.posX * 32.0);
      this.posY = MathHelper.floor_double(entity.posY * 32.0);
      this.posZ = MathHelper.floor_double(entity.posZ * 32.0);
      this.yaw = (byte)(entity.rotationYaw * 256.0F / 360.0F);
      this.pitch = (byte)(entity.rotationPitch * 256.0F / 360.0F);
      this.onGround = entity.onGround;
   }

   public S18PacketEntityTeleport(int integer1, int integer2, int integer3, int integer4, byte byte5, byte byte6, boolean boolean7) {
      this.entityId = integer1;
      this.posX = integer2;
      this.posY = integer3;
      this.posZ = integer4;
      this.yaw = byte5;
      this.pitch = byte6;
      this.onGround = boolean7;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readVarIntFromBuffer();
      this.posX = buffer.readInt();
      this.posY = buffer.readInt();
      this.posZ = buffer.readInt();
      this.yaw = buffer.readByte();
      this.pitch = buffer.readByte();
      this.onGround = buffer.readBoolean();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityId);
      buffer.writeInt(this.posX);
      buffer.writeInt(this.posY);
      buffer.writeInt(this.posZ);
      buffer.writeByte(this.yaw);
      buffer.writeByte(this.pitch);
      buffer.writeBoolean(this.onGround);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleEntityTeleport(this);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public int getX() {
      return this.posX;
   }

   public int getY() {
      return this.posY;
   }

   public int getZ() {
      return this.posZ;
   }

   public byte getYaw() {
      return this.yaw;
   }

   public byte getPitch() {
      return this.pitch;
   }

   public boolean getOnGround() {
      return this.onGround;
   }

   public int Method623() {
      return this.entityId;
   }

   public int Method624() {
      return this.posX;
   }

   public int Method625() {
      return this.posY;
   }

   public int Method626() {
      return this.posZ;
   }

   public byte Method627() {
      return this.yaw;
   }

   public byte Method628() {
      return this.pitch;
   }

   public boolean Method629() {
      return this.onGround;
   }
}
