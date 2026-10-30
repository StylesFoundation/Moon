package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

public class GuiScreenServerList extends GuiScreen {
   private final GuiScreen field_146303_a;
   private final ServerData field_146301_f;
   private GuiTextField field_146302_g;

   public GuiScreenServerList(GuiScreen guiScreen, ServerData serverData) {
      this.field_146303_a = guiScreen;
      this.field_146301_f = serverData;
   }

   @Override
   public void onUpdateScreen() {
      this.field_146302_g.updateCursorCounter();
   }

   @Override
   public void onInitGui() {
      Keyboard.enableRepeatEvents(true);
      this.m.clear();
      this.m.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("selectServer.select")));
      this.m.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel")));
      this.field_146302_g = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
      this.field_146302_g.setMaxStringLength(128);
      this.field_146302_g.setFocused(true);
      this.field_146302_g.setText(this.mc.gameSettings.lastServer);
      this.m.get(0).enabled = this.field_146302_g.getText().length() > 0 && this.field_146302_g.getText().split(":").length > 0;
   }

   @Override
   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      this.mc.gameSettings.lastServer = this.field_146302_g.getText();
      this.mc.gameSettings.saveOptions();
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         if (guiButton.l == 1) {
            this.field_146303_a.confirmClicked(false, 0);
         } else if (guiButton.l == 0) {
            this.field_146301_f.serverIP = this.field_146302_g.getText();
            this.field_146303_a.confirmClicked(true, 0);
         }
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (this.field_146302_g.textboxKeyTyped(charTyped, keyCode)) {
         this.m.get(0).enabled = this.field_146302_g.getText().length() > 0 && this.field_146302_g.getText().split(":").length > 0;
      } else if (keyCode == 28 || keyCode == 156) {
         this.actionPerformed(this.m.get(0));
      }
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      super.onMouseClicked(mouseX, mouseY, button);
      this.field_146302_g.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, I18n.format("selectServer.direct"), this.width / 2, 20, 16777215);
      this.drawString(this.fontRendererObj, I18n.format("addServer.enterIp"), this.width / 2 - 100, 100, 10526880);
      this.field_146302_g.drawTextBox();
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }
}
