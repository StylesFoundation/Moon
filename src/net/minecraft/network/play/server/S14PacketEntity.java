package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S14PacketEntity implements Packet<INetHandlerPlayClient> {
   protected int entityId;
   protected byte posX;
   protected byte posY;
   protected byte posZ;
   protected byte yaw;
   protected byte pitch;
   protected boolean onGround;
   protected boolean field_149069_g;

   public S14PacketEntity() {
   }

   public S14PacketEntity(int integer) {
      this.entityId = integer;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readVarIntFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityId);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleEntityMovement(this);
   }

   @Override
   public String toString() {
      return "Entity_" + super.toString();
   }

   public Entity getEntity(World world) {
      return world.getEntityByID(this.entityId);
   }

   public byte func_149062_c() {
      return this.posX;
   }

   public byte func_149061_d() {
      return this.posY;
   }

   public byte func_149064_e() {
      return this.posZ;
   }

   public byte func_149066_f() {
      return this.yaw;
   }

   public byte func_149063_g() {
      return this.pitch;
   }

   public boolean func_149060_h() {
      return this.field_149069_g;
   }

   public boolean getOnGround() {
      return this.onGround;
   }

   public int Method919() {
      return this.entityId;
   }

   public byte Method920() {
      return this.posX;
   }

   public byte Method921() {
      return this.posY;
   }

   public byte Method922() {
      return this.posZ;
   }

   public byte Method923() {
      return this.yaw;
   }

   public byte Method924() {
      return this.pitch;
   }

   public boolean Method925() {
      return this.onGround;
   }

   public boolean Method926() {
      return this.field_149069_g;
   }

   public static class S15PacketEntityRelMove extends S14PacketEntity {
      public S15PacketEntityRelMove() {
      }

      public S15PacketEntityRelMove(int integer, byte byte2, byte byte3, byte byte4, boolean boolean5) {
         super(integer);
         this.posX = byte2;
         this.posY = byte3;
         this.posZ = byte4;
         this.onGround = boolean5;
      }

      @Override
      public void readPacketData(PacketBuffer buffer) throws IOException {
         super.readPacketData(buffer);
         this.posX = buffer.readByte();
         this.posY = buffer.readByte();
         this.posZ = buffer.readByte();
         this.onGround = buffer.readBoolean();
      }

      @Override
      public void writePacketData(PacketBuffer buffer) throws IOException {
         super.writePacketData(buffer);
         buffer.writeByte(this.posX);
         buffer.writeByte(this.posY);
         buffer.writeByte(this.posZ);
         buffer.writeBoolean(this.onGround);
      }
   }

   public static class S16PacketEntityLook extends S14PacketEntity {
      public S16PacketEntityLook() {
         this.field_149069_g = true;
      }

      public S16PacketEntityLook(int integer, byte byte2, byte byte3, boolean boolean4) {
         super(integer);
         this.yaw = byte2;
         this.pitch = byte3;
         this.field_149069_g = true;
         this.onGround = boolean4;
      }

      @Override
      public void readPacketData(PacketBuffer buffer) throws IOException {
         super.readPacketData(buffer);
         this.yaw = buffer.readByte();
         this.pitch = buffer.readByte();
         this.onGround = buffer.readBoolean();
      }

      @Override
      public void writePacketData(PacketBuffer buffer) throws IOException {
         super.writePacketData(buffer);
         buffer.writeByte(this.yaw);
         buffer.writeByte(this.pitch);
         buffer.writeBoolean(this.onGround);
      }
   }

   public static class S17PacketEntityLookMove extends S14PacketEntity {
      public S17PacketEntityLookMove() {
         this.field_149069_g = true;
      }

      public S17PacketEntityLookMove(int integer, byte byte2, byte byte3, byte byte4, byte byte5, byte byte6, boolean boolean7) {
         super(integer);
         this.posX = byte2;
         this.posY = byte3;
         this.posZ = byte4;
         this.yaw = byte5;
         this.pitch = byte6;
         this.onGround = boolean7;
         this.field_149069_g = true;
      }

      @Override
      public void readPacketData(PacketBuffer buffer) throws IOException {
         super.readPacketData(buffer);
         this.posX = buffer.readByte();
         this.posY = buffer.readByte();
         this.posZ = buffer.readByte();
         this.yaw = buffer.readByte();
         this.pitch = buffer.readByte();
         this.onGround = buffer.readBoolean();
      }

      @Override
      public void writePacketData(PacketBuffer buffer) throws IOException {
         super.writePacketData(buffer);
         buffer.writeByte(this.posX);
         buffer.writeByte(this.posY);
         buffer.writeByte(this.posZ);
         buffer.writeByte(this.yaw);
         buffer.writeByte(this.pitch);
         buffer.writeBoolean(this.onGround);
      }
   }
}
