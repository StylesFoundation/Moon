package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.moon.viamcp.ViaMCP;
import com.moon.viamcp.gui.VersionSlider;
import com.moon.viamcp.protocols.ProtocolCollection;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector$LanServer;
import net.minecraft.client.network.LanServerDetector$LanServerList;
import net.minecraft.client.network.LanServerDetector$ThreadLanServerFind;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.resources.I18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiMultiplayer extends GuiScreen implements GuiYesNoCallback {
   private static final Logger logger = LogManager.getLogger();
   private final OldServerPinger oldServerPinger = new OldServerPinger();
   private final GuiScreen parentScreen;
   private ServerSelectionList serverListSelector;
   private ServerList savedServerList;
   private GuiButton btnEditServer;
   private GuiButton btnSelectServer;
   private GuiButton btnDeleteServer;
   private boolean deletingServer;
   private boolean addingServer;
   private boolean editingServer;
   private boolean directConnect;
   private String hoveringText;
   private ServerData selectedServer;
   private LanServerDetector$LanServerList lanServerList;
   private LanServerDetector$ThreadLanServerFind lanServerDetector;
   private boolean initialized;

   public GuiMultiplayer(GuiScreen guiScreen) {
      this.parentScreen = guiScreen;
   }

   @Override
   public void onInitGui() {
      Keyboard.enableRepeatEvents(true);
      this.m.clear();
      if (!this.initialized) {
         this.initialized = true;
         this.savedServerList = new ServerList(this.mc);
         this.savedServerList.loadServerList();
         this.lanServerList = new LanServerDetector$LanServerList();

         try {
            this.lanServerDetector = new LanServerDetector$ThreadLanServerFind(this.lanServerList);
            this.lanServerDetector.start();
         } catch (Exception var4) {
            logger.warn("Unable to start LAN server detection: " + var4.getMessage());
         }

         this.serverListSelector = new ServerSelectionList(this, this.mc, this.width, this.height, 32, this.height - 64, 36);
         this.serverListSelector.func_148195_a(this.savedServerList);
      } else {
         this.serverListSelector.setDimensions(this.width, this.height, 32, this.height - 64);
      }

      this.createButtons();
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.serverListSelector.handleMouseInput();
   }

   public void createButtons() {
      this.m.add(this.btnEditServer = new GuiButton(7, this.width / 2 - 154, this.height - 28, 70, 20, I18n.format("selectServer.edit")));
      this.m.add(this.btnDeleteServer = new GuiButton(2, this.width / 2 - 74, this.height - 28, 70, 20, I18n.format("selectServer.delete")));
      this.m.add(this.btnSelectServer = new GuiButton(1, this.width / 2 - 154, this.height - 52, 100, 20, I18n.format("selectServer.select")));
      this.m.add(new GuiButton(4, this.width / 2 - 50, this.height - 52, 100, 20, I18n.format("selectServer.direct")));
      this.m.add(new GuiButton(3, this.width / 2 + 4 + 50, this.height - 52, 100, 20, I18n.format("selectServer.add")));
      this.m.add(new GuiButton(8, this.width / 2 + 4, this.height - 28, 70, 20, I18n.format("selectServer.refresh")));
      this.m.add(new GuiButton(0, this.width / 2 + 4 + 76, this.height - 28, 75, 20, I18n.format("gui.cancel")));
      this.m
         .add(
            new VersionSlider(
               756,
               this.width - 127,
               12,
               "Test Slider",
               0,
               ProtocolCollection.values().length - 1,
               ProtocolCollection.values().length - ProtocolCollection.Method571(ViaMCP.getInstance().getVersion()).ordinal() - 1
            )
         );
      this.selectServer(this.serverListSelector.func_148193_k());
   }

   @Override
   public void onUpdateScreen() {
      super.onUpdateScreen();
      if (this.lanServerList.getWasUpdated()) {
         List var1 = this.lanServerList.getLanServers();
         this.lanServerList.setWasNotUpdated();
         this.serverListSelector.func_148194_a(var1);
      }

      this.oldServerPinger.pingPendingNetworks();
   }

   @Override
   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      if (this.lanServerDetector != null) {
         this.lanServerDetector.interrupt();
         this.lanServerDetector = null;
      }

      this.oldServerPinger.clearPendingNetworks();
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         GuiListExtended.IGuiListEntry var4 = this.serverListSelector.func_148193_k() < 0
            ? null
            : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
         if (guiButton.l == 2 && var4 instanceof ServerListEntryNormal) {
            String var11 = ((ServerListEntryNormal)var4).getServerData().serverName;
            if (var11 != null) {
               this.deletingServer = true;
               String var6 = I18n.format("selectServer.deleteQuestion");
               String var7 = "'" + var11 + "' " + I18n.format("selectServer.deleteWarning");
               String var8 = I18n.format("selectServer.deleteButton");
               String var9 = I18n.format("gui.cancel");
               GuiYesNo var10 = new GuiYesNo(this, var6, var7, var8, var9, this.serverListSelector.func_148193_k());
               this.mc.displayGuiScreen(var10);
            }
         } else if (guiButton.l == 1) {
            this.connectToSelected();
         } else if (guiButton.l == 4) {
            this.directConnect = true;
            this.mc.displayGuiScreen(new GuiScreenServerList(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName"), "", false)));
         } else if (guiButton.l == 3) {
            this.addingServer = true;
            this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName"), "", false)));
         } else if (guiButton.l == 7 && var4 instanceof ServerListEntryNormal) {
            this.editingServer = true;
            ServerData var5 = ((ServerListEntryNormal)var4).getServerData();
            this.selectedServer = new ServerData(var5.serverName, var5.serverIP, false);
            this.selectedServer.copyFrom(var5);
            this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer));
         } else if (guiButton.l == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
         } else if (guiButton.l == 8) {
            this.refreshServerList();
         }
      }
   }

   private void refreshServerList() {
      this.mc.displayGuiScreen(new GuiMultiplayer(this.parentScreen));
   }

   @Override
   public void confirmClicked(boolean boolean1, int integer) {
      GuiListExtended.IGuiListEntry var3 = this.serverListSelector.func_148193_k() < 0
         ? null
         : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
      if (this.deletingServer) {
         this.deletingServer = false;
         if (boolean1 && var3 instanceof ServerListEntryNormal) {
            this.savedServerList.removeServerData(this.serverListSelector.func_148193_k());
            this.savedServerList.saveServerList();
            this.serverListSelector.setSelectedSlotIndex(-1);
            this.serverListSelector.func_148195_a(this.savedServerList);
         }

         this.mc.displayGuiScreen(this);
      } else if (this.directConnect) {
         this.directConnect = false;
         if (boolean1) {
            this.connectToServer(this.selectedServer);
         } else {
            this.mc.displayGuiScreen(this);
         }
      } else if (this.addingServer) {
         this.addingServer = false;
         if (boolean1) {
            this.savedServerList.addServerData(this.selectedServer);
            this.savedServerList.saveServerList();
            this.serverListSelector.setSelectedSlotIndex(-1);
            this.serverListSelector.func_148195_a(this.savedServerList);
         }

         this.mc.displayGuiScreen(this);
      } else if (this.editingServer) {
         this.editingServer = false;
         if (boolean1 && var3 instanceof ServerListEntryNormal) {
            ServerData var4 = ((ServerListEntryNormal)var3).getServerData();
            var4.serverName = this.selectedServer.serverName;
            var4.serverIP = this.selectedServer.serverIP;
            var4.copyFrom(this.selectedServer);
            this.savedServerList.saveServerList();
            this.serverListSelector.func_148195_a(this.savedServerList);
         }

         this.mc.displayGuiScreen(this);
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      int var5 = this.serverListSelector.func_148193_k();
      GuiListExtended.IGuiListEntry var6 = var5 < 0 ? null : this.serverListSelector.getListEntry(var5);
      if (keyCode == 63) {
         this.refreshServerList();
      } else if (var5 >= 0) {
         if (keyCode == 200) {
            if (isShiftKeyDown()) {
               if (var5 > 0 && var6 instanceof ServerListEntryNormal) {
                  this.savedServerList.swapServers(var5, var5 - 1);
                  this.selectServer(this.serverListSelector.func_148193_k() - 1);
                  this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                  this.serverListSelector.func_148195_a(this.savedServerList);
               }
            } else if (var5 > 0) {
               this.selectServer(this.serverListSelector.func_148193_k() - 1);
               this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
               if (this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan) {
                  if (this.serverListSelector.func_148193_k() > 0) {
                     this.selectServer(this.serverListSelector.getSize() - 1);
                     this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                  } else {
                     this.selectServer(-1);
                  }
               }
            } else {
               this.selectServer(-1);
            }
         } else if (keyCode == 208) {
            if (isShiftKeyDown()) {
               if (var5 < this.savedServerList.countServers() - 1) {
                  this.savedServerList.swapServers(var5, var5 + 1);
                  this.selectServer(var5 + 1);
                  this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                  this.serverListSelector.func_148195_a(this.savedServerList);
               }
            } else if (var5 < this.serverListSelector.getSize()) {
               this.selectServer(this.serverListSelector.func_148193_k() + 1);
               this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
               if (this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan) {
                  if (this.serverListSelector.func_148193_k() < this.serverListSelector.getSize() - 1) {
                     this.selectServer(this.serverListSelector.getSize() + 1);
                     this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                  } else {
                     this.selectServer(-1);
                  }
               }
            } else {
               this.selectServer(-1);
            }
         } else if (keyCode != 28 && keyCode != 156) {
            super.onKeyTyped(charTyped, keyCode);
         } else {
            this.actionPerformed(this.m.get(2));
         }
      } else {
         super.onKeyTyped(charTyped, keyCode);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.hoveringText = null;
      this.drawDefaultBackground();
      this.serverListSelector.drawScreen(mouseX, mouseY, partialTicks);
      this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.title"), this.width / 2, 20, 16777215);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
      if (this.hoveringText != null) {
         this.drawHoveringText(Lists.newArrayList(Splitter.on("\n").split(this.hoveringText)), mouseX, mouseY);
      }
   }

   public void connectToSelected() {
      GuiListExtended.IGuiListEntry var1 = this.serverListSelector.func_148193_k() < 0
         ? null
         : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
      if (var1 instanceof ServerListEntryNormal) {
         this.connectToServer(((ServerListEntryNormal)var1).getServerData());
      } else if (var1 instanceof ServerListEntryLanDetected) {
         LanServerDetector$LanServer var2 = ((ServerListEntryLanDetected)var1).getLanServer();
         this.connectToServer(new ServerData(var2.getServerMotd(), var2.getServerIpPort(), true));
      }
   }

   private void connectToServer(ServerData serverData) {
      this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, serverData));
   }

   public void selectServer(int integer) {
      this.serverListSelector.setSelectedSlotIndex(integer);
      GuiListExtended.IGuiListEntry var2 = integer < 0 ? null : this.serverListSelector.getListEntry(integer);
      this.btnSelectServer.enabled = false;
      this.btnEditServer.enabled = false;
      this.btnDeleteServer.enabled = false;
      if (var2 != null && !(var2 instanceof ServerListEntryLanScan)) {
         this.btnSelectServer.enabled = true;
         if (var2 instanceof ServerListEntryNormal) {
            this.btnEditServer.enabled = true;
            this.btnDeleteServer.enabled = true;
         }
      }
   }

   public OldServerPinger getOldServerPinger() {
      return this.oldServerPinger;
   }

   public void setHoveringText(String string) {
      this.hoveringText = string;
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      super.onMouseClicked(mouseX, mouseY, button);
      this.serverListSelector.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   protected void onMouseReleased(int mouseX, int mouseY, int button) {
      super.onMouseReleased(mouseX, mouseY, button);
      this.serverListSelector.mouseReleased(mouseX, mouseY, button);
   }

   public ServerList getServerList() {
      return this.savedServerList;
   }

   public boolean func_175392_a(ServerListEntryNormal serverListEntryNormal, int integer) {
      return integer > 0;
   }

   public boolean func_175394_b(ServerListEntryNormal serverListEntryNormal, int integer) {
      return integer < this.savedServerList.countServers() - 1;
   }

   public void func_175391_a(ServerListEntryNormal serverListEntryNormal, int integer, boolean boolean3) {
      int var4 = boolean3 ? 0 : integer - 1;
      this.savedServerList.swapServers(integer, var4);
      if (this.serverListSelector.func_148193_k() == integer) {
         this.selectServer(var4);
      }

      this.serverListSelector.func_148195_a(this.savedServerList);
   }

   public void func_175393_b(ServerListEntryNormal serverListEntryNormal, int integer, boolean boolean3) {
      int var4 = boolean3 ? this.savedServerList.countServers() - 1 : integer + 1;
      this.savedServerList.swapServers(integer, var4);
      if (this.serverListSelector.func_148193_k() == integer) {
         this.selectServer(var4);
      }

      this.serverListSelector.func_148195_a(this.savedServerList);
   }
}
