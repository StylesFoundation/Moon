package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C13PacketPlayerAbilities implements Packet<INetHandlerPlayServer> {
   private boolean a;
   private boolean b;
   private boolean c;
   private boolean d;
   private float e;
   private float f;

   public C13PacketPlayerAbilities() {
   }

   public C13PacketPlayerAbilities(PlayerCapabilities playerCapabilities) {
      this.setInvulnerable(playerCapabilities.disableDamage);
      this.setFlying(playerCapabilities.isFlying);
      this.setAllowFlying(playerCapabilities.allowFlying);
      this.setCreativeMode(playerCapabilities.isCreativeMode);
      this.setFlySpeed(playerCapabilities.getFlySpeed());
      this.setWalkSpeed(playerCapabilities.getWalkSpeed());
   }

   public C13PacketPlayerAbilities(boolean boolean1, boolean boolean2, boolean boolean3, boolean boolean4, float float5, float float6) {
      this.a = boolean1;
      this.b = boolean2;
      this.c = boolean3;
      this.d = boolean4;
      this.e = float5;
      this.f = float6;
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
      buffer.writeFloat(this.e);
      buffer.writeFloat(this.f);
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processPlayerAbilities(this);
   }

   public boolean isInvulnerable() {
      return this.a;
   }

   public void setInvulnerable(boolean boolean1) {
      this.a = boolean1;
   }

   public boolean isFlying() {
      return this.b;
   }

   public void setFlying(boolean boolean1) {
      this.b = boolean1;
   }

   public boolean isAllowFlying() {
      return this.c;
   }

   public void setAllowFlying(boolean boolean1) {
      this.c = boolean1;
   }

   public boolean isCreativeMode() {
      return this.d;
   }

   public void setCreativeMode(boolean boolean1) {
      this.d = boolean1;
   }

   public void setFlySpeed(float float1) {
      this.e = float1;
   }

   public void setWalkSpeed(float float1) {
      this.f = float1;
   }

   public boolean Method290() {
      return this.a;
   }

   public boolean Method291() {
      return this.b;
   }

   public boolean Method292() {
      return this.c;
   }

   public boolean Method293() {
      return this.d;
   }

   public float Method294() {
      return this.e;
   }

   public float Method295() {
      return this.f;
   }

   public C13PacketPlayerAbilities Method296(boolean boolean1) {
      this.a = boolean1;
      return this;
   }

   public C13PacketPlayerAbilities Method297(boolean boolean1) {
      this.b = boolean1;
      return this;
   }

   public C13PacketPlayerAbilities Method298(boolean boolean1) {
      this.c = boolean1;
      return this;
   }

   public C13PacketPlayerAbilities Method299(boolean boolean1) {
      this.d = boolean1;
      return this;
   }

   public C13PacketPlayerAbilities Method300(float float1) {
      this.e = float1;
      return this;
   }

   public C13PacketPlayerAbilities Method301(float float1) {
      this.f = float1;
      return this;
   }
}
