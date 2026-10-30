package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.optifine.CustomLoadingScreen;
import net.optifine.CustomLoadingScreens;

public class GuiDownloadTerrain extends GuiScreen {
   private final NetHandlerPlayClient netHandlerPlayClient;
   private int progress;
   private final CustomLoadingScreen customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();

   public GuiDownloadTerrain(NetHandlerPlayClient netHandlerPlayClient) {
      this.netHandlerPlayClient = netHandlerPlayClient;
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
   }

   @Override
   public void onInitGui() {
      this.m.clear();
   }

   @Override
   public void onUpdateScreen() {
      this.progress++;
      if (this.progress % 20 == 0) {
         this.netHandlerPlayClient.Method754(new C00PacketKeepAlive());
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      if (this.customLoadingScreen != null) {
         this.customLoadingScreen.drawBackground(this.width, this.height);
      } else {
         this.drawBackground(0);
      }

      this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain"), this.width / 2, this.height / 2 - 50, 16777215);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   @Override
   public boolean doesGuiPauseGame() {
      return false;
   }
}
