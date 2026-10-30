package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S1FPacketSetExperience implements Packet<INetHandlerPlayClient> {
   private float field_149401_a;
   private int totalExperience;
   private int level;

   public S1FPacketSetExperience() {
   }

   public S1FPacketSetExperience(float float1, int integer2, int integer3) {
      this.field_149401_a = float1;
      this.totalExperience = integer2;
      this.level = integer3;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.field_149401_a = buffer.readFloat();
      this.level = buffer.readVarIntFromBuffer();
      this.totalExperience = buffer.readVarIntFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeFloat(this.field_149401_a);
      buffer.writeVarIntToBuffer(this.level);
      buffer.writeVarIntToBuffer(this.totalExperience);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleSetExperience(this);
   }

   public float func_149397_c() {
      return this.field_149401_a;
   }

   public int getTotalExperience() {
      return this.totalExperience;
   }

   public int getLevel() {
      return this.level;
   }
}
