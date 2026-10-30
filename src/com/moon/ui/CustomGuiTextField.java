package com.moon.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;

public class CustomGuiTextField extends GuiTextField {
   private static Minecraft mc = Minecraft.getMinecraft();
   private boolean maskText = false;

   public CustomGuiTextField(int componentId, int x, int y, int width, int height) {
      super(componentId, mc.fontRendererObj, x, y, width, height);
      this.setMaxStringLength(10000000);
   }

   public CustomGuiTextField(int componentId, int x, int y, int width, int height, boolean maskText) {
      super(componentId, mc.fontRendererObj, x, y, width, height);
      this.maskText = maskText;
      this.setMaxStringLength(10000000);
   }

   @Override
   public void drawTextBox() {
      if (this.getVisible()) {
         drawRect(this.xPosition, this.yPosition + this.height - 1, this.xPosition + this.width, this.yPosition + this.height, -1);
         int textColor = this.isEnabled ? this.enabledColor : this.disabledColor;
         int cursorOffset = this.cursorPosition - this.lineScrollOffset;
         int selectionOffset = this.selectionEnd - this.lineScrollOffset;
         String displayText = this.maskText ? this.text.replaceAll(".", "*") : this.text;
         String visibleText = mc.fontRendererObj.trimStringToWidth(displayText.substring(this.lineScrollOffset), this.getWidth());
         boolean cursorInRange = cursorOffset >= 0 && cursorOffset <= visibleText.length();
         boolean showCursor = this.isFocused && this.cursorCounter / 6 % 2 == 0 && cursorInRange;
         int textX = this.xPosition + 4;
         int textY = this.yPosition + (this.height - 8) / 2;
         int currentX = textX;
         if (selectionOffset > visibleText.length()) {
            selectionOffset = visibleText.length();
         }

         if (visibleText.length() > 0) {
            String textBeforeCursor = cursorInRange ? visibleText.substring(0, cursorOffset) : visibleText;
            currentX = mc.fontRendererObj.drawStringWithShadow(textBeforeCursor, textX, textY, textColor);
         }

         boolean cursorNotAtEnd = this.cursorPosition < displayText.length() || displayText.length() >= this.getMaxStringLength();
         int cursorX = currentX;
         if (!cursorInRange) {
            cursorX = cursorOffset > 0 ? textX + this.width : textX;
         } else if (cursorNotAtEnd) {
            cursorX = currentX - 1;
            currentX--;
         }

         if (visibleText.length() > 0 && cursorInRange && cursorOffset < visibleText.length()) {
            mc.fontRendererObj.drawStringWithShadow(visibleText.substring(cursorOffset), currentX, textY, textColor);
         }

         if (showCursor) {
            if (cursorNotAtEnd) {
               Gui.drawRect(cursorX, textY - 1, cursorX + 1, textY + 1 + mc.fontRendererObj.FONT_HEIGHT, -3092272);
            } else {
               mc.fontRendererObj.drawStringWithShadow("_", cursorX, textY, textColor);
            }
         }

         if (selectionOffset != cursorOffset) {
            int selectionX = textX + mc.fontRendererObj.getStringWidth(visibleText.substring(0, selectionOffset));
            this.drawCursorVertical(cursorX, textY - 1, selectionX - 1, textY + 1 + mc.fontRendererObj.FONT_HEIGHT);
         }
      }
   }
}
