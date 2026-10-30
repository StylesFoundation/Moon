package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;

public class GuiLanguage extends GuiScreen {
   protected GuiScreen parentScreen;
   private GuiLanguage.List list;
   private final GameSettings game_settings_3;
   private final LanguageManager languageManager;
   private GuiOptionButton forceUnicodeFontBtn;
   private GuiOptionButton confirmSettingsBtn;

   public GuiLanguage(GuiScreen guiScreen, GameSettings gameSettings, LanguageManager languageManager) {
      this.parentScreen = guiScreen;
      this.game_settings_3 = gameSettings;
      this.languageManager = languageManager;
   }

   @Override
   public void onInitGui() {
      this.m
         .add(
            this.forceUnicodeFontBtn = new GuiOptionButton(
               100,
               this.width / 2 - 155,
               this.height - 38,
               GameSettings.Options.FORCE_UNICODE_FONT,
               this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT)
            )
         );
      this.m.add(this.confirmSettingsBtn = new GuiOptionButton(6, this.width / 2 - 155 + 160, this.height - 38, I18n.format("gui.done")));
      this.list = new GuiLanguage.List(this.mc);
      this.list.registerScrollButtons(7, 8);
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.list.handleMouseInput();
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         switch (guiButton.l) {
            case 5:
               break;
            case 6:
               this.mc.displayGuiScreen(this.parentScreen);
               break;
            case 100:
               if (guiButton instanceof GuiOptionButton) {
                  this.game_settings_3.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
                  guiButton.displayString = this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
                  ScaledResolution var2 = new ScaledResolution(this.mc);
                  int var3 = var2.Method237();
                  int var4 = var2.Method238();
                  this.setWorldAndResolution(this.mc, var3, var4);
               }
               break;
            default:
               this.list.actionPerformed(guiButton);
         }
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.list.drawScreen(mouseX, mouseY, partialTicks);
      this.drawCenteredString(this.fontRendererObj, I18n.format("options.language"), this.width / 2, 16, 16777215);
      this.drawCenteredString(this.fontRendererObj, "(" + I18n.format("options.languageWarning") + ")", this.width / 2, this.height - 56, 8421504);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   class List extends GuiSlot {
      private final java.util.List<String> langCodeList = Lists.newArrayList();
      private final Map<String, Language> languageMap = Maps.newHashMap();

      public List(Minecraft minecraft) {
         super(minecraft, GuiLanguage.this.width, GuiLanguage.this.height, 32, GuiLanguage.this.height - 65 + 4, 18);

         for (Language var6 : GuiLanguage.this.languageManager.getLanguages()) {
            this.languageMap.put(var6.getLanguageCode(), var6);
            this.langCodeList.add(var6.getLanguageCode());
         }
      }

      @Override
      protected int getSize() {
         return this.langCodeList.size();
      }

      @Override
      protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
         Language var5 = this.languageMap.get(this.langCodeList.get(index));
         GuiLanguage.this.languageManager.setCurrentLanguage(var5);
         GuiLanguage.this.game_settings_3.language = var5.getLanguageCode();
         this.mc.refreshResources();
         GuiLanguage.this.fontRendererObj
            .setUnicodeFlag(GuiLanguage.this.languageManager.isCurrentLocaleUnicode() || GuiLanguage.this.game_settings_3.forceUnicodeFont);
         GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.languageManager.isCurrentLanguageBidirectional());
         GuiLanguage.this.confirmSettingsBtn.displayString = I18n.format("gui.done");
         GuiLanguage.this.forceUnicodeFontBtn.displayString = GuiLanguage.this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
         GuiLanguage.this.game_settings_3.saveOptions();
      }

      @Override
      protected boolean isSelected(int index) {
         return this.langCodeList.get(index).equals(GuiLanguage.this.languageManager.getCurrentLanguage().getLanguageCode());
      }

      @Override
      protected int getContentHeight() {
         return this.getSize() * 18;
      }

      @Override
      protected void drawBackground() {
         GuiLanguage.this.drawDefaultBackground();
      }

      @Override
      protected void drawSlot(int index, int left, int top, int right, int bottom, int slotHeight) {
         GuiLanguage.this.fontRendererObj.setBidiFlag(true);
         GuiLanguage.this.drawCenteredString(
            GuiLanguage.this.fontRendererObj, this.languageMap.get(this.langCodeList.get(index)).toString(), this.width / 2, top + 1, 16777215
         );
         GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.languageManager.getCurrentLanguage().isBidirectional());
      }
   }
}
