package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0FPacketSpawnMob implements Packet<INetHandlerPlayClient> {
   private int entityId;
   private int type;
   private int x;
   private int y;
   private int z;
   private int velocityX;
   private int velocityY;
   private int velocityZ;
   private byte yaw;
   private byte pitch;
   private byte headPitch;
   private DataWatcher field_149043_l;
   private List<DataWatcher.WatchableObject> watcher;

   public S0FPacketSpawnMob() {
   }

   public S0FPacketSpawnMob(EntityLivingBase entityLivingBase) {
      this.entityId = entityLivingBase.getEntityId();
      this.type = (byte)EntityList.getEntityID(entityLivingBase);
      this.x = MathHelper.floor_double(entityLivingBase.posX * 32.0);
      this.y = MathHelper.floor_double(entityLivingBase.posY * 32.0);
      this.z = MathHelper.floor_double(entityLivingBase.posZ * 32.0);
      this.yaw = (byte)(entityLivingBase.rotationYaw * 256.0F / 360.0F);
      this.pitch = (byte)(entityLivingBase.rotationPitch * 256.0F / 360.0F);
      this.headPitch = (byte)(entityLivingBase.rotationYawHead * 256.0F / 360.0F);
      double var7 = entityLivingBase.motionX;
      double var9 = entityLivingBase.motionY;
      double var11 = entityLivingBase.motionZ;
      if (var7 < -3.9) {
         var7 = -3.9;
      }

      if (var9 < -3.9) {
         var9 = -3.9;
      }

      if (var11 < -3.9) {
         var11 = -3.9;
      }

      if (var7 > 3.9) {
         var7 = 3.9;
      }

      if (var9 > 3.9) {
         var9 = 3.9;
      }

      if (var11 > 3.9) {
         var11 = 3.9;
      }

      this.velocityX = (int)(var7 * 8000.0);
      this.velocityY = (int)(var9 * 8000.0);
      this.velocityZ = (int)(var11 * 8000.0);
      this.field_149043_l = entityLivingBase.getDataWatcher();
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readVarIntFromBuffer();
      this.type = buffer.readByte() & 255;
      this.x = buffer.readInt();
      this.y = buffer.readInt();
      this.z = buffer.readInt();
      this.yaw = buffer.readByte();
      this.pitch = buffer.readByte();
      this.headPitch = buffer.readByte();
      this.velocityX = buffer.readShort();
      this.velocityY = buffer.readShort();
      this.velocityZ = buffer.readShort();
      this.watcher = DataWatcher.readWatchedListFromPacketBuffer(buffer);
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityId);
      buffer.writeByte(this.type & 0xFF);
      buffer.writeInt(this.x);
      buffer.writeInt(this.y);
      buffer.writeInt(this.z);
      buffer.writeByte(this.yaw);
      buffer.writeByte(this.pitch);
      buffer.writeByte(this.headPitch);
      buffer.writeShort(this.velocityX);
      buffer.writeShort(this.velocityY);
      buffer.writeShort(this.velocityZ);
      this.field_149043_l.writeTo(buffer);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleSpawnMob(this);
   }

   public List<DataWatcher.WatchableObject> func_149027_c() {
      if (this.watcher == null) {
         this.watcher = this.field_149043_l.getAllWatched();
      }

      return this.watcher;
   }

   public int getEntityID() {
      return this.entityId;
   }

   public int getEntityType() {
      return this.type;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getZ() {
      return this.z;
   }

   public int getVelocityX() {
      return this.velocityX;
   }

   public int getVelocityY() {
      return this.velocityY;
   }

   public int getVelocityZ() {
      return this.velocityZ;
   }

   public byte getYaw() {
      return this.yaw;
   }

   public byte getPitch() {
      return this.pitch;
   }

   public byte getHeadPitch() {
      return this.headPitch;
   }
}
