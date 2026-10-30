package net.optifine.gui;

import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.settings.GameSettings;

public class GuiOptionButtonOF extends GuiOptionButton implements IOptionControl {
   private GameSettings.Options option = null;

   public GuiOptionButtonOF(int integer1, int integer2, int integer3, GameSettings.Options options, String string) {
      super(integer1, integer2, integer3, options, string);
      this.option = options;
   }

   @Override
   public GameSettings.Options getOption() {
      return this.option;
   }
}
