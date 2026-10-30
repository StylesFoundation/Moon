package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.EnumParticleTypes;

public class S2APacketParticles implements Packet<INetHandlerPlayClient> {
   private EnumParticleTypes particleType;
   private float xCoord;
   private float yCoord;
   private float zCoord;
   private float xOffset;
   private float yOffset;
   private float zOffset;
   private float particleSpeed;
   private int particleCount;
   private boolean longDistance;
   private int[] particleArguments;

   public S2APacketParticles() {
   }

   public S2APacketParticles(
      EnumParticleTypes enumParticleTypes,
      boolean boolean2,
      float float3,
      float float4,
      float float5,
      float float6,
      float float7,
      float float8,
      float float9,
      int integer,
      int... arr
   ) {
      this.particleType = enumParticleTypes;
      this.longDistance = boolean2;
      this.xCoord = float3;
      this.yCoord = float4;
      this.zCoord = float5;
      this.xOffset = float6;
      this.yOffset = float7;
      this.zOffset = float8;
      this.particleSpeed = float9;
      this.particleCount = integer;
      this.particleArguments = arr;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.particleType = EnumParticleTypes.getParticleFromId(buffer.readInt());
      if (this.particleType == null) {
         this.particleType = EnumParticleTypes.BARRIER;
      }

      this.longDistance = buffer.readBoolean();
      this.xCoord = buffer.readFloat();
      this.yCoord = buffer.readFloat();
      this.zCoord = buffer.readFloat();
      this.xOffset = buffer.readFloat();
      this.yOffset = buffer.readFloat();
      this.zOffset = buffer.readFloat();
      this.particleSpeed = buffer.readFloat();
      this.particleCount = buffer.readInt();
      int var5 = this.particleType.getArgumentCount();
      this.particleArguments = new int[var5];

      for (int var6 = 0; var6 < var5; var6++) {
         this.particleArguments[var6] = buffer.readVarIntFromBuffer();
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeInt(this.particleType.getParticleID());
      buffer.writeBoolean(this.longDistance);
      buffer.writeFloat(this.xCoord);
      buffer.writeFloat(this.yCoord);
      buffer.writeFloat(this.zCoord);
      buffer.writeFloat(this.xOffset);
      buffer.writeFloat(this.yOffset);
      buffer.writeFloat(this.zOffset);
      buffer.writeFloat(this.particleSpeed);
      buffer.writeInt(this.particleCount);
      int var5 = this.particleType.getArgumentCount();

      for (int var6 = 0; var6 < var5; var6++) {
         buffer.writeVarIntToBuffer(this.particleArguments[var6]);
      }
   }

   public EnumParticleTypes getParticleType() {
      return this.particleType;
   }

   public boolean isLongDistance() {
      return this.longDistance;
   }

   public double getXCoordinate() {
      return this.xCoord;
   }

   public double getYCoordinate() {
      return this.yCoord;
   }

   public double getZCoordinate() {
      return this.zCoord;
   }

   public float getXOffset() {
      return this.xOffset;
   }

   public float getYOffset() {
      return this.yOffset;
   }

   public float getZOffset() {
      return this.zOffset;
   }

   public float getParticleSpeed() {
      return this.particleSpeed;
   }

   public int getParticleCount() {
      return this.particleCount;
   }

   public int[] getParticleArgs() {
      return this.particleArguments;
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleParticles(this);
   }
}
