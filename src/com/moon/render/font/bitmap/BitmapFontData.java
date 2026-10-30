package com.moon.render.font.bitmap;

import com.google.gson.annotations.SerializedName;

public class BitmapFontData {
   @SerializedName("chars")
   private BitmapGlyph[] chars;

   public BitmapGlyph[] getChars() {
      return this.chars;
   }

   public BitmapFontData withChars(BitmapGlyph[] chars) {
      this.chars = chars;
      return this;
   }

   public static int getDefaultValue() {
      return 0;
   }
}
