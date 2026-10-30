package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector$LanServer;

public class ServerSelectionList extends GuiListExtended {
   private final GuiMultiplayer owner;
   private final List<ServerListEntryNormal> serverListInternet = Lists.newArrayList();
   private final List<ServerListEntryLanDetected> serverListLan = Lists.newArrayList();
   private final GuiListExtended.IGuiListEntry lanScanEntry = new ServerListEntryLanScan();
   private int selectedSlotIndex = -1;

   public ServerSelectionList(GuiMultiplayer guiMultiplayer, Minecraft minecraft, int integer3, int integer4, int integer5, int integer6, int integer7) {
      super(minecraft, integer3, integer4, integer5, integer6, integer7);
      this.owner = guiMultiplayer;
   }

   @Override
   public GuiListExtended.IGuiListEntry getListEntry(int integer) {
      if (integer < this.serverListInternet.size()) {
         return this.serverListInternet.get(integer);
      }

      integer -= this.serverListInternet.size();
      return integer == 0 ? this.lanScanEntry : this.serverListLan.get(--integer);
   }

   @Override
   protected int getSize() {
      return this.serverListInternet.size() + 1 + this.serverListLan.size();
   }

   public void setSelectedSlotIndex(int integer) {
      this.selectedSlotIndex = integer;
   }

   @Override
   protected boolean isSelected(int index) {
      return index == this.selectedSlotIndex;
   }

   public int func_148193_k() {
      return this.selectedSlotIndex;
   }

   public void func_148195_a(ServerList serverList) {
      this.serverListInternet.clear();

      for (int var2 = 0; var2 < serverList.countServers(); var2++) {
         this.serverListInternet.add(new ServerListEntryNormal(this.owner, serverList.getServerData(var2)));
      }
   }

   public void func_148194_a(List<LanServerDetector$LanServer> list) {
      this.serverListLan.clear();

      for (LanServerDetector$LanServer var3 : list) {
         this.serverListLan.add(new ServerListEntryLanDetected(this.owner, var3));
      }
   }

   @Override
   protected int getScrollBarX() {
      return super.getScrollBarX() + 30;
   }

   @Override
   public int getListWidth() {
      return super.getListWidth() + 85;
   }
}
