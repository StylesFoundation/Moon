package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

public class GuiRenameWorld extends GuiScreen {
   private final GuiScreen parentScreen;
   private GuiTextField field_146583_f;
   private final String saveName;

   public GuiRenameWorld(GuiScreen guiScreen, String string) {
      this.parentScreen = guiScreen;
      this.saveName = string;
   }

   @Override
   public void onUpdateScreen() {
      this.field_146583_f.updateCursorCounter();
   }

   @Override
   public void onInitGui() {
      Keyboard.enableRepeatEvents(true);
      this.m.clear();
      this.m.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("selectWorld.renameButton")));
      this.m.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel")));
      String var5 = this.mc.getSaveLoader().getWorldInfo(this.saveName).getWorldName();
      this.field_146583_f = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
      this.field_146583_f.setFocused(true);
      this.field_146583_f.setText(var5);
   }

   @Override
   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         if (guiButton.l == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
         } else if (guiButton.l == 0) {
            this.mc.getSaveLoader().renameWorld(this.saveName, this.field_146583_f.getText().trim());
            this.mc.displayGuiScreen(this.parentScreen);
         }
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      this.field_146583_f.textboxKeyTyped(charTyped, keyCode);
      this.m.get(0).enabled = this.field_146583_f.getText().trim().length() > 0;
      if (keyCode == 28 || keyCode == 156) {
         this.actionPerformed(this.m.get(0));
      }
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      super.onMouseClicked(mouseX, mouseY, button);
      this.field_146583_f.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.renameTitle"), this.width / 2, 20, 16777215);
      this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterName"), this.width / 2 - 100, 47, 10526880);
      this.field_146583_f.drawTextBox();
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }
}
