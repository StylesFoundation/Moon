package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;

public class GuiOptions extends GuiScreen implements GuiYesNoCallback {
   private static final GameSettings.Options[] field_146440_f = new GameSettings.Options[]{GameSettings.Options.FOV};
   private final GuiScreen field_146441_g;
   private final GameSettings game_settings_1;
   private GuiButton field_175357_i;
   private GuiLockIconButton field_175356_r;
   protected String field_146442_a = "Options";

   public GuiOptions(GuiScreen guiScreen, GameSettings gameSettings) {
      this.field_146441_g = guiScreen;
      this.game_settings_1 = gameSettings;
   }

   @Override
   public void onInitGui() {
      int var3 = 0;
      this.field_146442_a = I18n.format("options.title");

      for (GameSettings.Options var7 : field_146440_f) {
         if (var7.getEnumFloat()) {
            this.m.add(new GuiOptionSlider(var7.returnEnumOrdinal(), this.width / 2 - 155 + var3 % 2 * 160, this.height / 6 - 12 + 24 * (var3 >> 1), var7));
         } else {
            GuiOptionButton var8 = new GuiOptionButton(
               var7.returnEnumOrdinal(),
               this.width / 2 - 155 + var3 % 2 * 160,
               this.height / 6 - 12 + 24 * (var3 >> 1),
               var7,
               this.game_settings_1.getKeyBinding(var7)
            );
            this.m.add(var8);
         }

         var3++;
      }

      if (this.mc.theWorld != null) {
         EnumDifficulty var9 = this.mc.theWorld.getDifficulty();
         this.field_175357_i = new GuiButton(
            108, this.width / 2 - 155 + var3 % 2 * 160, this.height / 6 - 12 + 24 * (var3 >> 1), 150, 20, this.func_175355_a(var9)
         );
         this.m.add(this.field_175357_i);
         if (this.mc.isSingleplayer() && !this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
            this.field_175357_i.setWidth(this.field_175357_i.getButtonWidth() - 20);
            this.field_175356_r = new GuiLockIconButton(
               109, this.field_175357_i.xPosition + this.field_175357_i.getButtonWidth(), this.field_175357_i.yPosition
            );
            this.m.add(this.field_175356_r);
            this.field_175356_r.func_175229_b(this.mc.theWorld.getWorldInfo().isDifficultyLocked());
            this.field_175356_r.enabled = !this.field_175356_r.func_175230_c();
            this.field_175357_i.enabled = !this.field_175356_r.func_175230_c();
         } else {
            this.field_175357_i.enabled = false;
         }
      } else {
         GuiOptionButton var10 = new GuiOptionButton(
            GameSettings.Options.REALMS_NOTIFICATIONS.returnEnumOrdinal(),
            this.width / 2 - 155 + var3 % 2 * 160,
            this.height / 6 - 12 + 24 * (var3 >> 1),
            GameSettings.Options.REALMS_NOTIFICATIONS,
            this.game_settings_1.getKeyBinding(GameSettings.Options.REALMS_NOTIFICATIONS)
         );
         this.m.add(var10);
      }

      this.m.add(new GuiButton(110, this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, I18n.format("options.skinCustomisation")));
      this.m
         .add(
            new GuiButton(8675309, this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20, "Super Secret Settings...") {
               @Override
               public void playPressSound(SoundHandler soundHandler) {
                  SoundEventAccessorComposite var2 = soundHandler.getRandomSoundFromCategories(
                     SoundCategory.ANIMALS, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.PLAYERS, SoundCategory.WEATHER
                  );
                  if (var2 != null) {
                     soundHandler.playSound(PositionedSoundRecord.create(var2.getSoundEventLocation(), 0.5F));
                  }
               }
            }
         );
      this.m.add(new GuiButton(106, this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20, I18n.format("options.sounds")));
      this.m.add(new GuiButton(107, this.width / 2 + 5, this.height / 6 + 72 - 6, 150, 20, I18n.format("options.stream")));
      this.m.add(new GuiButton(101, this.width / 2 - 155, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.video")));
      this.m.add(new GuiButton(100, this.width / 2 + 5, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.controls")));
      this.m.add(new GuiButton(102, this.width / 2 - 155, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.language")));
      this.m.add(new GuiButton(103, this.width / 2 + 5, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.chat.title")));
      this.m.add(new GuiButton(105, this.width / 2 - 155, this.height / 6 + 144 - 6, 150, 20, I18n.format("options.resourcepack")));
      this.m.add(new GuiButton(104, this.width / 2 + 5, this.height / 6 + 144 - 6, 150, 20, I18n.format("options.snooper.view")));
      this.m.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done")));
   }

   public String func_175355_a(EnumDifficulty enumDifficulty) {
      ChatComponentText var2 = new ChatComponentText("");
      var2.appendSibling(new ChatComponentTranslation("options.difficulty"));
      var2.appendText(": ");
      var2.appendSibling(new ChatComponentTranslation(enumDifficulty.getDifficultyResourceKey()));
      return var2.getFormattedText();
   }

   @Override
   public void confirmClicked(boolean boolean1, int integer) {
      this.mc.displayGuiScreen(this);
      if (integer == 109 && boolean1 && this.mc.theWorld != null) {
         this.mc.theWorld.getWorldInfo().setDifficultyLocked(true);
         this.field_175356_r.func_175229_b(true);
         this.field_175356_r.enabled = false;
         this.field_175357_i.enabled = false;
      }
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         if (guiButton.l < 100 && guiButton instanceof GuiOptionButton) {
            GameSettings.Options var4 = ((GuiOptionButton)guiButton).returnEnumOptions();
            this.game_settings_1.setOptionValue(var4, 1);
            guiButton.displayString = this.game_settings_1.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.l));
         }

         if (guiButton.l == 108) {
            this.mc.theWorld.getWorldInfo().setDifficulty(EnumDifficulty.getDifficultyEnum(this.mc.theWorld.getDifficulty().getDifficultyId() + 1));
            this.field_175357_i.displayString = this.func_175355_a(this.mc.theWorld.getDifficulty());
         }

         if (guiButton.l == 109) {
            this.mc
               .displayGuiScreen(
                  new GuiYesNo(
                     this,
                     new ChatComponentTranslation("difficulty.lock.title").getFormattedText(),
                     new ChatComponentTranslation(
                           "difficulty.lock.question", new ChatComponentTranslation(this.mc.theWorld.getWorldInfo().getDifficulty().getDifficultyResourceKey())
                        )
                        .getFormattedText(),
                     109
                  )
               );
         }

         if (guiButton.l == 110) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new GuiCustomizeSkin(this));
         }

         if (guiButton.l == 8675309) {
            this.mc.entityRenderer.activateNextShader();
         }

         if (guiButton.l == 101) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new GuiVideoSettings(this, this.game_settings_1));
         }

         if (guiButton.l == 100) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new GuiControls(this, this.game_settings_1));
         }

         if (guiButton.l == 102) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new GuiLanguage(this, this.game_settings_1, this.mc.getLanguageManager()));
         }

         if (guiButton.l == 103) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new ScreenChatOptions(this, this.game_settings_1));
         }

         if (guiButton.l == 104) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new GuiSnooper(this, this.game_settings_1));
         }

         if (guiButton.l == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.field_146441_g);
         }

         if (guiButton.l == 105) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
         }

         if (guiButton.l == 106) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.game_settings_1));
         }
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.field_146442_a, this.width / 2, 15, 16777215);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }
}
