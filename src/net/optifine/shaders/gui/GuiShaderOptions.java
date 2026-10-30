package net.optifine.shaders.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import net.minecraft.util.MathHelper;
import net.optifine.Lang;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProviderShaderOptions;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderOptionProfile;
import net.optifine.shaders.config.ShaderOptionScreen;

public class GuiShaderOptions extends GuiScreenOF {
   private final GuiScreen prevScreen;
   protected String title;
   private final GameSettings settings;
   private final TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderShaderOptions());
   private String screenName = null;
   private String screenText = null;
   private boolean changed = false;

   public GuiShaderOptions(GuiScreen guiScreen, GameSettings gameSettings) {
      this.title = "Shader Options";
      this.prevScreen = guiScreen;
      this.settings = gameSettings;
   }

   public GuiShaderOptions(GuiScreen guiScreen, GameSettings gameSettings, String string) {
      this(guiScreen, gameSettings);
      this.screenName = string;
      if (string != null) {
         this.screenText = Shaders.translate("screen." + string, string);
      }
   }

   @Override
   public void onInitGui() {
      this.title = I18n.format("of.options.shaderOptionsTitle");
      byte var4 = 100;
      byte var6 = 30;
      byte var7 = 20;
      byte var8 = 120;
      byte var9 = 20;
      int var10 = Shaders.getShaderPackColumns(this.screenName, 2);
      ShaderOption[] var11 = Shaders.getShaderPackOptions(this.screenName);
      if (var11 != null) {
         int var12 = MathHelper.ceiling_double_int(var11.length / 9.0);
         if (var10 < var12) {
            var10 = var12;
         }

         for (int var13 = 0; var13 < var11.length; var13++) {
            ShaderOption var14 = var11[var13];
            if (var14 != null && var14.isVisible()) {
               int var15 = var13 % var10;
               int var16 = var13 / var10;
               int var17 = Math.min(this.width / var10, 200);
               int var5 = (this.width - var17 * var10) / 2;
               int var18 = var15 * var17 + 5 + var5;
               int var19 = var6 + var16 * var7;
               int var20 = var17 - 10;
               String var21 = getButtonText(var14, var20);
               GuiButtonShaderOption var22;
               if (Shaders.isShaderPackOptionSlider(var14.getName())) {
                  var22 = new GuiSliderShaderOption(var4 + var13, var18, var19, var20, var9, var14, var21);
               } else {
                  var22 = new GuiButtonShaderOption(var4 + var13, var18, var19, var20, var9, var14, var21);
               }

               var22.enabled = var14.isEnabled();
               this.m.add(var22);
            }
         }
      }

      this.m.add(new GuiButton(201, this.width / 2 - var8 - 20, this.height / 6 + 168 + 11, var8, var9, I18n.format("controls.reset")));
      this.m.add(new GuiButton(200, this.width / 2 + 20, this.height / 6 + 168 + 11, var8, var9, I18n.format("gui.done")));
   }

   public static String getButtonText(ShaderOption shaderOption, int integer) {
      String var5 = shaderOption.getNameText();
      if (shaderOption instanceof ShaderOptionScreen) {
         return var5 + "...";
      }

      FontRenderer var7 = Config.getMinecraft().fontRendererObj;
      int var8 = var7.getStringWidth(": " + Lang.getOff()) + 5;

      while (var7.getStringWidth(var5) + var8 >= integer && var5.length() > 0) {
         var5 = var5.substring(0, var5.length() - 1);
      }

      String var12 = shaderOption.isChanged() ? shaderOption.getValueColor(shaderOption.getValue()) : "";
      String var9 = shaderOption.getValueText(shaderOption.getValue());
      String var10 = var9;
      String var11 = var12;
      return var5 + ": " + var11 + var10;
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) {
      if (guiButton.enabled) {
         if (guiButton.l < 200 && guiButton instanceof GuiButtonShaderOption var5) {
            ShaderOption var6 = var5.getShaderOption();
            if (var6 instanceof ShaderOptionScreen) {
               String var11 = var6.getName();
               GuiShaderOptions var12 = new GuiShaderOptions(this, this.settings, var11);
               this.mc.displayGuiScreen(var12);
               return;
            }

            if (isShiftKeyDown()) {
               var6.resetValue();
            } else if (var5.isSwitchable()) {
               var6.nextValue();
            }

            this.updateAllButtons();
            this.changed = true;
         }

         if (guiButton.l == 201) {
            for (ShaderOption var9 : Shaders.getChangedOptions(Shaders.getShaderPackOptions())) {
               var9.resetValue();
               this.changed = true;
            }

            this.updateAllButtons();
         }

         if (guiButton.l == 200) {
            if (this.changed) {
               Shaders.saveShaderPackOptions();
               this.changed = false;
               Shaders.uninit();
            }

            this.mc.displayGuiScreen(this.prevScreen);
         }
      }
   }

   @Override
   protected void actionPerformedRightClick(GuiButton guiButton) {
      if (guiButton instanceof GuiButtonShaderOption var5) {
         ShaderOption var6 = var5.getShaderOption();
         if (isShiftKeyDown()) {
            var6.resetValue();
         } else if (var5.isSwitchable()) {
            var6.prevValue();
         }

         this.updateAllButtons();
         this.changed = true;
      }
   }

   @Override
   public void onGuiClosed() {
      super.onGuiClosed();
      if (this.changed) {
         Shaders.saveShaderPackOptions();
         this.changed = false;
         Shaders.uninit();
      }
   }

   private void updateAllButtons() {
      for (GuiButton var5 : this.m) {
         if (var5 instanceof GuiButtonShaderOption var6) {
            ShaderOption var7 = var6.getShaderOption();
            if (var7 instanceof ShaderOptionProfile) {
               ((ShaderOptionProfile)var7).updateProfile();
            }

            var6.displayString = getButtonText(var7, var6.getButtonWidth());
            var6.valueChanged();
         }
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      if (this.screenText != null) {
         this.drawCenteredString(this.fontRendererObj, this.screenText, this.width / 2, 15, 16777215);
      } else {
         this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
      this.tooltipManager.drawTooltips(mouseX, mouseY, this.m);
   }
}
