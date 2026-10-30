package com.moon.render.font;

public interface TextMeasurer {
   float getStringWidth(String text);

   float getStringHeight(String text);

   float getLineHeight();
}
