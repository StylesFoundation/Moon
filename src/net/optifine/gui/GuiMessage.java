package net.optifine.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.src.Config;

public class GuiMessage extends GuiScreen {
   private final GuiScreen parentScreen;
   private final String messageLine1;
   private final String messageLine2;
   private final List<String> listLines2 = Lists.newArrayList();
   protected String confirmButtonText;
   private int ticksUntilEnable;

   public GuiMessage(GuiScreen guiScreen, String string2, String string3) {
      this.parentScreen = guiScreen;
      this.messageLine1 = string2;
      this.messageLine2 = string3;
      this.confirmButtonText = I18n.format("gui.done");
   }

   @Override
   public void onInitGui() {
      this.m.add(new GuiOptionButton(0, this.width / 2 - 74, this.height / 6 + 96, this.confirmButtonText));
      this.listLines2.clear();
      this.listLines2.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, this.width - 50));
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      Config.getMinecraft().displayGuiScreen(this.parentScreen);
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.messageLine1, this.width / 2, 70, 16777215);
      int var7 = 90;

      for (String var9 : this.listLines2) {
         this.drawCenteredString(this.fontRendererObj, var9, this.width / 2, var7, 16777215);
         var7 += this.fontRendererObj.FONT_HEIGHT;
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   public void setButtonDelay(int integer) {
      this.ticksUntilEnable = integer;
      Iterator var5 = this.m.iterator();

      while (var5.hasNext()) {
         ((GuiButton)var5.next()).enabled = false;
      }
   }

   @Override
   public void onUpdateScreen() {
      super.onUpdateScreen();
      if (--this.ticksUntilEnable == 0) {
         Iterator var4 = this.m.iterator();

         while (var4.hasNext()) {
            ((GuiButton)var4.next()).enabled = true;
         }
      }
   }
}
