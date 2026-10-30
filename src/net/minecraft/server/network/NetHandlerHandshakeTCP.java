package net.minecraft.server.network;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class NetHandlerHandshakeTCP implements INetHandlerHandshakeServer {
   private final MinecraftServer server;
   private final NetworkManager networkManager;

   public NetHandlerHandshakeTCP(MinecraftServer minecraftServer, NetworkManager networkManager) {
      this.server = minecraftServer;
      this.networkManager = networkManager;
   }

   @Override
   public void processHandshake(C00Handshake c00Handshake) {
      switch (c00Handshake.getRequestedState()) {
         case LOGIN:
            this.networkManager.setConnectionState(EnumConnectionState.LOGIN);
            if (c00Handshake.getProtocolVersion() > 47) {
               ChatComponentText var5 = new ChatComponentText("Outdated server! I'm still on 1.8.9");
               this.networkManager.sendPacket(new S00PacketDisconnect(var5));
               this.networkManager.closeChannel(var5);
            } else if (c00Handshake.getProtocolVersion() < 47) {
               ChatComponentText var6 = new ChatComponentText("Outdated client! Please use 1.8.9");
               this.networkManager.sendPacket(new S00PacketDisconnect(var6));
               this.networkManager.closeChannel(var6);
            } else {
               this.networkManager.setNetHandler(new NetHandlerLoginServer(this.server, this.networkManager));
            }
            break;
         case STATUS:
            this.networkManager.setConnectionState(EnumConnectionState.STATUS);
            this.networkManager.setNetHandler(new NetHandlerStatusServer(this.server, this.networkManager));
            break;
         default:
            throw new UnsupportedOperationException("Invalid intention " + c00Handshake.getRequestedState());
      }
   }

   @Override
   public void onDisconnect(IChatComponent iChatComponent) {
   }
}
