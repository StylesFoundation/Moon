package com.moon.render.font;

import com.moon.render.RenderTask;
import java.awt.Color;

public abstract class FontRenderer {
   public abstract void drawInternal(
      RenderTask context, String text, float x, float y, float z, int color, boolean applyColorCodes, boolean shadow, float sdfOffset
   );

   public void drawWithShadow(RenderTask context, String text, float x, float y, float z, int color) {
      this.drawInternal(context, text, x, y, z, color, true, true, 0.0F);
   }

   public void draw(RenderTask context, String text, float x, float y, float z, int color, boolean applyColorCodes) {
      this.drawInternal(context, text, x, y, z, color, applyColorCodes, false, 0.0F);
   }

   public void draw(RenderTask context, String text, float x, float y, float z, int color) {
      this.drawInternal(context, text, x, y, z, color, true, false, 0.0F);
   }

   public void drawOutlined(RenderTask context, String text, float x, float y, float z, int color) {
      this.draw(context, text, x, y - 0.5F, z - 0.5F, Color.BLACK.getRGB(), false);
      this.draw(context, text, x, y + 0.5F, z + 0.5F, Color.BLACK.getRGB(), false);
      this.draw(context, text, x, y + 0.5F, z - 0.5F, Color.BLACK.getRGB(), false);
      this.draw(context, text, x, y - 0.5F, z + 0.5F, Color.BLACK.getRGB(), false);
      this.draw(context, text, x, y, z - 0.5F, Color.BLACK.getRGB(), false);
      this.draw(context, text, x, y, z + 0.5F, Color.BLACK.getRGB(), false);
      this.draw(context, text, x, y - 0.5F, z, Color.BLACK.getRGB(), false);
      this.draw(context, text, x, y + 0.5F, z, Color.BLACK.getRGB(), false);
      this.draw(context, text, x, y, z, color, true);
   }

   public abstract void bind();

   public abstract float getTextWidth(String text, float scale);

   public abstract float getTextHeight(String text, float scale);

   public abstract float getLineHeight(float scale);

   public String trimToWidth(String text, int maxWidth, float scale, boolean reverse) {
      StringBuilder builder = new StringBuilder();
      float currentWidth = 0.0F;
      int startIndex = reverse ? text.length() - 1 : 0;
      int step = reverse ? -1 : 1;
      boolean isColorCode = false;
      boolean isBold = false;

      for (int i = startIndex; i >= 0 && i < text.length() && currentWidth < maxWidth; i += step) {
         char c = text.charAt(i);
         float charWidth = this.getTextWidth(c + "", scale);
         if (isColorCode) {
            isColorCode = false;
            if (c == 'l' || c == 'L') {
               isBold = true;
            } else if (c == 'r' || c == 'R') {
               isBold = false;
            }
         } else if (charWidth < 0.0F) {
            isColorCode = true;
         } else {
            currentWidth += charWidth;
            if (isBold) {
               currentWidth++;
            }
         }

         if (currentWidth > maxWidth) {
            break;
         }

         if (reverse) {
            builder.insert(0, c);
         } else {
            builder.append(c);
         }
      }

      return builder.toString();
   }

   public static int getDefaultColor() {
      return 0;
   }
}
