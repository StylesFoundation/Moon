package net.minecraft.client.gui;

import java.io.IOException;
import java.util.Random;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

public class GuiCreateWorld extends GuiScreen {
   private final GuiScreen parentScreen;
   private GuiTextField worldNameField;
   private GuiTextField worldSeedField;
   private String saveDirName;
   private String gameMode = "survival";
   private String savedGameMode;
   private boolean generateStructuresEnabled = true;
   private boolean allowCheats;
   private boolean allowCheatsWasSetByUser;
   private boolean bonusChestEnabled;
   private boolean hardCoreMode;
   private boolean alreadyGenerated;
   private boolean inMoreWorldOptionsDisplay;
   private GuiButton btnGameMode;
   private GuiButton btnMoreOptions;
   private GuiButton btnMapFeatures;
   private GuiButton btnBonusItems;
   private GuiButton btnMapType;
   private GuiButton btnAllowCommands;
   private GuiButton btnCustomizeType;
   private String Q;
   private String R;
   private String worldSeed;
   private String worldName;
   private int selectedIndex;
   public String chunkProviderSettingsJson = "";
   private static final String[] disallowedFilenames = new String[]{
      "CON",
      "COM",
      "PRN",
      "AUX",
      "CLOCK$",
      "NUL",
      "COM1",
      "COM2",
      "COM3",
      "COM4",
      "COM5",
      "COM6",
      "COM7",
      "COM8",
      "COM9",
      "LPT1",
      "LPT2",
      "LPT3",
      "LPT4",
      "LPT5",
      "LPT6",
      "LPT7",
      "LPT8",
      "LPT9"
   };

   public GuiCreateWorld(GuiScreen guiScreen) {
      this.parentScreen = guiScreen;
      this.worldSeed = "";
      this.worldName = I18n.format("selectWorld.newWorld");
   }

   @Override
   public void onUpdateScreen() {
      this.worldNameField.updateCursorCounter();
      this.worldSeedField.updateCursorCounter();
   }

   @Override
   public void onInitGui() {
      Keyboard.enableRepeatEvents(true);
      this.m.clear();
      this.m.add(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("selectWorld.create")));
      this.m.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel")));
      this.m.add(this.btnGameMode = new GuiButton(2, this.width / 2 - 75, 115, 150, 20, I18n.format("selectWorld.gameMode")));
      this.m.add(this.btnMoreOptions = new GuiButton(3, this.width / 2 - 75, 187, 150, 20, I18n.format("selectWorld.moreWorldOptions")));
      this.m.add(this.btnMapFeatures = new GuiButton(4, this.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.mapFeatures")));
      this.btnMapFeatures.visible = false;
      this.m.add(this.btnBonusItems = new GuiButton(7, this.width / 2 + 5, 151, 150, 20, I18n.format("selectWorld.bonusItems")));
      this.btnBonusItems.visible = false;
      this.m.add(this.btnMapType = new GuiButton(5, this.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.mapType")));
      this.btnMapType.visible = false;
      this.m.add(this.btnAllowCommands = new GuiButton(6, this.width / 2 - 155, 151, 150, 20, I18n.format("selectWorld.allowCommands")));
      this.btnAllowCommands.visible = false;
      this.m.add(this.btnCustomizeType = new GuiButton(8, this.width / 2 + 5, 120, 150, 20, I18n.format("selectWorld.customizeType")));
      this.btnCustomizeType.visible = false;
      this.worldNameField = new GuiTextField(9, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
      this.worldNameField.setFocused(true);
      this.worldNameField.setText(this.worldName);
      this.worldSeedField = new GuiTextField(10, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
      this.worldSeedField.setText(this.worldSeed);
      this.showMoreWorldOptions(this.inMoreWorldOptionsDisplay);
      this.calcSaveDirName();
      this.updateDisplayState();
   }

   private void calcSaveDirName() {
      this.saveDirName = this.worldNameField.getText().trim();

      for (char var6 : ChatAllowedCharacters.allowedCharactersArray) {
         this.saveDirName = this.saveDirName.replace(var6, '_');
      }

      if (StringUtils.isEmpty(this.saveDirName)) {
         this.saveDirName = "World";
      }

      this.saveDirName = getUncollidingSaveDirName(this.mc.getSaveLoader(), this.saveDirName);
   }

   private void updateDisplayState() {
      this.btnGameMode.displayString = I18n.format("selectWorld.gameMode") + ": " + I18n.format("selectWorld.gameMode." + this.gameMode);
      this.Q = I18n.format("selectWorld.gameMode." + this.gameMode + ".line1");
      this.R = I18n.format("selectWorld.gameMode." + this.gameMode + ".line2");
      this.btnMapFeatures.displayString = I18n.format("selectWorld.mapFeatures") + " ";
      if (this.generateStructuresEnabled) {
         this.btnMapFeatures.displayString = this.btnMapFeatures.displayString + I18n.format("options.on");
      } else {
         this.btnMapFeatures.displayString = this.btnMapFeatures.displayString + I18n.format("options.off");
      }

      this.btnBonusItems.displayString = I18n.format("selectWorld.bonusItems") + " ";
      if (this.bonusChestEnabled && !this.hardCoreMode) {
         this.btnBonusItems.displayString = this.btnBonusItems.displayString + I18n.format("options.on");
      } else {
         this.btnBonusItems.displayString = this.btnBonusItems.displayString + I18n.format("options.off");
      }

      this.btnMapType.displayString = I18n.format("selectWorld.mapType") + " " + I18n.format(WorldType.worldTypes[this.selectedIndex].Method712());
      this.btnAllowCommands.displayString = I18n.format("selectWorld.allowCommands") + " ";
      if (this.allowCheats && !this.hardCoreMode) {
         this.btnAllowCommands.displayString = this.btnAllowCommands.displayString + I18n.format("options.on");
      } else {
         this.btnAllowCommands.displayString = this.btnAllowCommands.displayString + I18n.format("options.off");
      }
   }

   public static String getUncollidingSaveDirName(ISaveFormat iSaveFormat, String string) {
      StringBuilder var2 = new StringBuilder(string.replaceAll("[\\./\"]", "_"));

      for (String var6 : disallowedFilenames) {
         if (var2.toString().equalsIgnoreCase(var6)) {
            var2 = new StringBuilder("_" + var2 + "_");
         }
      }

      string = var2.toString();

      while (iSaveFormat.getWorldInfo(string) != null) {
         string = string + "-";
      }

      return string;
   }

   @Override
   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         if (guiButton.l == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
         } else if (guiButton.l == 0) {
            this.mc.displayGuiScreen(null);
            if (this.alreadyGenerated) {
               return;
            }

            this.alreadyGenerated = true;
            long var4 = new Random().nextLong();
            String var6 = this.worldSeedField.getText();
            if (!StringUtils.isEmpty(var6)) {
               try {
                  long var7 = Long.parseLong(var6);
                  if (var7 != 0L) {
                     var4 = var7;
                  }
               } catch (NumberFormatException var9) {
                  var4 = var6.hashCode();
               }
            }

            WorldSettings.GameType var10 = WorldSettings.GameType.getByName(this.gameMode);
            WorldSettings var8 = new WorldSettings(var4, var10, this.generateStructuresEnabled, this.hardCoreMode, WorldType.worldTypes[this.selectedIndex]);
            var8.setWorldName(this.chunkProviderSettingsJson);
            if (this.bonusChestEnabled && !this.hardCoreMode) {
               var8.enableBonusChest();
            }

            if (this.allowCheats && !this.hardCoreMode) {
               var8.enableCommands();
            }

            this.mc.launchIntegratedServer(this.saveDirName, this.worldNameField.getText().trim(), var8);
         } else if (guiButton.l == 3) {
            this.toggleMoreWorldOptions();
         } else if (guiButton.l == 2) {
            if (this.gameMode.equals("survival")) {
               if (!this.allowCheatsWasSetByUser) {
                  this.allowCheats = false;
               }

               this.hardCoreMode = false;
               this.gameMode = "hardcore";
               this.hardCoreMode = true;
               this.btnAllowCommands.enabled = false;
               this.btnBonusItems.enabled = false;
               this.updateDisplayState();
            } else if (this.gameMode.equals("hardcore")) {
               if (!this.allowCheatsWasSetByUser) {
                  this.allowCheats = true;
               }

               this.hardCoreMode = false;
               this.gameMode = "creative";
               this.updateDisplayState();
               this.hardCoreMode = false;
               this.btnAllowCommands.enabled = true;
               this.btnBonusItems.enabled = true;
            } else {
               if (!this.allowCheatsWasSetByUser) {
                  this.allowCheats = false;
               }

               this.gameMode = "survival";
               this.updateDisplayState();
               this.btnAllowCommands.enabled = true;
               this.btnBonusItems.enabled = true;
               this.hardCoreMode = false;
            }

            this.updateDisplayState();
         } else if (guiButton.l == 4) {
            this.generateStructuresEnabled = !this.generateStructuresEnabled;
            this.updateDisplayState();
         } else if (guiButton.l == 7) {
            this.bonusChestEnabled = !this.bonusChestEnabled;
            this.updateDisplayState();
         } else if (guiButton.l == 5) {
            this.selectedIndex++;
            if (this.selectedIndex >= WorldType.worldTypes.length) {
               this.selectedIndex = 0;
            }

            while (!this.canSelectCurWorldType()) {
               this.selectedIndex++;
               if (this.selectedIndex >= WorldType.worldTypes.length) {
                  this.selectedIndex = 0;
               }
            }

            this.chunkProviderSettingsJson = "";
            this.updateDisplayState();
            this.showMoreWorldOptions(this.inMoreWorldOptionsDisplay);
         } else if (guiButton.l == 6) {
            this.allowCheatsWasSetByUser = true;
            this.allowCheats = !this.allowCheats;
            this.updateDisplayState();
         } else if (guiButton.l == 8) {
            if (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT) {
               this.mc.displayGuiScreen(new GuiCreateFlatWorld(this, this.chunkProviderSettingsJson));
            } else {
               this.mc.displayGuiScreen(new GuiCustomizeWorldScreen(this, this.chunkProviderSettingsJson));
            }
         }
      }
   }

   private boolean canSelectCurWorldType() {
      WorldType var1 = WorldType.worldTypes[this.selectedIndex];
      return var1 != null && var1.getCanBeCreated() && (var1 != WorldType.DEBUG_WORLD || isShiftKeyDown());
   }

   private void toggleMoreWorldOptions() {
      this.showMoreWorldOptions(!this.inMoreWorldOptionsDisplay);
   }

   private void showMoreWorldOptions(boolean boolean1) {
      this.inMoreWorldOptionsDisplay = boolean1;
      if (WorldType.worldTypes[this.selectedIndex] == WorldType.DEBUG_WORLD) {
         this.btnGameMode.visible = !this.inMoreWorldOptionsDisplay;
         this.btnGameMode.enabled = false;
         if (this.savedGameMode == null) {
            this.savedGameMode = this.gameMode;
         }

         this.gameMode = "spectator";
         this.btnMapFeatures.visible = false;
         this.btnBonusItems.visible = false;
         this.btnMapType.visible = this.inMoreWorldOptionsDisplay;
         this.btnAllowCommands.visible = false;
         this.btnCustomizeType.visible = false;
      } else {
         this.btnGameMode.visible = !this.inMoreWorldOptionsDisplay;
         this.btnGameMode.enabled = true;
         if (this.savedGameMode != null) {
            this.gameMode = this.savedGameMode;
            this.savedGameMode = null;
         }

         this.btnMapFeatures.visible = this.inMoreWorldOptionsDisplay && WorldType.worldTypes[this.selectedIndex] != WorldType.CUSTOMIZED;
         this.btnBonusItems.visible = this.inMoreWorldOptionsDisplay;
         this.btnMapType.visible = this.inMoreWorldOptionsDisplay;
         this.btnAllowCommands.visible = this.inMoreWorldOptionsDisplay;
         this.btnCustomizeType.visible = this.inMoreWorldOptionsDisplay
            && (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT || WorldType.worldTypes[this.selectedIndex] == WorldType.CUSTOMIZED);
      }

      this.updateDisplayState();
      if (this.inMoreWorldOptionsDisplay) {
         this.btnMoreOptions.displayString = I18n.format("gui.done");
      } else {
         this.btnMoreOptions.displayString = I18n.format("selectWorld.moreWorldOptions");
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (this.worldNameField.isFocused() && !this.inMoreWorldOptionsDisplay) {
         this.worldNameField.textboxKeyTyped(charTyped, keyCode);
         this.worldName = this.worldNameField.getText();
      } else if (this.worldSeedField.isFocused() && this.inMoreWorldOptionsDisplay) {
         this.worldSeedField.textboxKeyTyped(charTyped, keyCode);
         this.worldSeed = this.worldSeedField.getText();
      }

      if (keyCode == 28 || keyCode == 156) {
         this.actionPerformed(this.m.get(0));
      }

      this.m.get(0).enabled = this.worldNameField.getText().length() > 0;
      this.calcSaveDirName();
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      super.onMouseClicked(mouseX, mouseY, button);
      if (this.inMoreWorldOptionsDisplay) {
         this.worldSeedField.mouseClicked(mouseX, mouseY, button);
      } else {
         this.worldNameField.mouseClicked(mouseX, mouseY, button);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.create"), this.width / 2, 20, -1);
      if (this.inMoreWorldOptionsDisplay) {
         this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterSeed"), this.width / 2 - 100, 47, -6250336);
         this.drawString(this.fontRendererObj, I18n.format("selectWorld.seedInfo"), this.width / 2 - 100, 85, -6250336);
         if (this.btnMapFeatures.visible) {
            this.drawString(this.fontRendererObj, I18n.format("selectWorld.mapFeatures.info"), this.width / 2 - 150, 122, -6250336);
         }

         if (this.btnAllowCommands.visible) {
            this.drawString(this.fontRendererObj, I18n.format("selectWorld.allowCommands.info"), this.width / 2 - 150, 172, -6250336);
         }

         this.worldSeedField.drawTextBox();
         if (WorldType.worldTypes[this.selectedIndex].showWorldInfoNotice()) {
            this.fontRendererObj
               .drawSplitString(
                  I18n.format(WorldType.worldTypes[this.selectedIndex].Method713()),
                  this.btnMapType.xPosition + 2,
                  this.btnMapType.yPosition + 22,
                  this.btnMapType.getButtonWidth(),
                  10526880
               );
         }
      } else {
         this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterName"), this.width / 2 - 100, 47, -6250336);
         this.drawString(this.fontRendererObj, I18n.format("selectWorld.resultFolder") + " " + this.saveDirName, this.width / 2 - 100, 85, -6250336);
         this.worldNameField.drawTextBox();
         this.drawString(this.fontRendererObj, this.Q, this.width / 2 - 100, 137, -6250336);
         this.drawString(this.fontRendererObj, this.R, this.width / 2 - 100, 149, -6250336);
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   public void recreateFromExistingWorld(WorldInfo worldInfo) {
      this.worldName = I18n.format("selectWorld.newWorld.copyOf", worldInfo.getWorldName());
      this.worldSeed = worldInfo.getSeed() + "";
      this.selectedIndex = worldInfo.getTerrainType().getWorldTypeID();
      this.chunkProviderSettingsJson = worldInfo.getGeneratorOptions();
      this.generateStructuresEnabled = worldInfo.isMapFeaturesEnabled();
      this.allowCheats = worldInfo.areCommandsAllowed();
      if (worldInfo.isHardcoreModeEnabled()) {
         this.gameMode = "hardcore";
      } else if (worldInfo.getGameType().isSurvivalOrAdventure()) {
         this.gameMode = "survival";
      } else if (worldInfo.getGameType().isCreative()) {
         this.gameMode = "creative";
      }
   }
}
