package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S08PacketPlayerPosLook implements Packet<INetHandlerPlayClient> {
   private double x;
   private double y;
   private double z;
   private float yaw;
   private float pitch;
   private Set<S08PacketPlayerPosLook.EnumFlags> field_179835_f;

   public S08PacketPlayerPosLook() {
   }

   public S08PacketPlayerPosLook(double double1, double double2, double double3, float float4, float float5, Set<S08PacketPlayerPosLook.EnumFlags> set) {
      this.x = double1;
      this.y = double2;
      this.z = double3;
      this.yaw = float4;
      this.pitch = float5;
      this.field_179835_f = set;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.x = buffer.readDouble();
      this.y = buffer.readDouble();
      this.z = buffer.readDouble();
      this.yaw = buffer.readFloat();
      this.pitch = buffer.readFloat();
      this.field_179835_f = S08PacketPlayerPosLook.EnumFlags.func_180053_a(buffer.readUnsignedByte());
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeDouble(this.x);
      buffer.writeDouble(this.y);
      buffer.writeDouble(this.z);
      buffer.writeFloat(this.yaw);
      buffer.writeFloat(this.pitch);
      buffer.writeByte(S08PacketPlayerPosLook.EnumFlags.func_180056_a(this.field_179835_f));
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handlePlayerPosLook(this);
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public float getYaw() {
      return this.yaw;
   }

   public float Method909() {
      return this.pitch;
   }

   public Set<S08PacketPlayerPosLook.EnumFlags> func_179834_f() {
      return this.field_179835_f;
   }

   public S08PacketPlayerPosLook Method910(double double1) {
      this.x = double1;
      return this;
   }

   public S08PacketPlayerPosLook Method911(double double1) {
      this.y = double1;
      return this;
   }

   public S08PacketPlayerPosLook Method912(double double1) {
      this.z = double1;
      return this;
   }

   public S08PacketPlayerPosLook Method913(float float1) {
      this.yaw = float1;
      return this;
   }

   public S08PacketPlayerPosLook Method914(float float1) {
      this.pitch = float1;
      return this;
   }

   public S08PacketPlayerPosLook Method915(Set<S08PacketPlayerPosLook.EnumFlags> set) {
      this.field_179835_f = set;
      return this;
   }

   public enum EnumFlags {
      X(0),
      Y(1),
      Z(2),
      Y_ROT(3),
      X_ROT(4);

      private final int field_180058_f;

      EnumFlags(int integer3) {
         this.field_180058_f = integer3;
      }

      private int func_180055_a() {
         return 1 << this.field_180058_f;
      }

      private boolean func_180054_b(int integer) {
         return (integer & this.func_180055_a()) == this.func_180055_a();
      }

      public static Set<S08PacketPlayerPosLook.EnumFlags> func_180053_a(int integer) {
         EnumSet var4 = EnumSet.noneOf(S08PacketPlayerPosLook.EnumFlags.class);

         for (S08PacketPlayerPosLook.EnumFlags var8 : values()) {
            if (var8.func_180054_b(integer)) {
               var4.add(var8);
            }
         }

         return var4;
      }

      public static int func_180056_a(Set<S08PacketPlayerPosLook.EnumFlags> set) {
         int var4 = 0;

         for (S08PacketPlayerPosLook.EnumFlags var6 : set) {
            var4 |= var6.func_180055_a();
         }

         return var4;
      }

      private static S08PacketPlayerPosLook.EnumFlags[] Method402() {
         return new S08PacketPlayerPosLook.EnumFlags[]{X, Y, Z, Y_ROT, X_ROT};
      }
   }
}
