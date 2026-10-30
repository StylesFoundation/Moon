package net.minecraft.client.network;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import moom.l;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OldServerPinger {
   private static final Logger logger = LogManager.getLogger();
   private final List<NetworkManager> pingDestinations = Collections.synchronizedList(Lists.newArrayList());

   public void ping(ServerData serverData) throws UnknownHostException {
      ServerAddress var4 = ServerAddress.fromString(serverData.serverIP);
      final NetworkManager var5 = NetworkManager.createNetworkManagerAndConnect(InetAddress.getByName(var4.getIP()), var4.getPort(), false);
      this.pingDestinations.add(var5);
      serverData.d = "Pinging...";
      serverData.pingToServer = -1L;
      serverData.i = null;
      var5.setNetHandler(
         new INetHandlerStatusClient() {
            private boolean field_147403_d = false;
            private boolean field_183009_e = false;
            private long field_175092_e = 0L;

            @Override
            public void handleServerInfo(S00PacketServerInfo s00PacketServerInfo) {
               if (this.field_183009_e) {
                  var5.closeChannel(new ChatComponentText("Received unrequested status"));
               } else {
                  this.field_183009_e = true;
                  ServerStatusResponse serverStatus = s00PacketServerInfo.getResponse();
                  if (serverStatus.getServerDescription() != null) {
                     serverData.d = serverStatus.getServerDescription().getFormattedText();
                  } else {
                     serverData.d = "";
                  }

                  if (serverStatus.getProtocolVersionInfo() != null) {
                     serverData.g = serverStatus.getProtocolVersionInfo().getName();
                     serverData.version = serverStatus.getProtocolVersionInfo().getProtocol();
                  } else {
                     serverData.g = "Old";
                     serverData.version = 0;
                  }

                  if (serverStatus.getPlayerCountData() == null) {
                     serverData.c = EnumChatFormatting.DARK_GRAY + "???";
                  } else {
                     serverData.c = ""
                        + EnumChatFormatting.GRAY
                        + serverStatus.getPlayerCountData().getOnlinePlayerCount()
                        + EnumChatFormatting.DARK_GRAY
                        + "/"
                        + EnumChatFormatting.GRAY
                        + serverStatus.getPlayerCountData().getMaxPlayers();
                     if (ArrayUtils.isNotEmpty(serverStatus.getPlayerCountData().getPlayers())) {
                        StringBuilder var6 = new StringBuilder();

                        for (GameProfile var10 : serverStatus.getPlayerCountData().getPlayers()) {
                           if (var6.length() > 0) {
                              var6.append("\n");
                           }

                           var6.append(var10.getName());
                        }

                        if (serverStatus.getPlayerCountData().getPlayers().length < serverStatus.getPlayerCountData().getOnlinePlayerCount()) {
                           if (var6.length() > 0) {
                              var6.append("\n");
                           }

                           var6.append("... and ")
                              .append(serverStatus.getPlayerCountData().getOnlinePlayerCount() - serverStatus.getPlayerCountData().getPlayers().length)
                              .append(" more ...");
                        }

                        serverData.i = var6.toString();
                     }
                  }

                  if (serverStatus.getFavicon() != null) {
                     String var11 = serverStatus.getFavicon();
                     if (var11.startsWith("data:image/png;base64,")) {
                        serverData.setBase64EncodedIconData(var11.substring(22));
                     } else {
                        OldServerPinger.logger.error("Invalid server icon (unknown format)");
                     }
                  } else {
                     serverData.setBase64EncodedIconData(null);
                  }

                  this.field_175092_e = Minecraft.getSystemTime();
                  var5.sendPacket(new C01PacketPing(this.field_175092_e));
                  this.field_147403_d = true;
               }
            }

            @Override
            public void handlePong(S01PacketPong s01PacketPong) {
               long var2 = this.field_175092_e;
               long var4 = Minecraft.getSystemTime();
               serverData.pingToServer = var4 - var2;
               var5.closeChannel(new ChatComponentText("Finished"));
            }

            @Override
            public void onDisconnect(IChatComponent iChatComponent) {
               if (!this.field_147403_d) {
                  OldServerPinger.logger.error("Can't ping " + serverData.serverIP + ": " + iChatComponent.getUnformattedText());
                  serverData.d = EnumChatFormatting.DARK_RED + "Can't connect to server.";
                  serverData.c = "";
                  OldServerPinger.this.tryCompatibilityPing(serverData);
               }
            }
         }
      );

      try {
         var5.sendPacket(new C00Handshake(47, var4.getIP(), var4.getPort(), EnumConnectionState.STATUS));
         var5.sendPacket(new C00PacketServerQuery());
      } catch (Throwable var7) {
         logger.error(var7);
      }
   }

   private void tryCompatibilityPing(ServerData serverData) {
      ServerAddress var2 = ServerAddress.fromString(serverData.serverIP);
      ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.CLIENT_NIO_EVENTLOOP.getValue()))
               .handler(new l(this, var2, serverData)))
            .channel(NioSocketChannel.class))
         .connect(var2.getIP(), var2.getPort());
   }

   public void pingPendingNetworks() {
      synchronized (this.pingDestinations) {
         Iterator var5 = this.pingDestinations.iterator();

         while (var5.hasNext()) {
            NetworkManager var6 = (NetworkManager)var5.next();
            if (var6.isChannelOpen()) {
               var6.processReceivedPackets();
            } else {
               var5.remove();
               var6.checkDisconnected();
            }
         }
      }
   }

   public void clearPendingNetworks() {
      synchronized (this.pingDestinations) {
         Iterator var5 = this.pingDestinations.iterator();

         while (var5.hasNext()) {
            NetworkManager var6 = (NetworkManager)var5.next();
            if (var6.isChannelOpen()) {
               var5.remove();
               var6.closeChannel(new ChatComponentText("Cancelled"));
            }
         }
      }
   }
}
