package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C03PacketPlayer implements Packet<INetHandlerPlayServer> {
   protected double x;
   protected double y;
   protected double c;
   protected float yaw;
   protected float e;
   protected boolean onGround;
   protected boolean moving;
   protected boolean rotating;
   protected boolean i;

   public C03PacketPlayer() {
   }

   public C03PacketPlayer(boolean boolean1) {
      this.onGround = boolean1;
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processPlayer(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.onGround = buffer.readUnsignedByte() != 0;
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.onGround ? 1 : 0);
   }

   public double getPositionX() {
      return this.x;
   }

   public double Method469() {
      return this.y;
   }

   public double getPositionY() {
      return this.c;
   }

   public float getYaw() {
      return this.yaw;
   }

   public float Method470() {
      return this.e;
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public boolean Method471() {
      return this.moving;
   }

   public boolean Method472() {
      return this.rotating;
   }

   public void setMoving(boolean boolean1) {
      this.moving = boolean1;
   }

   public boolean Method473() {
      return this.i;
   }

   public void Method474(boolean boolean1) {
      this.i = boolean1;
   }

   public double getPositionZ() {
      return this.x;
   }

   public double Method475() {
      return this.y;
   }

   public double Method476() {
      return this.c;
   }

   public float getPitch() {
      return this.yaw;
   }

   public float Method477() {
      return this.e;
   }

   public boolean Method478() {
      return this.onGround;
   }

   public boolean Method479() {
      return this.moving;
   }

   public boolean Method480() {
      return this.rotating;
   }

   public boolean Method481() {
      return this.i;
   }

   public C03PacketPlayer Method482(double double1) {
      this.x = double1;
      return this;
   }

   public C03PacketPlayer Method483(double double1) {
      this.y = double1;
      return this;
   }

   public C03PacketPlayer Method484(double double1) {
      this.c = double1;
      return this;
   }

   public C03PacketPlayer Method485(float float1) {
      this.yaw = float1;
      return this;
   }

   public C03PacketPlayer Method486(float float1) {
      this.e = float1;
      return this;
   }

   public C03PacketPlayer Method487(boolean boolean1) {
      this.onGround = boolean1;
      return this;
   }

   public C03PacketPlayer Method488(boolean boolean1) {
      this.moving = boolean1;
      return this;
   }

   public C03PacketPlayer Method489(boolean boolean1) {
      this.rotating = boolean1;
      return this;
   }

   public C03PacketPlayer Method490(boolean boolean1) {
      this.i = boolean1;
      return this;
   }

   public static class C04PacketPlayerPosition extends C03PacketPlayer {
      public C04PacketPlayerPosition() {
         this.moving = true;
      }

      public C04PacketPlayerPosition(double double1, double double2, double double3, boolean boolean4) {
         this.x = double1;
         this.y = double2;
         this.c = double3;
         this.onGround = boolean4;
         this.moving = true;
      }

      @Override
      public void readPacketData(PacketBuffer buffer) throws IOException {
         this.x = buffer.readDouble();
         this.y = buffer.readDouble();
         this.c = buffer.readDouble();
         super.readPacketData(buffer);
      }

      @Override
      public void writePacketData(PacketBuffer buffer) throws IOException {
         buffer.writeDouble(this.x);
         buffer.writeDouble(this.y);
         buffer.writeDouble(this.c);
         super.writePacketData(buffer);
      }
   }

   public static class C05PacketPlayerLook extends C03PacketPlayer {
      public C05PacketPlayerLook() {
         this.rotating = true;
      }

      public C05PacketPlayerLook(float float1, float float2, boolean boolean3) {
         this.yaw = float1;
         this.e = float2;
         this.onGround = boolean3;
         this.rotating = true;
      }

      @Override
      public void readPacketData(PacketBuffer buffer) throws IOException {
         this.yaw = buffer.readFloat();
         this.e = buffer.readFloat();
         super.readPacketData(buffer);
      }

      @Override
      public void writePacketData(PacketBuffer buffer) throws IOException {
         buffer.writeFloat(this.yaw);
         buffer.writeFloat(this.e);
         super.writePacketData(buffer);
      }
   }

   public static class C06PacketPlayerPosLook extends C03PacketPlayer {
      public C06PacketPlayerPosLook() {
         this.moving = true;
         this.rotating = true;
      }

      public C06PacketPlayerPosLook(double double1, double double2, double double3, float float4, float float5, boolean boolean6) {
         this.x = double1;
         this.y = double2;
         this.c = double3;
         this.yaw = float4;
         this.e = float5;
         this.onGround = boolean6;
         this.rotating = true;
         this.moving = true;
      }

      @Override
      public void readPacketData(PacketBuffer buffer) throws IOException {
         this.x = buffer.readDouble();
         this.y = buffer.readDouble();
         this.c = buffer.readDouble();
         this.yaw = buffer.readFloat();
         this.e = buffer.readFloat();
         super.readPacketData(buffer);
      }

      @Override
      public void writePacketData(PacketBuffer buffer) throws IOException {
         buffer.writeDouble(this.x);
         buffer.writeDouble(this.y);
         buffer.writeDouble(this.c);
         buffer.writeFloat(this.yaw);
         buffer.writeFloat(this.e);
         super.writePacketData(buffer);
      }
   }
}
