package net.optifine.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiPerformanceSettingsOF extends GuiScreen {
   private static final GameSettings.Options[] enumOptions = new GameSettings.Options[]{
      GameSettings.Options.SMOOTH_FPS,
      GameSettings.Options.SMOOTH_WORLD,
      GameSettings.Options.FAST_MATH,
      GameSettings.Options.CHUNK_UPDATES,
      GameSettings.Options.CHUNK_UPDATES_DYNAMIC,
      GameSettings.Options.RENDER_REGIONS,
      GameSettings.Options.LAZY_CHUNK_LOADING,
      GameSettings.Options.SMART_ANIMATIONS
   };
   private final GuiScreen prevScreen;
   private final GameSettings settings;
   private final TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());
   protected String title;

   public GuiPerformanceSettingsOF(GuiScreen guiScreen, GameSettings gameSettings) {
      this.prevScreen = guiScreen;
      this.settings = gameSettings;
   }

   @Override
   public void onInitGui() {
      this.title = I18n.format("of.options.performanceTitle");
      this.m.clear();

      for (int var4 = 0; var4 < enumOptions.length; var4++) {
         GameSettings.Options var5 = enumOptions[var4];
         int var6 = this.width / 2 - 155 + var4 % 2 * 160;
         int var7 = this.height / 6 + 21 * (var4 / 2) - 12;
         if (!var5.getEnumFloat()) {
            this.m.add(new GuiOptionButtonOF(var5.returnEnumOrdinal(), var6, var7, var5, this.settings.getKeyBinding(var5)));
         } else {
            this.m.add(new GuiOptionSliderOF(var5.returnEnumOrdinal(), var6, var7, var5));
         }
      }

      this.m.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done")));
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) {
      if (guiButton.enabled) {
         if (guiButton.l < 200 && guiButton instanceof GuiOptionButton) {
            this.settings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
            guiButton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.l));
         }

         if (guiButton.l == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.prevScreen);
         }
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
      this.tooltipManager.drawTooltips(mouseX, mouseY, this.m);
   }
}
