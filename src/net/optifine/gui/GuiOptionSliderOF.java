package net.optifine.gui;

import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.settings.GameSettings;

public class GuiOptionSliderOF extends GuiOptionSlider implements IOptionControl {
   private GameSettings.Options option = null;

   public GuiOptionSliderOF(int integer1, int integer2, int integer3, GameSettings.Options options) {
      super(integer1, integer2, integer3, options);
      this.option = options;
   }

   @Override
   public GameSettings.Options getOption() {
      return this.option;
   }
}
