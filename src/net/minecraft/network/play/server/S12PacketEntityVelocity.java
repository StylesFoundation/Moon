package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S12PacketEntityVelocity implements Packet<INetHandlerPlayClient> {
   private int entityID;
   private int b;
   private int c;
   private int d;

   public S12PacketEntityVelocity() {
   }

   public S12PacketEntityVelocity(Entity entity) {
      this(entity.getEntityId(), entity.motionX, entity.motionY, entity.motionZ);
   }

   public S12PacketEntityVelocity(int integer, double double2, double double3, double double4) {
      this.entityID = integer;
      if (double2 < -3.9) {
         double2 = -3.9;
      }

      if (double3 < -3.9) {
         double3 = -3.9;
      }

      if (double4 < -3.9) {
         double4 = -3.9;
      }

      if (double2 > 3.9) {
         double2 = 3.9;
      }

      if (double3 > 3.9) {
         double3 = 3.9;
      }

      if (double4 > 3.9) {
         double4 = 3.9;
      }

      this.b = (int)(double2 * 8000.0);
      this.c = (int)(double3 * 8000.0);
      this.d = (int)(double4 * 8000.0);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityID = buffer.readVarIntFromBuffer();
      this.b = buffer.readShort();
      this.c = buffer.readShort();
      this.d = buffer.readShort();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityID);
      buffer.writeShort(this.b);
      buffer.writeShort(this.c);
      buffer.writeShort(this.d);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleEntityVelocity(this);
   }

   public int getEntityID() {
      return this.entityID;
   }

   public int getMotionZ() {
      return this.b;
   }

   public int getMotionX() {
      return this.c;
   }

   public int getMotionY() {
      return this.d;
   }

   public void Method578(int integer) {
      this.entityID = integer;
   }

   public void Method579(int integer) {
      this.b = integer;
   }

   public void Method580(int integer) {
      this.c = integer;
   }

   public void Method581(int integer) {
      this.d = integer;
   }
}
