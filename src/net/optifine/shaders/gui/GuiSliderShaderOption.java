package net.optifine.shaders.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.optifine.shaders.config.ShaderOption;

public class GuiSliderShaderOption extends GuiButtonShaderOption {
   private float sliderValue = 1.0F;
   public boolean dragging;
   private ShaderOption shaderOption = null;

   public GuiSliderShaderOption(int integer1, int integer2, int integer3, int integer4, int integer5, ShaderOption shaderOption, String string) {
      super(integer1, integer2, integer3, integer4, integer5, shaderOption, string);
      this.shaderOption = shaderOption;
      this.sliderValue = shaderOption.getIndexNormalized();
      this.displayString = GuiShaderOptions.getButtonText(shaderOption, this.width);
   }

   @Override
   protected int getHoverState(boolean boolean1) {
      return 0;
   }

   @Override
   protected void mouseDragged(Minecraft minecraft, int integer2, int integer3) {
      if (this.visible) {
         if (this.dragging && !GuiScreen.isShiftKeyDown()) {
            this.sliderValue = (float)(integer2 - (this.xPosition + 4)) / (this.width - 8);
            this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
            this.shaderOption.setIndexNormalized(this.sliderValue);
            this.sliderValue = this.shaderOption.getIndexNormalized();
            this.displayString = GuiShaderOptions.getButtonText(this.shaderOption, this.width);
         }

         minecraft.getTextureManager().bindTexture(buttonTextures);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
         this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
      }
   }

   @Override
   public boolean mousePressed(Minecraft minecraft, int integer2, int integer3) {
      if (super.mousePressed(minecraft, integer2, integer3)) {
         this.sliderValue = (float)(integer2 - (this.xPosition + 4)) / (this.width - 8);
         this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
         this.shaderOption.setIndexNormalized(this.sliderValue);
         this.displayString = GuiShaderOptions.getButtonText(this.shaderOption, this.width);
         this.dragging = true;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void mouseReleased(int integer1, int integer2) {
      this.dragging = false;
   }

   @Override
   public void valueChanged() {
      this.sliderValue = this.shaderOption.getIndexNormalized();
   }

   @Override
   public boolean isSwitchable() {
      return false;
   }
}
