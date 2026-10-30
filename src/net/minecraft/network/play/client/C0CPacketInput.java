package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0CPacketInput implements Packet<INetHandlerPlayServer> {
   private float strafeSpeed;
   private float forwardSpeed;
   private boolean jumping;
   private boolean sneaking;

   public C0CPacketInput() {
   }

   public C0CPacketInput(float float1, float float2, boolean boolean3, boolean boolean4) {
      this.strafeSpeed = float1;
      this.forwardSpeed = float2;
      this.jumping = boolean3;
      this.sneaking = boolean4;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.strafeSpeed = buffer.readFloat();
      this.forwardSpeed = buffer.readFloat();
      byte var5 = buffer.readByte();
      this.jumping = (var5 & 1) > 0;
      this.sneaking = (var5 & 2) > 0;
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeFloat(this.strafeSpeed);
      buffer.writeFloat(this.forwardSpeed);
      byte var5 = 0;
      if (this.jumping) {
         var5 = 1;
      }

      if (this.sneaking) {
         var5 = (byte)(var5 | 2);
      }

      buffer.writeByte(var5);
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processInput(this);
   }

   public float getStrafeSpeed() {
      return this.strafeSpeed;
   }

   public float getForwardSpeed() {
      return this.forwardSpeed;
   }

   public boolean isJumping() {
      return this.jumping;
   }

   public boolean isSneaking() {
      return this.sneaking;
   }

   public float Method961() {
      return this.strafeSpeed;
   }

   public float Method962() {
      return this.forwardSpeed;
   }

   public boolean Method963() {
      return this.jumping;
   }

   public boolean Method964() {
      return this.sneaking;
   }

   public C0CPacketInput Method965(float float1) {
      this.strafeSpeed = float1;
      return this;
   }

   public C0CPacketInput Method966(float float1) {
      this.forwardSpeed = float1;
      return this;
   }

   public C0CPacketInput Method967(boolean boolean1) {
      this.jumping = boolean1;
      return this;
   }

   public C0CPacketInput Method968(boolean boolean1) {
      this.sneaking = boolean1;
      return this;
   }
}
