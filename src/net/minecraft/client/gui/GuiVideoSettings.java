package net.minecraft.client.gui;

import com.moon.Moon;
import com.moon.events.impl.render.ScaleEvent;
import java.io.IOException;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import net.optifine.Lang;
import net.optifine.gui.GuiAnimationSettingsOF;
import net.optifine.gui.GuiDetailSettingsOF;
import net.optifine.gui.GuiOptionButtonOF;
import net.optifine.gui.GuiOptionSliderOF;
import net.optifine.gui.GuiOtherSettingsOF;
import net.optifine.gui.GuiPerformanceSettingsOF;
import net.optifine.gui.GuiQualitySettingsOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProviderOptions;
import net.optifine.shaders.gui.GuiShaders;

public class GuiVideoSettings extends GuiScreenOF {
   private final GuiScreen parentGuiScreen;
   protected String screenTitle = "Video Settings";
   private final GameSettings guiGameSettings;
   private static final GameSettings.Options[] videoOptions = new GameSettings.Options[]{
      GameSettings.Options.GRAPHICS,
      GameSettings.Options.RENDER_DISTANCE,
      GameSettings.Options.AMBIENT_OCCLUSION,
      GameSettings.Options.FRAMERATE_LIMIT,
      GameSettings.Options.AO_LEVEL,
      GameSettings.Options.VIEW_BOBBING,
      GameSettings.Options.GUI_SCALE,
      GameSettings.Options.USE_VBO,
      GameSettings.Options.GAMMA,
      GameSettings.Options.BLOCK_ALTERNATIVES,
      GameSettings.Options.DYNAMIC_LIGHTS,
      GameSettings.Options.DYNAMIC_FOV
   };
   private final TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());

   public GuiVideoSettings(GuiScreen guiScreen, GameSettings gameSettings) {
      this.parentGuiScreen = guiScreen;
      this.guiGameSettings = gameSettings;
   }

   @Override
   public void onInitGui() {
      this.screenTitle = I18n.format("options.videoTitle");
      this.m.clear();

      for (int var3 = 0; var3 < videoOptions.length; var3++) {
         GameSettings.Options var4 = videoOptions[var3];
         if (var4 != null) {
            int var5 = this.width / 2 - 155 + var3 % 2 * 160;
            int var6 = this.height / 6 + 21 * (var3 / 2) - 12;
            if (var4.getEnumFloat()) {
               this.m.add(new GuiOptionSliderOF(var4.returnEnumOrdinal(), var5, var6, var4));
            } else {
               this.m.add(new GuiOptionButtonOF(var4.returnEnumOrdinal(), var5, var6, var4, this.guiGameSettings.getKeyBinding(var4)));
            }
         }
      }

      int var7 = this.height / 6 + 21 * (videoOptions.length / 2) - 12;
      int var10 = this.width / 2 - 155;
      this.m.add(new GuiOptionButton(231, var10, var7, Lang.get("of.options.shaders")));
      var10 = this.width / 2 - 155 + 160;
      this.m.add(new GuiOptionButton(202, var10, var7, Lang.get("of.options.quality")));
      var7 += 21;
      var10 = this.width / 2 - 155;
      this.m.add(new GuiOptionButton(201, var10, var7, Lang.get("of.options.details")));
      var10 = this.width / 2 - 155 + 160;
      this.m.add(new GuiOptionButton(212, var10, var7, Lang.get("of.options.performance")));
      var7 += 21;
      var10 = this.width / 2 - 155;
      this.m.add(new GuiOptionButton(211, var10, var7, Lang.get("of.options.animations")));
      var10 = this.width / 2 - 155 + 160;
      this.m.add(new GuiOptionButton(222, var10, var7, Lang.get("of.options.other")));
      int var10000 = var7 + 21;
      this.m.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done")));
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      this.actionPerformed(guiButton, 1);
   }

   @Override
   protected void actionPerformedRightClick(GuiButton guiButton) {
      if (guiButton.l == GameSettings.Options.GUI_SCALE.ordinal()) {
         this.actionPerformed(guiButton, -1);
      }
   }

   private void actionPerformed(GuiButton guiButton, int integer) {
      if (guiButton.enabled) {
         int var5 = this.guiGameSettings.guiScale;
         if (guiButton.l < 200 && guiButton instanceof GuiOptionButton) {
            this.guiGameSettings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), integer);
            guiButton.displayString = this.guiGameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.l));
         }

         if (guiButton.l == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.parentGuiScreen);
         }

         if (this.guiGameSettings.guiScale != var5) {
            ScaledResolution var6 = new ScaledResolution(this.mc);
            int var7 = var6.Method237();
            int var8 = var6.Method238();
            Moon.getInstance().getEventBus().publish(new ScaleEvent());
            this.setWorldAndResolution(this.mc, var7, var8);
         }

         if (guiButton.l == 201) {
            this.mc.gameSettings.saveOptions();
            GuiDetailSettingsOF var9 = new GuiDetailSettingsOF(this, this.guiGameSettings);
            this.mc.displayGuiScreen(var9);
         }

         if (guiButton.l == 202) {
            this.mc.gameSettings.saveOptions();
            GuiQualitySettingsOF var10 = new GuiQualitySettingsOF(this, this.guiGameSettings);
            this.mc.displayGuiScreen(var10);
         }

         if (guiButton.l == 211) {
            this.mc.gameSettings.saveOptions();
            GuiAnimationSettingsOF var11 = new GuiAnimationSettingsOF(this, this.guiGameSettings);
            this.mc.displayGuiScreen(var11);
         }

         if (guiButton.l == 212) {
            this.mc.gameSettings.saveOptions();
            GuiPerformanceSettingsOF var12 = new GuiPerformanceSettingsOF(this, this.guiGameSettings);
            this.mc.displayGuiScreen(var12);
         }

         if (guiButton.l == 222) {
            this.mc.gameSettings.saveOptions();
            GuiOtherSettingsOF var13 = new GuiOtherSettingsOF(this, this.guiGameSettings);
            this.mc.displayGuiScreen(var13);
         }

         if (guiButton.l == 231) {
            if (Config.isAntialiasing() || Config.isAntialiasingConfigured()) {
               Config.showGuiMessage(Lang.get("of.message.shaders.aa1"), Lang.get("of.message.shaders.aa2"));
               return;
            }

            if (Config.isAnisotropicFiltering()) {
               Config.showGuiMessage(Lang.get("of.message.shaders.af1"), Lang.get("of.message.shaders.af2"));
               return;
            }

            if (Config.getGameSettings().anaglyph) {
               Config.showGuiMessage(Lang.get("of.message.shaders.an1"), Lang.get("of.message.shaders.an2"));
               return;
            }

            this.mc.gameSettings.saveOptions();
            GuiShaders var14 = new GuiShaders(this, this.guiGameSettings);
            this.mc.displayGuiScreen(var14);
         }
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 15, 16777215);
      String var6 = Config.getVersion();
      if ("HD_U".equals("HD")) {
         var6 = "OptiFine HD M5";
      }

      if ("HD_U".equals("HD_U")) {
         var6 = "OptiFine HD M5 Ultra";
      }

      if ("HD_U".equals("L")) {
         var6 = "OptiFine M5 Light";
      }

      this.drawString(this.fontRendererObj, var6, 2, this.height - 10, 8421504);
      int var9 = this.fontRendererObj.getStringWidth("Moon Client");
      this.drawString(this.fontRendererObj, "Moon Client", this.width - var9 - 2, this.height - 10, 8421504);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
      this.tooltipManager.drawTooltips(mouseX, mouseY, this.m);
   }

   public static int getButtonWidth(GuiButton guiButton) {
      return guiButton.width;
   }

   public static int getButtonHeight(GuiButton guiButton) {
      return guiButton.h;
   }

   public static void drawGradientRect(GuiScreen guiScreen, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7) {
      guiScreen.drawGradientRect(integer2, integer3, integer4, integer5, integer6, integer7);
   }

   public static String getGuiChatText(GuiChat guiChat) {
      return guiChat.inputField.getText();
   }
}
