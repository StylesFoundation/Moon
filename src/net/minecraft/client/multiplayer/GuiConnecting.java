package net.minecraft.client.multiplayer;

import com.moon.Moon;
import com.moon.events.impl.network.ConnectServerEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiConnecting extends GuiScreen {
   private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
   private static final Logger logger = LogManager.getLogger();
   private NetworkManager networkManager;
   private boolean cancel;
   private final GuiScreen previousGuiScreen;

   public GuiConnecting(GuiScreen guiScreen, Minecraft minecraft, ServerData serverData) {
      this.mc = minecraft;
      this.previousGuiScreen = guiScreen;
      ServerAddress var4 = ServerAddress.fromString(serverData.serverIP);
      minecraft.loadWorld(null);
      minecraft.setServerData(serverData);
      this.connect(var4.getIP(), var4.getPort());
   }

   public GuiConnecting(GuiScreen guiScreen, Minecraft minecraft, String string, int integer) {
      this.mc = minecraft;
      this.previousGuiScreen = guiScreen;
      minecraft.loadWorld(null);
      this.connect(string, integer);
   }

   private void connect(String string, int integer) {
      logger.info("Connecting to " + string + ", " + integer);
      Moon.getInstance().getEventBus().publish(new ConnectServerEvent(string, false));
      (new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet()) {
            @Override
            public void run() {
               InetAddress var4 = null;

               try {
                  if (GuiConnecting.this.cancel) {
                     return;
                  }

                  var4 = InetAddress.getByName(string);
                  GuiConnecting.this.networkManager = NetworkManager.createNetworkManagerAndConnect(
                     var4, integer, GuiConnecting.access$200(GuiConnecting.this).gameSettings.isUsingNativeTransport()
                  );
                  GuiConnecting.this.networkManager
                     .setNetHandler(
                        new NetHandlerLoginClient(
                           GuiConnecting.this.networkManager, GuiConnecting.access$300(GuiConnecting.this), GuiConnecting.this.previousGuiScreen
                        )
                     );
                  GuiConnecting.this.networkManager.sendPacket(new C00Handshake(47, string, integer, EnumConnectionState.LOGIN));
                  GuiConnecting.this.networkManager.sendPacket(new C00PacketLoginStart(GuiConnecting.access$500(GuiConnecting.this).getSession().getProfile()));
               } catch (UnknownHostException var8) {
                  if (GuiConnecting.this.cancel) {
                     return;
                  }

                  GuiConnecting.logger.error("Couldn't connect to server", var8);
                  GuiConnecting.access$700(GuiConnecting.this)
                     .displayGuiScreen(
                        new GuiDisconnected(
                           GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", "Unknown host")
                        )
                     );
               } catch (Exception var9) {
                  if (GuiConnecting.this.cancel) {
                     return;
                  }

                  GuiConnecting.logger.error("Couldn't connect to server", var9);
                  String var6 = var9.toString();
                  if (var4 != null) {
                     String var7 = var4 + ":" + integer;
                     var6 = var6.replaceAll(var7, "");
                  }

                  GuiConnecting.access$800(GuiConnecting.this)
                     .displayGuiScreen(
                        new GuiDisconnected(
                           GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", var6)
                        )
                     );
               }
            }
         })
         .start();
   }

   @Override
   public void onUpdateScreen() {
      if (this.networkManager != null) {
         if (this.networkManager.isChannelOpen()) {
            this.networkManager.processReceivedPackets();
         } else {
            this.networkManager.checkDisconnected();
         }
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
   }

   @Override
   public void onInitGui() {
      this.m.clear();
      this.m.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel")));
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.l == 0) {
         this.cancel = true;
         if (this.networkManager != null) {
            this.networkManager.closeChannel(new ChatComponentText("Aborted"));
         }

         this.mc.displayGuiScreen(this.previousGuiScreen);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      if (this.networkManager == null) {
         this.drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting"), this.width / 2, this.height / 2 - 50, 16777215);
      } else {
         this.drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing"), this.width / 2, this.height / 2 - 50, 16777215);
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   static Minecraft access$200(GuiConnecting guiConnecting) {
      return guiConnecting.mc;
   }

   static Minecraft access$300(GuiConnecting guiConnecting) {
      return guiConnecting.mc;
   }

   static Minecraft access$500(GuiConnecting guiConnecting) {
      return guiConnecting.mc;
   }

   static Minecraft access$700(GuiConnecting guiConnecting) {
      return guiConnecting.mc;
   }

   static Minecraft access$800(GuiConnecting guiConnecting) {
      return guiConnecting.mc;
   }
}
