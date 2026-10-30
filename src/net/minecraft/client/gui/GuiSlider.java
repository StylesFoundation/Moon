package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiSlider extends GuiButton {
   private float sliderPosition = 1.0F;
   public boolean isMouseDown;
   private final String name;
   private final float min;
   private final float max;
   private final GuiPageButtonList.GuiResponder responder;
   private final GuiSlider.FormatHelper formatHelper;

   public GuiSlider(
      GuiPageButtonList.GuiResponder guiResponder,
      int integer2,
      int integer3,
      int integer4,
      String string,
      float float6,
      float float7,
      float float8,
      GuiSlider.FormatHelper formatHelper
   ) {
      super(integer2, integer3, integer4, 150, 20, "");
      this.name = string;
      this.min = float6;
      this.max = float7;
      this.sliderPosition = (float8 - float6) / (float7 - float6);
      this.formatHelper = formatHelper;
      this.responder = guiResponder;
      this.displayString = this.getDisplayString();
   }

   public float func_175220_c() {
      return this.min + (this.max - this.min) * this.sliderPosition;
   }

   public void func_175218_a(float float1, boolean boolean2) {
      this.sliderPosition = (float1 - this.min) / (this.max - this.min);
      this.displayString = this.getDisplayString();
      if (boolean2) {
         this.responder.onTick(this.l, this.func_175220_c());
      }
   }

   public float func_175217_d() {
      return this.sliderPosition;
   }

   private String getDisplayString() {
      return this.formatHelper == null
         ? I18n.format(this.name) + ": " + this.func_175220_c()
         : this.formatHelper.getText(this.l, I18n.format(this.name), this.func_175220_c());
   }

   @Override
   protected int getHoverState(boolean boolean1) {
      return 0;
   }

   @Override
   protected void mouseDragged(Minecraft minecraft, int integer2, int integer3) {
      if (this.visible) {
         if (this.isMouseDown) {
            this.sliderPosition = (float)(integer2 - (this.xPosition + 4)) / (this.width - 8);
            if (this.sliderPosition < 0.0F) {
               this.sliderPosition = 0.0F;
            }

            if (this.sliderPosition > 1.0F) {
               this.sliderPosition = 1.0F;
            }

            this.displayString = this.getDisplayString();
            this.responder.onTick(this.l, this.func_175220_c());
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(this.xPosition + (int)(this.sliderPosition * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
         this.drawTexturedModalRect(this.xPosition + (int)(this.sliderPosition * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
      }
   }

   public void func_175219_a(float float1) {
      this.sliderPosition = float1;
      this.displayString = this.getDisplayString();
      this.responder.onTick(this.l, this.func_175220_c());
   }

   @Override
   public boolean mousePressed(Minecraft minecraft, int integer2, int integer3) {
      if (super.mousePressed(minecraft, integer2, integer3)) {
         this.sliderPosition = (float)(integer2 - (this.xPosition + 4)) / (this.width - 8);
         if (this.sliderPosition < 0.0F) {
            this.sliderPosition = 0.0F;
         }

         if (this.sliderPosition > 1.0F) {
            this.sliderPosition = 1.0F;
         }

         this.displayString = this.getDisplayString();
         this.responder.onTick(this.l, this.func_175220_c());
         this.isMouseDown = true;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void mouseReleased(int integer1, int integer2) {
      this.isMouseDown = false;
   }

   public interface FormatHelper {
      String getText(int integer, String string, float float3);
   }
}
