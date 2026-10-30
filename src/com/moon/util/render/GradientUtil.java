package com.moon.util.render;

public class GradientUtil {
   public static int getColorAt(int[] colors, double position) {
      int length = colors.length;
      if (position > 1.0) {
         position = 1.0 - position % 1.0;
      }

      if (position == 1.0) {
         return colors[0];
      }

      if (position == 0.0) {
         return colors[length - 1];
      }

      double scaledPos = Math.max(0.0, (1.0 - position) * (length - 1));
      int index = (int)scaledPos;
      int startColor = colors[index];
      int endColor = colors[index + 1];
      return interpolate(startColor, endColor, scaledPos - index);
   }

   public static int interpolate(int start, int end, double factor) {
      if (factor > 1.0) {
         factor = 1.0 - factor % 1.0;
      }

      return lerpColors(start, end, factor);
   }

   public static int lerpColors(int start, int end, double factor) {
      double invFactor = 1.0 - factor;
      int red = (int)((start >> 16 & 0xFF) * invFactor + (end >> 16 & 0xFF) * factor);
      int green = (int)((start >> 8 & 0xFF) * invFactor + (end >> 8 & 0xFF) * factor);
      int blue = (int)((start & 0xFF) * invFactor + (end & 0xFF) * factor);
      return ((int)((start >> 24 & 0xFF) * invFactor + (end >> 24 & 0xFF) * factor) & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
   }

   public static float[] toRgba(int color) {
      return new float[]{(color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, (color >> 24 & 0xFF) / 255.0F};
   }
}
