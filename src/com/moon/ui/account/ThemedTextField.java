package com.moon.ui.account;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.moon.Moon;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.manager.FontType;
import com.moon.ui.theme.ColorScheme;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;

public class ThemedTextField extends Gui {
   private final int id;
   private final FontRenderer fontRenderer;
   public final int width;
   public final int height;
   public float x;
   public float y;
   private String text = "";
   private int maxStringLength = 32;
   private int cursorBlinkCounter;
   private boolean centered = true;
   private boolean canLoseFocus = true;
   private boolean focused;
   private boolean editable = true;
   private int scrollOffset;
   private int cursorPosition;
   private int selectionEnd;
   private int enabledColor = 14737632;
   private int disabledColor = 7368816;
   private boolean visible = true;
   private GuiPageButtonList.GuiResponder guiResponder;
   private Predicate<String> textValidator = Predicates.alwaysTrue();
   private ColorScheme theme;

   public ThemedTextField(int id, FontRenderer fontRenderer, float x, float y, int width, int height, ColorScheme theme) {
      this.id = id;
      this.fontRenderer = fontRenderer;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.theme = theme;
   }

   public void setGuiResponder(GuiPageButtonList.GuiResponder guiResponder) {
      this.guiResponder = guiResponder;
   }

   public void updateCursorCounter() {
      this.cursorBlinkCounter++;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      if (this.textValidator.apply(text)) {
         if (text.length() > this.maxStringLength) {
            this.text = text.substring(0, this.maxStringLength);
         } else {
            this.text = text;
         }

         this.setCursorPositionEnd();
      }
   }

   public String getSelectedText() {
      int start = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
      int end = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
      return this.text.substring(start, end);
   }

   public void setTextValidator(Predicate<String> textValidator) {
      this.textValidator = textValidator;
   }

   public void writeText(String text) {
      String newText = "";
      String filteredText = ChatAllowedCharacters.filterAllowedCharacters(text);
      int start = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
      int end = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
      int availableSpace = this.maxStringLength - this.text.length() - (start - end);
      if (this.text.length() > 0) {
         newText = "" + this.text.substring(0, start);
      }

      int insertedLength;
      if (availableSpace < filteredText.length()) {
         newText = newText + filteredText.substring(0, availableSpace);
         insertedLength = availableSpace;
      } else {
         newText = newText + filteredText;
         insertedLength = filteredText.length();
      }

      if (this.text.length() > 0 && end < this.text.length()) {
         newText = newText + this.text.substring(end);
      }

      if (this.textValidator.apply(newText)) {
         this.text = newText;
         this.moveCursorBy(start - this.selectionEnd + insertedLength);
         if (this.guiResponder != null) {
            this.guiResponder.func_175319_a(this.id, this.text);
         }
      }
   }

   public void deleteWord(int direction) {
      if (this.text.length() != 0) {
         if (this.selectionEnd != this.cursorPosition) {
            this.writeText("");
         } else {
            this.deleteFromCursor(this.getWordPosition(direction) - this.cursorPosition);
         }
      }
   }

   public void deleteFromCursor(int direction) {
      if (this.text.length() != 0) {
         if (this.selectionEnd != this.cursorPosition) {
            this.writeText("");
         } else {
            boolean backwards = direction < 0;
            int start = backwards ? this.cursorPosition + direction : this.cursorPosition;
            int end = backwards ? this.cursorPosition : this.cursorPosition + direction;
            String newText = "";
            if (start >= 0) {
               newText = this.text.substring(0, start);
            }

            if (end < this.text.length()) {
               newText = newText + this.text.substring(end);
            }

            if (this.textValidator.apply(newText)) {
               this.text = newText;
               if (backwards) {
                  this.moveCursorBy(direction);
               }

               if (this.guiResponder != null) {
                  this.guiResponder.func_175319_a(this.id, this.text);
               }
            }
         }
      }
   }

   public int getId() {
      return this.id;
   }

   public int getWordPosition(int wordOffset) {
      return this.getWordPosition(wordOffset, this.getCursorPosition());
   }

   public int getWordPosition(int wordOffset, int cursorPosition) {
      return this.getWordPosition(wordOffset, cursorPosition, true);
   }

   public int getWordPosition(int wordOffset, int cursorPosition, boolean skipSpaces) {
      int pos = cursorPosition;
      boolean backwards = wordOffset < 0;
      int wordCount = Math.abs(wordOffset);

      for (int i = 0; i < wordCount; i++) {
         if (!backwards) {
            int textLength = this.text.length();
            pos = this.text.indexOf(32, pos);
            if (pos == -1) {
               pos = textLength;
            } else {
               while (skipSpaces && pos < textLength && this.text.charAt(pos) == ' ') {
                  pos++;
               }
            }
         } else {
            while (skipSpaces && pos > 0 && this.text.charAt(pos - 1) == ' ') {
               pos--;
            }

            while (pos > 0 && this.text.charAt(pos - 1) != ' ') {
               pos--;
            }
         }
      }

      return pos;
   }

   public void moveCursorBy(int offset) {
      this.setCursorPosition(this.selectionEnd + offset);
   }

   public void setCursorPositionStart() {
      this.setCursorPosition(0);
   }

   public void setCursorPositionEnd() {
      this.setCursorPosition(this.text.length());
   }

   public boolean textboxKeyTyped(char charTyped, int keyCode) {
      if (!this.focused) {
         return false;
      }

      if (GuiScreen.isKeyComboCtrlA(keyCode)) {
         this.setCursorPositionEnd();
         this.setSelectionPos(0);
         return true;
      }

      if (GuiScreen.isKeyComboCtrlC(keyCode)) {
         GuiScreen.setClipboardString(this.getSelectedText());
         return true;
      }

      if (GuiScreen.isKeyComboCtrlV(keyCode)) {
         if (this.editable) {
            this.writeText(GuiScreen.getClipboardString());
         }

         return true;
      } else if (GuiScreen.isKeyComboCtrlX(keyCode)) {
         GuiScreen.setClipboardString(this.getSelectedText());
         if (this.editable) {
            this.writeText("");
         }

         return true;
      } else {
         switch (keyCode) {
            case 14:
               if (GuiScreen.isCtrlKeyDown()) {
                  if (this.editable) {
                     this.deleteWord(-1);
                  }
               } else if (this.editable) {
                  this.deleteFromCursor(-1);
               }

               return true;
            case 199:
               if (GuiScreen.isShiftKeyDown()) {
                  this.setSelectionPos(0);
               } else {
                  this.setCursorPositionStart();
               }

               return true;
            case 203:
               if (GuiScreen.isShiftKeyDown()) {
                  if (GuiScreen.isCtrlKeyDown()) {
                     this.setSelectionPos(this.getWordPosition(-1, this.getSelectionEnd()));
                  } else {
                     this.setSelectionPos(this.getSelectionEnd() - 1);
                  }
               } else if (GuiScreen.isCtrlKeyDown()) {
                  this.setCursorPosition(this.getWordPosition(-1));
               } else {
                  this.moveCursorBy(-1);
               }

               return true;
            case 205:
               if (GuiScreen.isShiftKeyDown()) {
                  if (GuiScreen.isCtrlKeyDown()) {
                     this.setSelectionPos(this.getWordPosition(1, this.getSelectionEnd()));
                  } else {
                     this.setSelectionPos(this.getSelectionEnd() + 1);
                  }
               } else if (GuiScreen.isCtrlKeyDown()) {
                  this.setCursorPosition(this.getWordPosition(1));
               } else {
                  this.moveCursorBy(1);
               }

               return true;
            case 207:
               if (GuiScreen.isShiftKeyDown()) {
                  this.setSelectionPos(this.text.length());
               } else {
                  this.setCursorPositionEnd();
               }

               return true;
            case 211:
               if (GuiScreen.isCtrlKeyDown()) {
                  if (this.editable) {
                     this.deleteWord(1);
                  }
               } else if (this.editable) {
                  this.deleteFromCursor(1);
               }

               return true;
            default:
               if (ChatAllowedCharacters.isAllowedCharacter(charTyped)) {
                  if (this.editable) {
                     this.writeText(Character.toString(charTyped));
                  }

                  return true;
               } else {
                  return false;
               }
         }
      }
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      boolean hovered = mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
      if (this.canLoseFocus) {
         this.setFocused(hovered);
      }

      if (this.focused && hovered && button == 0) {
         int relativeX = mouseX - MathHelper.ceiling_double_int(this.x);
         if (this.centered) {
            relativeX -= 4;
         }

         String trimmedText = this.fontRenderer.trimStringToWidth(this.text.substring(this.scrollOffset), this.getWidth());
         this.setCursorPosition(this.fontRenderer.trimStringToWidth(trimmedText, relativeX).length() + this.scrollOffset);
      }
   }

   public void drawTextBox() {
      if (this.isVisible()) {
         com.moon.render.font.FontRenderer msdfFontRenderer = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
         RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(msdfFontRenderer).build();
         RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
         rectTask.addCommand(RoundedRectangleCommand.of(this.x, this.y - 0.5F, this.width, this.height, 6.0F, this.theme.getOnSecondary()));
         if (!this.editable) {
            ;
         }

         int relativeCursor = this.cursorPosition - this.scrollOffset;
         int relativeSelectionEnd = this.selectionEnd - this.scrollOffset;
         String visibleText = this.fontRenderer.trimStringToWidth(this.text.substring(this.scrollOffset), this.getWidth());
         boolean cursorVisible = relativeCursor >= 0 && relativeCursor <= visibleText.length();
         boolean showCursor = this.focused && this.cursorBlinkCounter / 6 % 2 == 0 && cursorVisible;
         float renderX = this.centered ? this.x + 4.0F : this.x;
         float renderY = this.centered ? this.y + (this.height - 8) / 2.0F : this.y;
         float currentX = renderX;
         if (relativeSelectionEnd > visibleText.length()) {
            relativeSelectionEnd = visibleText.length();
         }

         if (visibleText.length() > 0) {
            String textBeforeCursor = cursorVisible ? visibleText.substring(0, relativeCursor) : visibleText;
            msdfFontRenderer.draw(textTask, textBeforeCursor, 9.0F, renderX, renderY - 1.5F, this.theme.getPrimary(), false);
            currentX += (int)msdfFontRenderer.getTextWidth(textBeforeCursor, 9.0F) + 2.0F;
         }

         boolean canDrawCursor = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
         float cursorX = currentX;
         if (!cursorVisible) {
            cursorX = relativeCursor > 0 ? renderX + this.width : renderX;
         } else if (canDrawCursor) {
            cursorX = currentX--;
         }

         if (visibleText.length() > 0 && cursorVisible && relativeCursor < visibleText.length()) {
            msdfFontRenderer.draw(textTask, visibleText.substring(relativeCursor), 9.0F, currentX + 2.0F, renderY - 1.5F, this.theme.getPrimary(), false);
            float afterTextX = currentX + ((int)msdfFontRenderer.getTextWidth(visibleText.substring(relativeCursor) + "2", 9.0F) + 2.0F);
         }

         float lineHeight = msdfFontRenderer.getLineHeight(9.0F);
         if (showCursor) {
            if (canDrawCursor) {
               rectTask.addCommand(RoundedRectangleCommand.of(cursorX - 1.0F, renderY - 2.0F, 2.0F, lineHeight, 0.0F, this.theme.getPrimary()));
            } else {
               rectTask.addCommand(RoundedRectangleCommand.of(cursorX - 1.0F, renderY - 2.0F, 2.0F, lineHeight, 0.0F, this.theme.getPrimary()));
            }
         }

         if (relativeSelectionEnd != relativeCursor) {
            float selectionWidth = (int)(renderX + msdfFontRenderer.getTextWidth(visibleText.substring(0, relativeSelectionEnd), 9.0F)) - 1 - cursorX + 1.0F;
            rectTask.addCommand(RoundedRectangleCommand.of(cursorX + selectionWidth, renderY - 2.0F, -selectionWidth, lineHeight, 0.0F, -3092272));
         }

         rectTask.render();
         textTask.render();
      }
   }

   public int getMaxStringLength() {
      return this.maxStringLength;
   }

   public void setMaxStringLength(int maxStringLength) {
      this.maxStringLength = maxStringLength;
      if (this.text.length() > maxStringLength) {
         this.text = this.text.substring(0, maxStringLength);
      }
   }

   public int getCursorPosition() {
      return this.cursorPosition;
   }

   public void setCursorPosition(int cursorPosition) {
      this.cursorPosition = cursorPosition;
      int textLength = this.text.length();
      this.cursorPosition = MathHelper.clamp_int(this.cursorPosition, 0, textLength);
      this.setSelectionPos(this.cursorPosition);
   }

   public boolean isCentered() {
      return this.centered;
   }

   public void setCentered(boolean centered) {
      this.centered = centered;
   }

   public void setEnabledColor(int color) {
      this.enabledColor = color;
   }

   public void setDisabledColor(int color) {
      this.disabledColor = color;
   }

   public boolean isFocused() {
      return this.focused;
   }

   public void setFocused(boolean focused) {
      if (focused && !this.focused) {
         this.cursorBlinkCounter = 0;
      }

      this.focused = focused;
   }

   public void setEditable(boolean editable) {
      this.editable = editable;
   }

   public int getSelectionEnd() {
      return this.selectionEnd;
   }

   public int getWidth() {
      return this.isCentered() ? this.width - 8 : this.width;
   }

   public void setSelectionPos(int selectionPos) {
      int textLength = this.text.length();
      if (selectionPos > textLength) {
         selectionPos = textLength;
      }

      if (selectionPos < 0) {
         selectionPos = 0;
      }

      this.selectionEnd = selectionPos;
      if (this.fontRenderer != null) {
         if (this.scrollOffset > textLength) {
            this.scrollOffset = textLength;
         }

         int width = this.getWidth();
         int visibleEnd = this.fontRenderer.trimStringToWidth(this.text.substring(this.scrollOffset), width).length() + this.scrollOffset;
         if (selectionPos == this.scrollOffset) {
            this.scrollOffset = this.scrollOffset - this.fontRenderer.trimStringToWidth(this.text, width, true).length();
         }

         if (selectionPos > visibleEnd) {
            this.scrollOffset += selectionPos - visibleEnd;
         } else if (selectionPos <= this.scrollOffset) {
            this.scrollOffset = this.scrollOffset - (this.scrollOffset - selectionPos);
         }

         this.scrollOffset = MathHelper.clamp_int(this.scrollOffset, 0, textLength);
      }
   }

   public void setCanLoseFocus(boolean canLoseFocus) {
      this.canLoseFocus = canLoseFocus;
   }

   public boolean isVisible() {
      return this.visible;
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }
}
