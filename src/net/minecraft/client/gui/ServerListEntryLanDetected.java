package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.LanServerDetector$LanServer;
import net.minecraft.client.resources.I18n;

public class ServerListEntryLanDetected implements GuiListExtended.IGuiListEntry {
   private final GuiMultiplayer field_148292_c;
   protected final Minecraft mc;
   protected final LanServerDetector$LanServer field_148291_b;
   private long field_148290_d = 0L;

   protected ServerListEntryLanDetected(GuiMultiplayer guiMultiplayer, LanServerDetector$LanServer lanServer) {
      this.field_148292_c = guiMultiplayer;
      this.field_148291_b = lanServer;
      this.mc = Minecraft.getMinecraft();
   }

   @Override
   public void drawEntry(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, boolean boolean8) {
      this.mc.fontRendererObj.drawString(I18n.format("lanServer.title"), integer2 + 32 + 3, integer3 + 1, 16777215);
      this.mc.fontRendererObj.drawString(this.field_148291_b.getServerMotd(), integer2 + 32 + 3, integer3 + 12, 8421504);
      if (this.mc.gameSettings.hideServerAddress) {
         this.mc.fontRendererObj.drawString(I18n.format("selectServer.hiddenAddress"), integer2 + 32 + 3, integer3 + 12 + 11, 3158064);
      } else {
         this.mc.fontRendererObj.drawString(this.field_148291_b.getServerIpPort(), integer2 + 32 + 3, integer3 + 12 + 11, 3158064);
      }
   }

   @Override
   public boolean mousePressed(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      this.field_148292_c.selectServer(integer1);
      if (Minecraft.getSystemTime() - this.field_148290_d < 250L) {
         this.field_148292_c.connectToSelected();
      }

      this.field_148290_d = Minecraft.getSystemTime();
      return false;
   }

   @Override
   public void setSelected(int integer1, int integer2, int integer3) {
   }

   @Override
   public void mouseReleased(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
   }

   public LanServerDetector$LanServer getLanServer() {
      return this.field_148291_b;
   }
}
