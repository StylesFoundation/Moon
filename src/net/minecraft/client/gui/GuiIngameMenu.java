package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;

public class GuiIngameMenu extends GuiScreen {
   private int field_146445_a;
   private int field_146444_f;

   @Override
   public void onInitGui() {
      this.field_146445_a = 0;
      this.m.clear();
      byte var3 = -16;
      this.m.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + var3, I18n.format("menu.returnToMenu")));
      if (!this.mc.isIntegratedServerRunning()) {
         this.m.get(0).displayString = I18n.format("menu.disconnect");
      }

      this.m.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + var3, I18n.format("menu.returnToGame")));
      this.m.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + var3, 98, 20, I18n.format("menu.options")));
      GuiButton var5;
      this.m.add(var5 = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + var3, 98, 20, I18n.format("menu.shareToLan")));
      this.m.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + var3, 98, 20, I18n.format("gui.achievements")));
      this.m.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + var3, 98, 20, I18n.format("gui.stats")));
      var5.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      switch (guiButton.l) {
         case 0:
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
            break;
         case 1:
            boolean var2 = this.mc.isIntegratedServerRunning();
            this.mc.isConnectedToRealms();
            guiButton.enabled = false;
            this.mc.theWorld.sendQuittingDisconnectingPacket();
            this.mc.loadWorld(null);
            if (var2) {
               this.mc.displayGuiScreen(new GuiMainMenu());
            } else {
               this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
            }
         case 2:
         case 3:
         default:
            break;
         case 4:
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
            break;
         case 5:
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
            break;
         case 6:
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
            break;
         case 7:
            this.mc.displayGuiScreen(new GuiShareToLan(this));
      }
   }

   @Override
   public void onUpdateScreen() {
      super.onUpdateScreen();
      this.field_146444_f++;
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game"), this.width / 2, 40, 16777215);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }
}
