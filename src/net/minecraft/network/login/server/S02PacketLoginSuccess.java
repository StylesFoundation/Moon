package net.minecraft.network.login.server;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

public class S02PacketLoginSuccess implements Packet<INetHandlerLoginClient> {
   private GameProfile profile;

   public S02PacketLoginSuccess() {
   }

   public S02PacketLoginSuccess(GameProfile gameProfile) {
      this.profile = gameProfile;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      String var5 = buffer.readStringFromBuffer(36);
      String var6 = buffer.readStringFromBuffer(16);
      UUID var7 = UUID.fromString(var5);
      this.profile = new GameProfile(var7, var6);
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      UUID var5 = this.profile.getId();
      buffer.writeString(var5 == null ? "" : var5.toString());
      buffer.writeString(this.profile.getName());
   }

   public void processPacket(INetHandlerLoginClient handler) {
      handler.handleLoginSuccess(this);
   }

   public GameProfile getProfile() {
      return this.profile;
   }
}
