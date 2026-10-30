package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S39PacketPlayerAbilities implements Packet<INetHandlerPlayClient> {
   private boolean invulnerable;
   private boolean flying;
   private boolean allowFlying;
   private boolean creativeMode;
   private float flySpeed;
   private float walkSpeed;

   public S39PacketPlayerAbilities() {
   }

   public S39PacketPlayerAbilities(PlayerCapabilities playerCapabilities) {
      this.setInvulnerable(playerCapabilities.disableDamage);
      this.setFlying(playerCapabilities.isFlying);
      this.setAllowFlying(playerCapabilities.allowFlying);
      this.setCreativeMode(playerCapabilities.isCreativeMode);
      this.setFlySpeed(playerCapabilities.getFlySpeed());
      this.setWalkSpeed(playerCapabilities.getWalkSpeed());
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      byte var5 = buffer.readByte();
      this.setInvulnerable((var5 & 1) > 0);
      this.setFlying((var5 & 2) > 0);
      this.setAllowFlying((var5 & 4) > 0);
      this.setCreativeMode((var5 & 8) > 0);
      this.setFlySpeed(buffer.readFloat());
      this.setWalkSpeed(buffer.readFloat());
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      byte var5 = 0;
      if (this.isInvulnerable()) {
         var5 = 1;
      }

      if (this.isFlying()) {
         var5 = (byte)(var5 | 2);
      }

      if (this.isAllowFlying()) {
         var5 = (byte)(var5 | 4);
      }

      if (this.isCreativeMode()) {
         var5 = (byte)(var5 | 8);
      }

      buffer.writeByte(var5);
      buffer.writeFloat(this.flySpeed);
      buffer.writeFloat(this.walkSpeed);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handlePlayerAbilities(this);
   }

   public boolean isInvulnerable() {
      return this.invulnerable;
   }

   public void setInvulnerable(boolean boolean1) {
      this.invulnerable = boolean1;
   }

   public boolean isFlying() {
      return this.flying;
   }

   public void setFlying(boolean boolean1) {
      this.flying = boolean1;
   }

   public boolean isAllowFlying() {
      return this.allowFlying;
   }

   public void setAllowFlying(boolean boolean1) {
      this.allowFlying = boolean1;
   }

   public boolean isCreativeMode() {
      return this.creativeMode;
   }

   public void setCreativeMode(boolean boolean1) {
      this.creativeMode = boolean1;
   }

   public float getFlySpeed() {
      return this.flySpeed;
   }

   public void setFlySpeed(float float1) {
      this.flySpeed = float1;
   }

   public float getWalkSpeed() {
      return this.walkSpeed;
   }

   public void setWalkSpeed(float float1) {
      this.walkSpeed = float1;
   }
}
