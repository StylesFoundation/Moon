package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;

public class GuiErrorScreen extends GuiScreen {
   private final String field_146313_a;
   private final String field_146312_f;

   public GuiErrorScreen(String string1, String string2) {
      this.field_146313_a = string1;
      this.field_146312_f = string2;
   }

   @Override
   public void onInitGui() {
      super.onInitGui();
      this.m.add(new GuiButton(0, this.width / 2 - 100, 140, I18n.format("gui.cancel")));
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawGradientRect(0, 0, this.width, this.height, -12574688, -11530224);
      this.drawCenteredString(this.fontRendererObj, this.field_146313_a, this.width / 2, 90, 16777215);
      this.drawCenteredString(this.fontRendererObj, this.field_146312_f, this.width / 2, 110, 16777215);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      this.mc.displayGuiScreen(null);
   }
}
