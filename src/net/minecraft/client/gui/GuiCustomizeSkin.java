package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenCapeOF;

public class GuiCustomizeSkin extends GuiScreen {
   private final GuiScreen parentScreen;
   private String title;

   public GuiCustomizeSkin(GuiScreen guiScreen) {
      this.parentScreen = guiScreen;
   }

   @Override
   public void onInitGui() {
      int var3 = 0;
      this.title = I18n.format("options.skinCustomisation.title");

      for (EnumPlayerModelParts var7 : EnumPlayerModelParts.values()) {
         this.m
            .add(new GuiCustomizeSkin.ButtonPart(var7.getPartId(), this.width / 2 - 155 + var3 % 2 * 160, this.height / 6 + 24 * (var3 >> 1), 150, 20, var7));
         var3++;
      }

      if (var3 % 2 == 1) {
         var3++;
      }

      this.m.add(new GuiButtonOF(210, this.width / 2 - 100, this.height / 6 + 24 * (var3 >> 1), I18n.format("of.options.skinCustomisation.ofCape")));
      var3 += 2;
      this.m.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 24 * (var3 >> 1), I18n.format("gui.done")));
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         if (guiButton.l == 210) {
            this.mc.displayGuiScreen(new GuiScreenCapeOF(this));
         }

         if (guiButton.l == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.parentScreen);
         } else if (guiButton instanceof GuiCustomizeSkin.ButtonPart) {
            EnumPlayerModelParts var4 = ((GuiCustomizeSkin.ButtonPart)guiButton).playerModelParts;
            this.mc.gameSettings.switchModelPartEnabled(var4);
            guiButton.displayString = this.func_175358_a(var4);
         }
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   private String func_175358_a(EnumPlayerModelParts enumPlayerModelParts) {
      String var2;
      if (this.mc.gameSettings.getModelParts().contains(enumPlayerModelParts)) {
         var2 = I18n.format("options.on");
      } else {
         var2 = I18n.format("options.off");
      }

      return enumPlayerModelParts.func_179326_d().getFormattedText() + ": " + var2;
   }

   class ButtonPart extends GuiButton {
      private final EnumPlayerModelParts playerModelParts;

      private ButtonPart(int integer2, int integer3, int integer4, int integer5, int integer6, EnumPlayerModelParts enumPlayerModelParts) {
         super(integer2, integer3, integer4, integer5, integer6, GuiCustomizeSkin.this.func_175358_a(enumPlayerModelParts));
         this.playerModelParts = enumPlayerModelParts;
      }
   }
}
