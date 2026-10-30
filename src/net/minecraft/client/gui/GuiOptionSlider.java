package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;

public class GuiOptionSlider extends GuiButton {
   private float sliderValue = 1.0F;
   public boolean dragging;
   private final GameSettings.Options options;
   private final float field_146132_r;
   private final float field_146131_s;

   public GuiOptionSlider(int integer1, int integer2, int integer3, GameSettings.Options options) {
      this(integer1, integer2, integer3, options, 0.0F, 1.0F);
   }

   public GuiOptionSlider(int integer1, int integer2, int integer3, GameSettings.Options options, float float5, float float6) {
      super(integer1, integer2, integer3, 150, 20, "");
      this.options = options;
      this.field_146132_r = float5;
      this.field_146131_s = float6;
      Minecraft var9 = Minecraft.getMinecraft();
      this.sliderValue = options.normalizeValue(var9.gameSettings.getOptionFloatValue(options));
      this.displayString = var9.gameSettings.getKeyBinding(options);
   }

   @Override
   protected int getHoverState(boolean boolean1) {
      return 0;
   }

   @Override
   protected void mouseDragged(Minecraft minecraft, int integer2, int integer3) {
      if (this.visible) {
         if (this.dragging) {
            this.sliderValue = (float)(integer2 - (this.xPosition + 4)) / (this.width - 8);
            this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
            float var6 = this.options.denormalizeValue(this.sliderValue);
            minecraft.gameSettings.setOptionFloatValue(this.options, var6);
            this.sliderValue = this.options.normalizeValue(var6);
            this.displayString = minecraft.gameSettings.getKeyBinding(this.options);
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
         minecraft.gameSettings.setOptionFloatValue(this.options, this.options.denormalizeValue(this.sliderValue));
         this.displayString = minecraft.gameSettings.getKeyBinding(this.options);
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
}
