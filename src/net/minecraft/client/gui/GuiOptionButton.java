package net.minecraft.client.gui;

import net.minecraft.client.settings.GameSettings;

public class GuiOptionButton extends GuiButton {
   private final GameSettings.Options enumOptions;

   public GuiOptionButton(int integer1, int integer2, int integer3, String string) {
      this(integer1, integer2, integer3, null, string);
   }

   public GuiOptionButton(int integer1, int integer2, int integer3, int integer4, int integer5, String string) {
      super(integer1, integer2, integer3, integer4, integer5, string);
      this.enumOptions = null;
   }

   public GuiOptionButton(int integer1, int integer2, int integer3, GameSettings.Options options, String string) {
      super(integer1, integer2, integer3, 150, 20, string);
      this.enumOptions = options;
   }

   public GameSettings.Options returnEnumOptions() {
      return this.enumOptions;
   }
}
