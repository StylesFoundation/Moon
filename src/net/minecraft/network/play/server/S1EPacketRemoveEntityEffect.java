package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.PotionEffect;

public class S1EPacketRemoveEntityEffect implements Packet<INetHandlerPlayClient> {
   private int entityId;
   private int effectId;

   public S1EPacketRemoveEntityEffect() {
   }

   public S1EPacketRemoveEntityEffect(int integer, PotionEffect potionEffect) {
      this.entityId = integer;
      this.effectId = potionEffect.getPotionID();
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readVarIntFromBuffer();
      this.effectId = buffer.readUnsignedByte();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityId);
      buffer.writeByte(this.effectId);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleRemoveEntityEffect(this);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public int getEffectId() {
      return this.effectId;
   }
}
