package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S06PacketUpdateHealth implements Packet<INetHandlerPlayClient> {
   private float health;
   private int foodLevel;
   private float saturationLevel;

   public S06PacketUpdateHealth() {
   }

   public S06PacketUpdateHealth(float float1, int integer, float float3) {
      this.health = float1;
      this.foodLevel = integer;
      this.saturationLevel = float3;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.health = buffer.readFloat();
      this.foodLevel = buffer.readVarIntFromBuffer();
      this.saturationLevel = buffer.readFloat();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeFloat(this.health);
      buffer.writeVarIntToBuffer(this.foodLevel);
      buffer.writeFloat(this.saturationLevel);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleUpdateHealth(this);
   }

   public float getHealth() {
      return this.health;
   }

   public int getFoodLevel() {
      return this.foodLevel;
   }

   public float getSaturationLevel() {
      return this.saturationLevel;
   }
}
