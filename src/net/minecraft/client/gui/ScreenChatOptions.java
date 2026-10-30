package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class ScreenChatOptions extends GuiScreen {
   private static final GameSettings.Options[] field_146399_a = new GameSettings.Options[]{
      GameSettings.Options.CHAT_VISIBILITY,
      GameSettings.Options.CHAT_COLOR,
      GameSettings.Options.CHAT_LINKS,
      GameSettings.Options.CHAT_OPACITY,
      GameSettings.Options.CHAT_LINKS_PROMPT,
      GameSettings.Options.CHAT_SCALE,
      GameSettings.Options.CHAT_HEIGHT_FOCUSED,
      GameSettings.Options.CHAT_HEIGHT_UNFOCUSED,
      GameSettings.Options.CHAT_WIDTH,
      GameSettings.Options.REDUCED_DEBUG_INFO
   };
   private final GuiScreen parentScreen;
   private final GameSettings game_settings;
   private String field_146401_i;

   public ScreenChatOptions(GuiScreen guiScreen, GameSettings gameSettings) {
      this.parentScreen = guiScreen;
      this.game_settings = gameSettings;
   }

   @Override
   public void onInitGui() {
      int var3 = 0;
      this.field_146401_i = I18n.format("options.chat.title");

      for (GameSettings.Options var7 : field_146399_a) {
         if (var7.getEnumFloat()) {
            this.m.add(new GuiOptionSlider(var7.returnEnumOrdinal(), this.width / 2 - 155 + var3 % 2 * 160, this.height / 6 + 24 * (var3 >> 1), var7));
         } else {
            this.m
               .add(
                  new GuiOptionButton(
                     var7.returnEnumOrdinal(),
                     this.width / 2 - 155 + var3 % 2 * 160,
                     this.height / 6 + 24 * (var3 >> 1),
                     var7,
                     this.game_settings.getKeyBinding(var7)
                  )
               );
         }

         var3++;
      }

      this.m.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 120, I18n.format("gui.done")));
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         if (guiButton.l < 100 && guiButton instanceof GuiOptionButton) {
            this.game_settings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
            guiButton.displayString = this.game_settings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.l));
         }

         if (guiButton.l == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.parentScreen);
         }
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.field_146401_i, this.width / 2, 20, 16777215);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }
}
