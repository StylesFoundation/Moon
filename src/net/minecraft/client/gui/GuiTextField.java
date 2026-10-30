package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;

public class GuiTextField extends Gui {
   private final int id;
   private final FontRenderer fontRendererInstance;
   public int xPosition;
   public int yPosition;
   public final int width;
   public final int height;
   public String text = "";
   public int maxStringLength = 32;
   public int cursorCounter;
   public boolean enableBackgroundDrawing = true;
   public boolean canLoseFocus = true;
   public boolean isFocused;
   public boolean isEnabled = true;
   public int lineScrollOffset;
   public int cursorPosition;
   public int selectionEnd;
   public int enabledColor = 14737632;
   public int disabledColor = 7368816;
   public boolean visible = true;
   private GuiPageButtonList.GuiResponder field_175210_x;
   private Predicate<String> validator = Predicates.alwaysTrue();

   public GuiTextField(int integer1, FontRenderer fontRenderer, int integer3, int integer4, int integer5, int integer6) {
      this.id = integer1;
      this.fontRendererInstance = fontRenderer;
      this.xPosition = integer3;
      this.yPosition = integer4;
      this.width = integer5;
      this.height = integer6;
   }

   public void func_175207_a(GuiPageButtonList.GuiResponder guiResponder) {
      this.field_175210_x = guiResponder;
   }

   public void updateCursorCounter() {
      this.cursorCounter++;
   }

   public void setText(String string) {
      if (this.validator.apply(string)) {
         if (string.length() > this.maxStringLength) {
            this.text = string.substring(0, this.maxStringLength);
         } else {
            this.text = string;
         }

         this.setCursorPositionEnd();
      }
   }

   public String getText() {
      return this.text;
   }

   public String getSelectedText() {
      int var1 = Math.min(this.cursorPosition, this.selectionEnd);
      int var2 = Math.max(this.cursorPosition, this.selectionEnd);
      return this.text.substring(var1, var2);
   }

   public void setValidator(Predicate<String> predicate) {
      this.validator = predicate;
   }

   public void writeText(String string) {
      String var2 = "";
      String var3 = ChatAllowedCharacters.filterAllowedCharacters(string);
      int var4 = Math.min(this.cursorPosition, this.selectionEnd);
      int var5 = Math.max(this.cursorPosition, this.selectionEnd);
      int var6 = this.maxStringLength - this.text.length() - (var4 - var5);
      if (this.text.length() > 0) {
         var2 = "" + this.text.substring(0, var4);
      }

      int var7;
      if (var6 < var3.length()) {
         var2 = var2 + var3.substring(0, var6);
         var7 = var6;
      } else {
         var2 = var2 + var3;
         var7 = var3.length();
      }

      if (this.text.length() > 0 && var5 < this.text.length()) {
         var2 = var2 + this.text.substring(var5);
      }

      if (this.validator.apply(var2)) {
         this.text = var2;
         this.moveCursorBy(var4 - this.selectionEnd + var7);
         if (this.field_175210_x != null) {
            this.field_175210_x.func_175319_a(this.id, this.text);
         }
      }
   }

   public void deleteWords(int integer) {
      if (this.text.length() != 0) {
         if (this.selectionEnd != this.cursorPosition) {
            this.writeText("");
         } else {
            this.deleteFromCursor(this.getNthWordFromCursor(integer) - this.cursorPosition);
         }
      }
   }

   public void deleteFromCursor(int integer) {
      if (this.text.length() != 0) {
         if (this.selectionEnd != this.cursorPosition) {
            this.writeText("");
         } else {
            boolean var2 = integer < 0;
            int var3 = var2 ? this.cursorPosition + integer : this.cursorPosition;
            int var4 = var2 ? this.cursorPosition : this.cursorPosition + integer;
            String var5 = "";
            if (var3 >= 0) {
               var5 = this.text.substring(0, var3);
            }

            if (var4 < this.text.length()) {
               var5 = var5 + this.text.substring(var4);
            }

            if (this.validator.apply(var5)) {
               this.text = var5;
               if (var2) {
                  this.moveCursorBy(integer);
               }

               if (this.field_175210_x != null) {
                  this.field_175210_x.func_175319_a(this.id, this.text);
               }
            }
         }
      }
   }

   public int getId() {
      return this.id;
   }

   public int getNthWordFromCursor(int integer) {
      return this.getNthWordFromPos(integer, this.getCursorPosition());
   }

   public int getNthWordFromPos(int integer1, int integer2) {
      return this.func_146197_a(integer1, integer2, true);
   }

   public int func_146197_a(int integer1, int integer2, boolean boolean3) {
      int var6 = integer2;
      boolean var7 = integer1 < 0;
      int var8 = Math.abs(integer1);

      for (int var9 = 0; var9 < var8; var9++) {
         if (!var7) {
            int var10 = this.text.length();
            var6 = this.text.indexOf(32, var6);
            if (var6 == -1) {
               var6 = var10;
            } else {
               while (boolean3 && var6 < var10 && this.text.charAt(var6) == ' ') {
                  var6++;
               }
            }
         } else {
            while (boolean3 && var6 > 0 && this.text.charAt(var6 - 1) == ' ') {
               var6--;
            }

            while (var6 > 0 && this.text.charAt(var6 - 1) != ' ') {
               var6--;
            }
         }
      }

      return var6;
   }

   public void moveCursorBy(int integer) {
      this.setCursorPosition(this.selectionEnd + integer);
   }

   public void setCursorPosition(int integer) {
      this.cursorPosition = integer;
      int var2 = this.text.length();
      this.cursorPosition = MathHelper.clamp_int(this.cursorPosition, 0, var2);
      this.setSelectionPos(this.cursorPosition);
   }

   public void setCursorPositionZero() {
      this.setCursorPosition(0);
   }

   public void setCursorPositionEnd() {
      this.setCursorPosition(this.text.length());
   }

   public boolean textboxKeyTyped(char character, int integer) {
      if (!this.isFocused) {
         return false;
      }

      if (GuiScreen.isKeyComboCtrlA(integer)) {
         this.setCursorPositionEnd();
         this.setSelectionPos(0);
         return true;
      }

      if (GuiScreen.isKeyComboCtrlC(integer)) {
         GuiScreen.setClipboardString(this.getSelectedText());
         return true;
      }

      if (GuiScreen.isKeyComboCtrlV(integer)) {
         if (this.isEnabled) {
            this.writeText(GuiScreen.getClipboardString());
         }

         return true;
      } else if (GuiScreen.isKeyComboCtrlX(integer)) {
         GuiScreen.setClipboardString(this.getSelectedText());
         if (this.isEnabled) {
            this.writeText("");
         }

         return true;
      } else {
         switch (integer) {
            case 14:
               if (GuiScreen.isCtrlKeyDown()) {
                  if (this.isEnabled) {
                     this.deleteWords(-1);
                  }
               } else if (this.isEnabled) {
                  this.deleteFromCursor(-1);
               }

               return true;
            case 199:
               if (GuiScreen.isShiftKeyDown()) {
                  this.setSelectionPos(0);
               } else {
                  this.setCursorPositionZero();
               }

               return true;
            case 203:
               if (GuiScreen.isShiftKeyDown()) {
                  if (GuiScreen.isCtrlKeyDown()) {
                     this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                  } else {
                     this.setSelectionPos(this.getSelectionEnd() - 1);
                  }
               } else if (GuiScreen.isCtrlKeyDown()) {
                  this.setCursorPosition(this.getNthWordFromCursor(-1));
               } else {
                  this.moveCursorBy(-1);
               }

               return true;
            case 205:
               if (GuiScreen.isShiftKeyDown()) {
                  if (GuiScreen.isCtrlKeyDown()) {
                     this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                  } else {
                     this.setSelectionPos(this.getSelectionEnd() + 1);
                  }
               } else if (GuiScreen.isCtrlKeyDown()) {
                  this.setCursorPosition(this.getNthWordFromCursor(1));
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
                  if (this.isEnabled) {
                     this.deleteWords(1);
                  }
               } else if (this.isEnabled) {
                  this.deleteFromCursor(1);
               }

               return true;
            default:
               if (ChatAllowedCharacters.isAllowedCharacter(character)) {
                  if (this.isEnabled) {
                     this.writeText(Character.toString(character));
                  }

                  return true;
               } else {
                  return false;
               }
         }
      }
   }

   public void mouseClicked(int integer1, int integer2, int integer3) {
      boolean var4 = integer1 >= this.xPosition
         && integer1 < this.xPosition + this.width
         && integer2 >= this.yPosition
         && integer2 < this.yPosition + this.height;
      if (this.canLoseFocus) {
         this.setFocused(var4);
      }

      if (this.isFocused && var4 && integer3 == 0) {
         int var5 = integer1 - this.xPosition;
         if (this.enableBackgroundDrawing) {
            var5 -= 4;
         }

         String var6 = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
         this.setCursorPosition(this.fontRendererInstance.trimStringToWidth(var6, var5).length() + this.lineScrollOffset);
      }
   }

   public void drawTextBox() {
      if (this.getVisible()) {
         if (this.getEnableBackgroundDrawing()) {
            drawRect(this.xPosition - 1, this.yPosition - 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, -6250336);
            drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -16777216);
         }

         int var3 = this.isEnabled ? this.enabledColor : this.disabledColor;
         int var4 = this.cursorPosition - this.lineScrollOffset;
         int var5 = this.selectionEnd - this.lineScrollOffset;
         String var6 = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
         boolean var7 = var4 >= 0 && var4 <= var6.length();
         boolean var8 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && var7;
         int var9 = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
         int var10 = this.enableBackgroundDrawing ? this.yPosition + (this.height - 8) / 2 : this.yPosition;
         int var11 = var9;
         if (var5 > var6.length()) {
            var5 = var6.length();
         }

         if (var6.length() > 0) {
            String var12 = var7 ? var6.substring(0, var4) : var6;
            var11 = this.fontRendererInstance.drawStringWithShadow(var12, var9, var10, var3);
         }

         boolean var15 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
         int var13 = var11;
         if (!var7) {
            var13 = var4 > 0 ? var9 + this.width : var9;
         } else if (var15) {
            var13 = var11 - 1;
            var11--;
         }

         if (var6.length() > 0 && var7 && var4 < var6.length()) {
            this.fontRendererInstance.drawStringWithShadow(var6.substring(var4), var11, var10, var3);
         }

         if (var8) {
            if (var15) {
               Gui.drawRect(var13, var10 - 1, var13 + 1, var10 + 1 + this.fontRendererInstance.FONT_HEIGHT, -3092272);
            } else {
               this.fontRendererInstance.drawStringWithShadow("_", var13, var10, var3);
            }
         }

         if (var5 != var4) {
            int var14 = var9 + this.fontRendererInstance.getStringWidth(var6.substring(0, var5));
            this.drawCursorVertical(var13, var10 - 1, var14 - 1, var10 + 1 + this.fontRendererInstance.FONT_HEIGHT);
         }
      }
   }

   public void drawCursorVertical(int integer1, int integer2, int integer3, int integer4) {
      if (integer1 < integer3) {
         int var7 = integer1;
         integer1 = integer3;
         integer3 = var7;
      }

      if (integer2 < integer4) {
         int var9 = integer2;
         integer2 = integer4;
         integer4 = var9;
      }

      if (integer3 > this.xPosition + this.width) {
         integer3 = this.xPosition + this.width;
      }

      if (integer1 > this.xPosition + this.width) {
         integer1 = this.xPosition + this.width;
      }

      Tessellator var10 = Tessellator.getInstance();
      WorldRenderer var8 = var10.getWorldRenderer();
      GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
      GlStateManager.disableTexture2D();
      GlStateManager.enableColorLogic();
      GlStateManager.colorLogicOp(5387);
      var8.begin(7, DefaultVertexFormats.POSITION);
      var8.pos(integer1, integer4, 0.0).endVertex();
      var8.pos(integer3, integer4, 0.0).endVertex();
      var8.pos(integer3, integer2, 0.0).endVertex();
      var8.pos(integer1, integer2, 0.0).endVertex();
      var10.draw();
      GlStateManager.disableColorLogic();
      GlStateManager.enableTexture2D();
   }

   public void setMaxStringLength(int integer) {
      this.maxStringLength = integer;
      if (this.text.length() > integer) {
         this.text = this.text.substring(0, integer);
      }
   }

   public int getMaxStringLength() {
      return this.maxStringLength;
   }

   public int getCursorPosition() {
      return this.cursorPosition;
   }

   public boolean getEnableBackgroundDrawing() {
      return this.enableBackgroundDrawing;
   }

   public void setEnableBackgroundDrawing(boolean boolean1) {
      this.enableBackgroundDrawing = boolean1;
   }

   public void setTextColor(int integer) {
      this.enabledColor = integer;
   }

   public void setDisabledTextColour(int integer) {
      this.disabledColor = integer;
   }

   public void setFocused(boolean boolean1) {
      if (boolean1 && !this.isFocused) {
         this.cursorCounter = 0;
      }

      this.isFocused = boolean1;
   }

   public boolean isFocused() {
      return this.isFocused;
   }

   public void setEnabled(boolean boolean1) {
      this.isEnabled = boolean1;
   }

   public int getSelectionEnd() {
      return this.selectionEnd;
   }

   public int getWidth() {
      return this.getEnableBackgroundDrawing() ? this.width - 8 : this.width;
   }

   public void setSelectionPos(int integer) {
      int var2 = this.text.length();
      if (integer > var2) {
         integer = var2;
      }

      if (integer < 0) {
         integer = 0;
      }

      this.selectionEnd = integer;
      if (this.fontRendererInstance != null) {
         if (this.lineScrollOffset > var2) {
            this.lineScrollOffset = var2;
         }

         int var3 = this.getWidth();
         int var5 = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), var3).length() + this.lineScrollOffset;
         if (integer == this.lineScrollOffset) {
            this.lineScrollOffset = this.lineScrollOffset - this.fontRendererInstance.trimStringToWidth(this.text, var3, true).length();
         }

         if (integer > var5) {
            this.lineScrollOffset += integer - var5;
         } else if (integer <= this.lineScrollOffset) {
            this.lineScrollOffset = this.lineScrollOffset - (this.lineScrollOffset - integer);
         }

         this.lineScrollOffset = MathHelper.clamp_int(this.lineScrollOffset, 0, var2);
      }
   }

   public void setCanLoseFocus(boolean boolean1) {
      this.canLoseFocus = boolean1;
   }

   public boolean getVisible() {
      return this.visible;
   }

   public void setVisible(boolean boolean1) {
      this.visible = boolean1;
   }
}
