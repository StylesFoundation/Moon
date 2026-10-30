package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;

public class GuiDisconnected extends GuiScreen {
   private final String reason;
   private final IChatComponent message;
   private List<String> multilineMessage;
   private final GuiScreen parentScreen;
   private int field_175353_i;

   public GuiDisconnected(GuiScreen guiScreen, String string, IChatComponent iChatComponent) {
      this.parentScreen = guiScreen;
      this.reason = I18n.format(string);
      this.message = iChatComponent;
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
   }

   @Override
   public void onInitGui() {
      this.m.clear();
      this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
      this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
      this.m
         .add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu")));
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.l == 0) {
         this.mc.displayGuiScreen(this.parentScreen);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(
         this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810
      );
      int var6 = this.height / 2 - this.field_175353_i / 2;
      if (this.multilineMessage != null) {
         for (String var8 : this.multilineMessage) {
            this.drawCenteredString(this.fontRendererObj, var8, this.width / 2, var6, 16777215);
            var6 += this.fontRendererObj.FONT_HEIGHT;
         }
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }
}
