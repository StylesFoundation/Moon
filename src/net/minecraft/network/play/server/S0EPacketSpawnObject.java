package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0EPacketSpawnObject implements Packet<INetHandlerPlayClient> {
   private int entityId;
   private int x;
   private int y;
   private int z;
   private int speedX;
   private int speedY;
   private int speedZ;
   private int pitch;
   private int yaw;
   private int type;
   private int field_149020_k;

   public S0EPacketSpawnObject() {
   }

   public S0EPacketSpawnObject(Entity entity, int integer) {
      this(entity, integer, 0);
   }

   public S0EPacketSpawnObject(Entity entity, int integer2, int integer3) {
      this.entityId = entity.getEntityId();
      this.x = MathHelper.floor_double(entity.posX * 32.0);
      this.y = MathHelper.floor_double(entity.posY * 32.0);
      this.z = MathHelper.floor_double(entity.posZ * 32.0);
      this.pitch = MathHelper.floor_float(entity.rotationPitch * 256.0F / 360.0F);
      this.yaw = MathHelper.floor_float(entity.rotationYaw * 256.0F / 360.0F);
      this.type = integer2;
      this.field_149020_k = integer3;
      if (integer3 > 0) {
         double var7 = entity.motionX;
         double var9 = entity.motionY;
         double var11 = entity.motionZ;
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

         this.speedX = (int)(var7 * 8000.0);
         this.speedY = (int)(var9 * 8000.0);
         this.speedZ = (int)(var11 * 8000.0);
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readVarIntFromBuffer();
      this.type = buffer.readByte();
      this.x = buffer.readInt();
      this.y = buffer.readInt();
      this.z = buffer.readInt();
      this.pitch = buffer.readByte();
      this.yaw = buffer.readByte();
      this.field_149020_k = buffer.readInt();
      if (this.field_149020_k > 0) {
         this.speedX = buffer.readShort();
         this.speedY = buffer.readShort();
         this.speedZ = buffer.readShort();
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityId);
      buffer.writeByte(this.type);
      buffer.writeInt(this.x);
      buffer.writeInt(this.y);
      buffer.writeInt(this.z);
      buffer.writeByte(this.pitch);
      buffer.writeByte(this.yaw);
      buffer.writeInt(this.field_149020_k);
      if (this.field_149020_k > 0) {
         buffer.writeShort(this.speedX);
         buffer.writeShort(this.speedY);
         buffer.writeShort(this.speedZ);
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleSpawnObject(this);
   }

   public int getEntityID() {
      return this.entityId;
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

   public int getSpeedX() {
      return this.speedX;
   }

   public int getSpeedY() {
      return this.speedY;
   }

   public int getSpeedZ() {
      return this.speedZ;
   }

   public int getPitch() {
      return this.pitch;
   }

   public int getYaw() {
      return this.yaw;
   }

   public int getType() {
      return this.type;
   }

   public int func_149009_m() {
      return this.field_149020_k;
   }

   public void setX(int integer) {
      this.x = integer;
   }

   public void setY(int integer) {
      this.y = integer;
   }

   public void setZ(int integer) {
      this.z = integer;
   }

   public void setSpeedX(int integer) {
      this.speedX = integer;
   }

   public void setSpeedY(int integer) {
      this.speedY = integer;
   }

   public void setSpeedZ(int integer) {
      this.speedZ = integer;
   }

   public void func_149002_g(int integer) {
      this.field_149020_k = integer;
   }
}
