package com.moon.render.font.manager;

import com.moon.render.font.CustomFontRenderer;
import com.moon.render.font.bitmap.BitmapFontRenderer;

public class FontDefinition {
   private final String fontName;
   private final float size;
   private final float scale;

   public CustomFontRenderer createRenderer(FontImplementation implementation) {
      switch (implementation) {
         case BITMAP:
            return new BitmapFontRenderer(this.fontName, this.size, this.scale);
         default:
            throw new IncompatibleClassChangeError();
      }
   }

   private FontDefinition(String fontName, float size, float scale) {
      this.fontName = fontName;
      this.size = size;
      this.scale = scale;
   }

   public static FontDefinition of(String fontName, float size, float scale) {
      return new FontDefinition(fontName, size, scale);
   }
}
