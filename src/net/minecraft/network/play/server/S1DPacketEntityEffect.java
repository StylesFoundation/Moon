package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.PotionEffect;

public class S1DPacketEntityEffect implements Packet<INetHandlerPlayClient> {
   private int entityId;
   private byte effectId;
   private byte amplifier;
   private int duration;
   private byte hideParticles;

   public S1DPacketEntityEffect() {
   }

   public S1DPacketEntityEffect(int integer, PotionEffect potionEffect) {
      this.entityId = integer;
      this.effectId = (byte)(potionEffect.getPotionID() & 0xFF);
      this.amplifier = (byte)(potionEffect.getAmplifier() & 0xFF);
      this.duration = Math.min(potionEffect.getDuration(), 32767);
      this.hideParticles = (byte)(potionEffect.getIsShowParticles() ? 1 : 0);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readVarIntFromBuffer();
      this.effectId = buffer.readByte();
      this.amplifier = buffer.readByte();
      this.duration = buffer.readVarIntFromBuffer();
      this.hideParticles = buffer.readByte();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityId);
      buffer.writeByte(this.effectId);
      buffer.writeByte(this.amplifier);
      buffer.writeVarIntToBuffer(this.duration);
      buffer.writeByte(this.hideParticles);
   }

   public boolean func_149429_c() {
      return this.duration == 32767;
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleEntityEffect(this);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public byte getEffectId() {
      return this.effectId;
   }

   public byte getAmplifier() {
      return this.amplifier;
   }

   public int getDuration() {
      return this.duration;
   }

   public boolean func_179707_f() {
      return this.hideParticles != 0;
   }
}
