package com.moon.render.font.msdf;

import com.moon.render.font.TextMeasurer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MsdfTextMeasurer implements TextMeasurer {
   private final Map<String, Float> widthCache = new ConcurrentHashMap<>();
   private final Map<String, Float> heightCache = new ConcurrentHashMap<>();
   private final MsdfCharacterProvider provider;

   @Override
   public float getStringWidth(String text) {
      this.widthCache.computeIfAbsent(text, key -> {
         char[] chars = text.toCharArray();
         float width = 0.0F;
         char previousChar = 0;

         for (int i = 0; i < chars.length; i++) {
            boolean hasPrevious = previousChar != 0;
            char c = chars[i];
            if (c != '\n' && c != '\r' && c != '\t') {
               if (c == 167) {
                  if (i >= chars.length - 2) {
                     break;
                  }

                  i++;
               } else {
                  MsdfGlyphRenderer glyphRenderer = this.provider.getGlyph(c);
                  if (glyphRenderer != null) {
                     float kerning = hasPrevious ? this.provider.getKerning(previousChar, c) : 0.0F;
                     width += glyphRenderer.getAdvance() + kerning;
                     previousChar = c;
                  }
               }
            }
         }

         return width;
      });
      return this.widthCache.get(text);
   }

   @Override
   public float getStringHeight(String text) {
      this.heightCache.computeIfAbsent(text, key -> {
         char[] chars = text.toCharArray();
         float height = 0.0F;

         for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c != '\n' && c != '\r' && c != '\t') {
               if (c == 167) {
                  if (i >= chars.length - 2) {
                     break;
                  }

                  i++;
               } else {
                  MsdfGlyphRenderer glyphRenderer = this.provider.getGlyph(c);
                  if (glyphRenderer != null && glyphRenderer.getPlaneHeight() > height) {
                     height = glyphRenderer.getPlaneHeight();
                  }
               }
            }
         }

         return height;
      });
      return this.heightCache.get(text) + this.provider.getFont().getData().getMetrics().getDescender();
   }

   @Override
   public float getLineHeight() {
      return this.provider.getFont().getData().getMetrics().getLineHeight();
   }

   public Map<String, Float> getWidthCache() {
      return this.widthCache;
   }

   public Map<String, Float> getHeightCache() {
      return this.heightCache;
   }

   public MsdfCharacterProvider getProvider() {
      return this.provider;
   }

   public MsdfTextMeasurer(MsdfCharacterProvider provider) {
      this.provider = provider;
   }
}
