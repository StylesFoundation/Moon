package com.moon.render.font.bitmap;

import com.moon.render.RenderTask;
import com.moon.render.command.impl.texture.TextureCommand;
import com.moon.render.font.CustomFontRenderer;
import com.moon.util.render.GradientUtil;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class BitmapFontRenderer extends CustomFontRenderer {
   private final Map<String, Float> widthCache = new HashMap<>();
   private final int[] COLOR_CODES = new int[32];
   private final float scaledSize;
   private final float scale;
   private final ResourceLocation textureLocation;
   private final BitmapGlyph[] glyphs = new BitmapGlyph[256];

   public BitmapFontRenderer(String fontName, float fontSize, float scale) {
      this.textureLocation = new ResourceLocation("bit/" + fontName + ".png");
      this.scaledSize = fontSize * scale;
      this.scale = scale;

      for (BitmapGlyph glyph : BitmapFontLoader.load(fontName).getChars()) {
         this.glyphs[glyph.getId()] = glyph;
      }

      this.initializeColorCodes();
   }

   private void initializeColorCodes() {
      for (int i = 0; i < 32; i++) {
         int shadow = (i >> 3 & 1) * 85;
         int red = (i >> 2 & 1) * 170 + shadow;
         int green = (i >> 1 & 1) * 170 + shadow;
         int blue = (i & 1) * 170 + shadow;
         if (i == 6) {
            red += 85;
         }

         if (i >= 16) {
            red /= 4;
            green /= 4;
            blue /= 4;
         }

         this.COLOR_CODES[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
      }
   }

   @Override
   public void draw(RenderTask context, String text, float x, float y, int color, boolean shadow) {
      float fontScale = this.scale;
      x *= fontScale;
      y *= fontScale;
      float currentX = x;
      float[] rgba = GradientUtil.toRgba(color);
      char[] chars = text.toCharArray();

      for (int i = 0; i < chars.length; i++) {
         char c = text.charAt(i);
         if (c < 256) {
            if (c == ' ') {
               currentX += 3.0F;
            } else if (c == 167 && i + 1 < text.length()) {
               if (!shadow) {
                  int colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                  if (colorIndex < 16) {
                     if (colorIndex < 0) {
                        colorIndex = 15;
                     }

                     int colorCode = this.COLOR_CODES[colorIndex];
                     color = new Color(colorCode >> 16 & 0xFF, colorCode >> 8 & 0xFF, colorCode & 0xFF, (int)(rgba[3] * 255.0F)).getRGB();
                  } else if (colorIndex == 21) {
                     color = new Color((int)(rgba[0] * 255.0F), (int)(rgba[1] * 255.0F), (int)(rgba[2] * 255.0F), (int)(rgba[3] * 255.0F)).getRGB();
                  }
               }

               i++;
            } else {
               BitmapGlyph glyph = this.glyphs[c];
               if (glyph != null) {
                  float endX = currentX + glyph.getWidth();
                  float endY = y + glyph.getHeight();
                  float minU = glyph.getX() / 128.0F;
                  float minV = glyph.getY() / 128.0F;
                  float maxU = (glyph.getX() + glyph.getWidth()) / 128.0F;
                  float maxV = (glyph.getY() + glyph.getHeight()) / 128.0F;
                  context.addCommand(TextureCommand.of(currentX, y, endX, endY, minU, minV, maxU, maxV, color));
                  currentX += glyph.getWidth() + 1;
               }
            }
         }
      }
   }

   @Override
   public void begin(Matrix4f matrix) {
      GlStateManager.enableTexture2D();
      GlStateManager.enableBlend();
      Minecraft.getMinecraft().getTextureManager().bindTexture(this.textureLocation);
      float fontScale = this.scale;
      matrix.scale(new Vector3f(1.0F / fontScale, 1.0F / fontScale, 1.0F));
   }

   @Override
   public void end(Matrix4f matrix) {
      GlStateManager.bindTexture(0);
      matrix.scale(new Vector3f(this.scale, this.scale, 1.0F));
   }

   @Override
   public float getStringWidth(String text) {
      if (this.widthCache.containsKey(text)) {
         return this.widthCache.get(text);
      }

      float width = 0.0F;
      int length = text.toCharArray().length;

      for (int i = 0; i < length; i++) {
         char c = text.charAt(i);
         if (c < 256) {
            if (c == ' ') {
               width += 3.0F;
            } else if (c == 167 && i + 1 < text.length()) {
               i++;
            } else {
               BitmapGlyph glyph = this.glyphs[c];
               if (glyph != null) {
                  width += glyph.getWidth() + 1;
               }
            }
         }
      }

      this.widthCache.put(text, width - 1.0F);
      return width - 1.0F;
   }

   @Override
   public float getLineHeightRatio() {
      return this.scaledSize / this.scale;
   }
}
