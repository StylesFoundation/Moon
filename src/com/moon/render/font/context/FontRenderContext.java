package com.moon.render.font.context;

public class FontRenderContext {
   private static final int[] COLOR_CODES = new int[32];
   private final FontColorState colorState = new FontColorState();
   private final char[] characters;
   private final int initialColor;
   private int renderedColor;
   private boolean applyColorCodes;

   public boolean processFormattingCode(int index) {
      if (this.renderedColor == 0) {
         this.renderedColor = this.initialColor;
      }

      char ch = this.characters[index];
      if (ch == 167 && index + 1 < this.characters.length) {
         int colorIndex = "0123456789abcdefklmnor".indexOf(this.characters[index + 1]);
         if (colorIndex < 16) {
            this.colorState.setObfuscated(false);
            this.colorState.setBold(false);
            this.colorState.setStrikethrough(false);
            this.colorState.setUnderlined(false);
            this.colorState.setItalic(false);
            if (colorIndex < 0) {
               colorIndex = 15;
            }

            int alpha = this.initialColor >> 24 & 0xFF;
            this.renderedColor = COLOR_CODES[colorIndex] + (alpha << 24);
         } else {
            switch (colorIndex) {
               case 16:
                  this.colorState.setObfuscated(true);
                  break;
               case 17:
                  this.colorState.setBold(true);
                  break;
               case 18:
                  this.colorState.setStrikethrough(true);
                  break;
               case 19:
                  this.colorState.setUnderlined(true);
                  break;
               case 20:
                  this.colorState.setItalic(true);
                  break;
               case 21:
                  this.renderedColor = this.initialColor;
                  this.colorState.reset();
            }
         }

         return true;
      } else {
         return false;
      }
   }

   FontRenderContext(char[] characters, int initialColor, int renderedColor, boolean applyColorCodes) {
      this.characters = characters;
      this.initialColor = initialColor;
      this.renderedColor = renderedColor;
      this.applyColorCodes = applyColorCodes;
   }

   public static FontRenderContextBuilder builder() {
      return new FontRenderContextBuilder();
   }

   public FontColorState getColorState() {
      return this.colorState;
   }

   public char[] getCharacters() {
      return this.characters;
   }

   public int getInitialColor() {
      return this.initialColor;
   }

   public int getRenderedColor() {
      return this.renderedColor;
   }

   public boolean isApplyColorCodes() {
      return this.applyColorCodes;
   }

   public FontRenderContext setApplyColorCodes(boolean applyColorCodes) {
      this.applyColorCodes = applyColorCodes;
      return this;
   }

   static {
      for (int i = 0; i < 32; i++) {
         int base = (i >> 3 & 1) * 85;
         int red = (i >> 2 & 1) * 170 + base;
         int green = (i >> 1 & 1) * 170 + base;
         int blue = (i & 1) * 170 + base;
         if (i == 6) {
            red += 85;
         }

         if (i >= 16) {
            red /= 4;
            green /= 4;
            blue /= 4;
         }

         COLOR_CODES[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
      }
   }
}
