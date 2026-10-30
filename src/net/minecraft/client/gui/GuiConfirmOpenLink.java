package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;

public class GuiConfirmOpenLink extends GuiYesNo {
   private final String openLinkWarning;
   private final String copyLinkButtonText;
   private final String linkText;
   private boolean showSecurityWarning = true;

   public GuiConfirmOpenLink(GuiYesNoCallback guiYesNoCallback, String string, int integer, boolean boolean4) {
      super(guiYesNoCallback, I18n.format(boolean4 ? "chat.link.confirmTrusted" : "chat.link.confirm"), string, integer);
      this.confirmButtonText = I18n.format(boolean4 ? "chat.link.open" : "gui.yes");
      this.cancelButtonText = I18n.format(boolean4 ? "gui.cancel" : "gui.no");
      this.copyLinkButtonText = I18n.format("chat.copy");
      this.openLinkWarning = I18n.format("chat.link.warning");
      this.linkText = string;
   }

   @Override
   public void onInitGui() {
      super.onInitGui();
      this.m.clear();
      this.m.add(new GuiButton(0, this.width / 2 - 50 - 105, this.height / 6 + 96, 100, 20, this.confirmButtonText));
      this.m.add(new GuiButton(2, this.width / 2 - 50, this.height / 6 + 96, 100, 20, this.copyLinkButtonText));
      this.m.add(new GuiButton(1, this.width / 2 - 50 + 105, this.height / 6 + 96, 100, 20, this.cancelButtonText));
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.l == 2) {
         this.copyLinkToClipboard();
      }

      this.parentScreen.confirmClicked(guiButton.l == 0, this.parentButtonClickedId);
   }

   public void copyLinkToClipboard() {
      setClipboardString(this.linkText);
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      super.onDrawScreen(mouseX, mouseY, partialTicks);
      if (this.showSecurityWarning) {
         this.drawCenteredString(this.fontRendererObj, this.openLinkWarning, this.width / 2, 110, 16764108);
      }
   }

   public void disableSecurityWarning() {
      this.showSecurityWarning = false;
   }
}
