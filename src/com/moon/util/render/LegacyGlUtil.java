package com.moon.util.render;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class LegacyGlUtil {
   public static double lerp(double start, double end, double factor) {
      return end + (start - end) * factor;
   }

   public static void drawFilledRect(float x, float y, float width, float height, int color) {
      if (width < 0.0F) {
         width = -width;
         x -= width;
      }

      if (height < 0.0F) {
         height = -height;
         y -= height;
      }

      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      setColor(color);
      GL11.glBegin(7);
      GL11.glVertex2f(x, y + height);
      GL11.glVertex2f(x + width, y + height);
      GL11.glVertex2f(x + width, y);
      GL11.glVertex2f(x, y);
      GL11.glEnd();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.resetColor();
   }

   public static void drawBorder(float x, float y, float width, float height, float lineWidth, int color, boolean rounded, float radius) {
      float cornerWidth = width / 4.0F;
      float cornerHeight = height / 4.0F;
      float rightX = x + width - (cornerWidth + (rounded ? radius : 0.0F));
      float bottomY = y + height - (cornerHeight + (rounded ? radius : 0.0F));
      drawFilledRect(x, y, cornerWidth + (rounded ? radius : 0.0F), lineWidth, color);
      drawFilledRect(rightX, y, cornerWidth, lineWidth, color);
      drawFilledRect(x, y + height - lineWidth, cornerWidth + (rounded ? radius : 0.0F), lineWidth, color);
      drawFilledRect(rightX, y + height - lineWidth, cornerWidth, lineWidth, color);
      drawFilledRect(x, y, lineWidth, cornerHeight + (rounded ? radius : 0.0F), color);
      drawFilledRect(x + width - lineWidth, y, lineWidth, cornerHeight + (rounded ? radius : 0.0F), color);
      drawFilledRect(x, bottomY, lineWidth, cornerHeight, color);
      drawFilledRect(x + width - lineWidth, bottomY, lineWidth, cornerHeight, color);
   }

   public static void setColor(int color) {
      float red = (color >> 16 & 0xFF) / 255.0F;
      float green = (color >> 8 & 0xFF) / 255.0F;
      float blue = (color & 0xFF) / 255.0F;
      float alpha = (color >> 24 & 0xFF) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
   }
}
