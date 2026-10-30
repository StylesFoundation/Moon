package net.optifine.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiOtherSettingsOF extends GuiScreen implements GuiYesNoCallback {
   private final GuiScreen prevScreen;
   protected String title;
   private final GameSettings settings;
   private static final GameSettings.Options[] enumOptions = new GameSettings.Options[]{
      GameSettings.Options.LAGOMETER,
      GameSettings.Options.PROFILER,
      GameSettings.Options.SHOW_FPS,
      GameSettings.Options.ADVANCED_TOOLTIPS,
      GameSettings.Options.WEATHER,
      GameSettings.Options.TIME,
      GameSettings.Options.USE_FULLSCREEN,
      GameSettings.Options.FULLSCREEN_MODE,
      GameSettings.Options.ANAGLYPH,
      GameSettings.Options.AUTOSAVE_TICKS,
      GameSettings.Options.SCREENSHOT_SIZE,
      GameSettings.Options.SHOW_GL_ERRORS
   };
   private final TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());

   public GuiOtherSettingsOF(GuiScreen guiScreen, GameSettings gameSettings) {
      this.prevScreen = guiScreen;
      this.settings = gameSettings;
   }

   @Override
   public void onInitGui() {
      this.title = I18n.format("of.options.otherTitle");
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

      this.m.add(new GuiButton(210, this.width / 2 - 100, this.height / 6 + 168 + 11 - 44, I18n.format("of.options.other.reset")));
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

         if (guiButton.l == 210) {
            this.mc.gameSettings.saveOptions();
            GuiYesNo var5 = new GuiYesNo(this, I18n.format("of.message.other.reset"), "", 9999);
            this.mc.displayGuiScreen(var5);
         }
      }
   }

   @Override
   public void confirmClicked(boolean boolean1, int integer) {
      if (boolean1) {
         this.mc.gameSettings.resetSettings();
      }

      this.mc.displayGuiScreen(this);
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
      this.tooltipManager.drawTooltips(mouseX, mouseY, this.m);
   }
}
