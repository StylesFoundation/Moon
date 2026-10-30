package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import java.io.IOException;
import java.net.IDN;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

public class GuiScreenAddServer extends GuiScreen {
   private final GuiScreen parentScreen;
   private final ServerData serverData;
   private GuiTextField serverIPField;
   private GuiTextField serverNameField;
   private GuiButton serverResourcePacks;
   private final Predicate<String> field_181032_r = string -> {
      if (string.length() == 0) {
         return true;
      }

      String[] var1 = string.split(":");
      if (var1.length == 0) {
         return true;
      }

      try {
         IDN.toASCII(var1[0]);
         return true;
      } catch (IllegalArgumentException var2) {
         return false;
      }
   };

   public GuiScreenAddServer(GuiScreen guiScreen, ServerData serverData) {
      this.parentScreen = guiScreen;
      this.serverData = serverData;
   }

   @Override
   public void onUpdateScreen() {
      this.serverNameField.updateCursorCounter();
      this.serverIPField.updateCursorCounter();
   }

   @Override
   public void onInitGui() {
      Keyboard.enableRepeatEvents(true);
      this.m.clear();
      this.m.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, I18n.format("addServer.add")));
      this.m.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18, I18n.format("gui.cancel")));
      this.m
         .add(
            this.serverResourcePacks = new GuiButton(
               2,
               this.width / 2 - 100,
               this.height / 4 + 72,
               I18n.format("addServer.resourcePack") + ": " + this.serverData.getResourceMode().getMotd().getFormattedText()
            )
         );
      this.serverNameField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 66, 200, 20);
      this.serverNameField.setFocused(true);
      this.serverNameField.setText(this.serverData.serverName);
      this.serverIPField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, 106, 200, 20);
      this.serverIPField.setMaxStringLength(128);
      this.serverIPField.setText(this.serverData.serverIP);
      this.serverIPField.setValidator(this.field_181032_r);
      this.m.get(0).enabled = this.serverIPField.getText().length() > 0
         && this.serverIPField.getText().split(":").length > 0
         && this.serverNameField.getText().length() > 0;
   }

   @Override
   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         if (guiButton.l == 2) {
            this.serverData
               .setResourceMode(
                  ServerData.ServerResourceMode.values()[(this.serverData.getResourceMode().ordinal() + 1) % ServerData.ServerResourceMode.values().length]
               );
            this.serverResourcePacks.displayString = I18n.format("addServer.resourcePack")
               + ": "
               + this.serverData.getResourceMode().getMotd().getFormattedText();
         } else if (guiButton.l == 1) {
            this.parentScreen.confirmClicked(false, 0);
         } else if (guiButton.l == 0) {
            this.serverData.serverName = this.serverNameField.getText();
            this.serverData.serverIP = this.serverIPField.getText();
            this.parentScreen.confirmClicked(true, 0);
         }
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      this.serverNameField.textboxKeyTyped(charTyped, keyCode);
      this.serverIPField.textboxKeyTyped(charTyped, keyCode);
      if (keyCode == 15) {
         this.serverNameField.setFocused(!this.serverNameField.isFocused());
         this.serverIPField.setFocused(!this.serverIPField.isFocused());
      }

      if (keyCode == 28 || keyCode == 156) {
         this.actionPerformed(this.m.get(0));
      }

      this.m.get(0).enabled = this.serverIPField.getText().length() > 0
         && this.serverIPField.getText().split(":").length > 0
         && this.serverNameField.getText().length() > 0;
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      super.onMouseClicked(mouseX, mouseY, button);
      this.serverIPField.mouseClicked(mouseX, mouseY, button);
      this.serverNameField.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, I18n.format("addServer.title"), this.width / 2, 17, 16777215);
      this.drawString(this.fontRendererObj, I18n.format("addServer.enterName"), this.width / 2 - 100, 53, 10526880);
      this.drawString(this.fontRendererObj, I18n.format("addServer.enterIp"), this.width / 2 - 100, 94, 10526880);
      this.serverNameField.drawTextBox();
      this.serverIPField.drawTextBox();
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }
}
