package net.optifine.shaders.gui;

import net.minecraft.client.gui.GuiButton;
import net.optifine.shaders.config.ShaderOption;

public class GuiButtonShaderOption extends GuiButton {
   private ShaderOption shaderOption = null;

   public GuiButtonShaderOption(int integer1, int integer2, int integer3, int integer4, int integer5, ShaderOption shaderOption, String string) {
      super(integer1, integer2, integer3, integer4, integer5, string);
      this.shaderOption = shaderOption;
   }

   public ShaderOption getShaderOption() {
      return this.shaderOption;
   }

   public void valueChanged() {
   }

   public boolean isSwitchable() {
      return true;
   }
}
