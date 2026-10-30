package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class GuiControls extends GuiScreen {
   private static final GameSettings.Options[] optionsArr = new GameSettings.Options[]{
      GameSettings.Options.INVERT_MOUSE, GameSettings.Options.SENSITIVITY, GameSettings.Options.TOUCHSCREEN
   };
   private final GuiScreen parentScreen;
   protected String screenTitle = "Controls";
   private final GameSettings options;
   public KeyBinding buttonId = null;
   public long time;
   private GuiKeyBindingList keyBindingList;
   private GuiButton buttonReset;

   public GuiControls(GuiScreen guiScreen, GameSettings gameSettings) {
      this.parentScreen = guiScreen;
      this.options = gameSettings;
   }

   @Override
   public void onInitGui() {
      this.keyBindingList = new GuiKeyBindingList(this, this.mc);
      this.m.add(new GuiButton(200, this.width / 2 - 155, this.height - 29, 150, 20, I18n.format("gui.done")));
      this.m.add(this.buttonReset = new GuiButton(201, this.width / 2 - 155 + 160, this.height - 29, 150, 20, I18n.format("controls.resetAll")));
      this.screenTitle = I18n.format("controls.title");
      int var3 = 0;

      for (GameSettings.Options var7 : optionsArr) {
         if (var7.getEnumFloat()) {
            this.m.add(new GuiOptionSlider(var7.returnEnumOrdinal(), this.width / 2 - 155 + var3 % 2 * 160, 18 + 24 * (var3 >> 1), var7));
         } else {
            this.m
               .add(
                  new GuiOptionButton(
                     var7.returnEnumOrdinal(), this.width / 2 - 155 + var3 % 2 * 160, 18 + 24 * (var3 >> 1), var7, this.options.getKeyBinding(var7)
                  )
               );
         }

         var3++;
      }
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.keyBindingList.handleMouseInput();
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.l == 200) {
         this.mc.displayGuiScreen(this.parentScreen);
      } else if (guiButton.l == 201) {
         for (KeyBinding var7 : this.mc.gameSettings.keyBindings) {
            var7.setKeyCode(var7.getKeyCodeDefault());
         }

         KeyBinding.resetKeyBindingArrayAndHash();
      } else if (guiButton.l < 100 && guiButton instanceof GuiOptionButton) {
         this.options.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
         guiButton.displayString = this.options.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.l));
      }
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      if (this.buttonId != null) {
         this.options.setOptionKeyBinding(this.buttonId, -100 + button);
         this.buttonId = null;
         KeyBinding.resetKeyBindingArrayAndHash();
      } else if (button != 0 || !this.keyBindingList.mouseClicked(mouseX, mouseY, button)) {
         super.onMouseClicked(mouseX, mouseY, button);
      }
   }

   @Override
   protected void onMouseReleased(int mouseX, int mouseY, int button) {
      if (button != 0 || !this.keyBindingList.mouseReleased(mouseX, mouseY, button)) {
         super.onMouseReleased(mouseX, mouseY, button);
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (this.buttonId != null) {
         if (keyCode == 1) {
            this.options.setOptionKeyBinding(this.buttonId, 0);
         } else if (keyCode != 0) {
            this.options.setOptionKeyBinding(this.buttonId, keyCode);
         } else if (charTyped > 0) {
            this.options.setOptionKeyBinding(this.buttonId, charTyped + 256);
         }

         this.buttonId = null;
         this.time = Minecraft.getSystemTime();
         KeyBinding.resetKeyBindingArrayAndHash();
      } else {
         super.onKeyTyped(charTyped, keyCode);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.keyBindingList.drawScreen(mouseX, mouseY, partialTicks);
      this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 8, 16777215);
      boolean var6 = true;

      for (KeyBinding var10 : this.options.keyBindings) {
         if (var10.getKeyCode() != var10.getKeyCodeDefault()) {
            var6 = false;
            break;
         }
      }

      this.buttonReset.enabled = !var6;
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }
}
