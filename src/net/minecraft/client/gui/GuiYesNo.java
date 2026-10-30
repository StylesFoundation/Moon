package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.resources.I18n;

public class GuiYesNo extends GuiScreen {
   protected GuiYesNoCallback parentScreen;
   protected String messageLine1;
   private final String messageLine2;
   private final List<String> field_175298_s = Lists.newArrayList();
   protected String confirmButtonText;
   protected String cancelButtonText;
   protected int parentButtonClickedId;
   private int ticksUntilEnable;

   public GuiYesNo(GuiYesNoCallback guiYesNoCallback, String string2, String string3, int integer) {
      this.parentScreen = guiYesNoCallback;
      this.messageLine1 = string2;
      this.messageLine2 = string3;
      this.parentButtonClickedId = integer;
      this.confirmButtonText = I18n.format("gui.yes");
      this.cancelButtonText = I18n.format("gui.no");
   }

   public GuiYesNo(GuiYesNoCallback guiYesNoCallback, String string2, String string3, String string4, String string5, int integer) {
      this.parentScreen = guiYesNoCallback;
      this.messageLine1 = string2;
      this.messageLine2 = string3;
      this.confirmButtonText = string4;
      this.cancelButtonText = string5;
      this.parentButtonClickedId = integer;
   }

   @Override
   public void onInitGui() {
      this.m.add(new GuiOptionButton(0, this.width / 2 - 155, this.height / 6 + 96, this.confirmButtonText));
      this.m.add(new GuiOptionButton(1, this.width / 2 - 155 + 160, this.height / 6 + 96, this.cancelButtonText));
      this.field_175298_s.clear();
      this.field_175298_s.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, this.width - 50));
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      this.parentScreen.confirmClicked(guiButton.l == 0, this.parentButtonClickedId);
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.messageLine1, this.width / 2, 70, 16777215);
      int var6 = 90;

      for (String var8 : this.field_175298_s) {
         this.drawCenteredString(this.fontRendererObj, var8, this.width / 2, var6, 16777215);
         var6 += this.fontRendererObj.FONT_HEIGHT;
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   public void setButtonDelay(int integer) {
      this.ticksUntilEnable = integer;
      Iterator var2 = this.m.iterator();

      while (var2.hasNext()) {
         ((GuiButton)var2.next()).enabled = false;
      }
   }

   @Override
   public void onUpdateScreen() {
      super.onUpdateScreen();
      if (--this.ticksUntilEnable == 0) {
         Iterator var1 = this.m.iterator();

         while (var1.hasNext()) {
            ((GuiButton)var1.next()).enabled = true;
         }
      }
   }
}
