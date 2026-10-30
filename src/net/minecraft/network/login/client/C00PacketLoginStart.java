package net.minecraft.network.login.client;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;

public class C00PacketLoginStart implements Packet<INetHandlerLoginServer> {
   private GameProfile profile;

   public C00PacketLoginStart() {
   }

   public C00PacketLoginStart(GameProfile gameProfile) {
      this.profile = gameProfile;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.profile = new GameProfile(null, buffer.readStringFromBuffer(16));
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeString(this.profile.getName());
   }

   public void processPacket(INetHandlerLoginServer handler) {
      handler.processLoginStart(this);
   }

   public GameProfile getProfile() {
      return this.profile;
   }
}
