package net.minecraft.client.gui;

import java.io.IOException;
import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

public class GuiGameOver extends GuiScreen implements GuiYesNoCallback {
   private int enableButtonsTimer;
   private final boolean x = false;

   @Override
   public void onInitGui() {
      this.m.clear();
      if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
         if (this.mc.isIntegratedServerRunning()) {
            this.m.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.deleteWorld")));
         } else {
            this.m.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.leaveServer")));
         }
      } else {
         this.m.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 72, I18n.format("deathScreen.respawn")));
         this.m.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.titleScreen")));
         if (this.mc.getSession() == null) {
            this.m.get(1).enabled = false;
         }
      }

      Iterator var3 = this.m.iterator();

      while (var3.hasNext()) {
         ((GuiButton)var3.next()).enabled = false;
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      switch (guiButton.l) {
         case 0:
            this.mc.thePlayer.respawnPlayer();
            this.mc.displayGuiScreen(null);
            break;
         case 1:
            if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
               this.mc.displayGuiScreen(new GuiMainMenu());
            } else {
               GuiYesNo var4 = new GuiYesNo(
                  this, I18n.format("deathScreen.quit.confirm"), "", I18n.format("deathScreen.titleScreen"), I18n.format("deathScreen.respawn"), 0
               );
               this.mc.displayGuiScreen(var4);
               var4.setButtonDelay(20);
            }
      }
   }

   @Override
   public void confirmClicked(boolean boolean1, int integer) {
      if (boolean1) {
         this.mc.theWorld.sendQuittingDisconnectingPacket();
         this.mc.loadWorld(null);
         this.mc.displayGuiScreen(new GuiMainMenu());
      } else {
         this.mc.thePlayer.respawnPlayer();
         this.mc.displayGuiScreen(null);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
      GlStateManager.pushMatrix();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      boolean var6 = this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
      String var7 = var6 ? I18n.format("deathScreen.title.hardcore") : I18n.format("deathScreen.title");
      this.drawCenteredString(this.fontRendererObj, var7, this.width / 2 / 2, 30, 16777215);
      GlStateManager.popMatrix();
      if (var6) {
         this.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.hardcoreInfo"), this.width / 2, 144, 16777215);
      }

      this.drawCenteredString(
         this.fontRendererObj,
         I18n.format("deathScreen.score") + ": " + EnumChatFormatting.YELLOW + this.mc.thePlayer.getScore(),
         this.width / 2,
         100,
         16777215
      );
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   @Override
   public boolean doesGuiPauseGame() {
      return false;
   }

   @Override
   public void onUpdateScreen() {
      super.onUpdateScreen();
      this.enableButtonsTimer++;
      if (this.enableButtonsTimer == 20) {
         Iterator var3 = this.m.iterator();

         while (var3.hasNext()) {
            ((GuiButton)var3.next()).enabled = true;
         }
      }
   }
}
