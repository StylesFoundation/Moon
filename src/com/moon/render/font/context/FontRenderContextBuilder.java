package com.moon.render.font.context;

import java.util.Arrays;

public class FontRenderContextBuilder {
   private char[] characters;
   private int initialColor;
   private int renderedColor;
   private boolean applyColorCodes;

   FontRenderContextBuilder() {
   }

   public FontRenderContextBuilder characters(char[] characters) {
      this.characters = characters;
      return this;
   }

   public FontRenderContextBuilder initialColor(int initialColor) {
      this.initialColor = initialColor;
      return this;
   }

   public FontRenderContextBuilder renderedColor(int renderedColor) {
      this.renderedColor = renderedColor;
      return this;
   }

   public FontRenderContextBuilder applyColorCodes(boolean applyColorCodes) {
      this.applyColorCodes = applyColorCodes;
      return this;
   }

   public FontRenderContext build() {
      return new FontRenderContext(this.characters, this.initialColor, this.renderedColor, this.applyColorCodes);
   }

   @Override
   public String toString() {
      return "FontRenderContext.FontRenderContextBuilder(characters="
         + Arrays.toString(this.characters)
         + ", initialColor="
         + this.initialColor
         + ", renderedColor="
         + this.renderedColor
         + ", applyColorCodes="
         + this.applyColorCodes
         + ")";
   }
}
