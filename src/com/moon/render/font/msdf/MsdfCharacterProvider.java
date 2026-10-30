package com.moon.render.font.msdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.util.MathHelper;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class MsdfCharacterProvider {
   private static final TaggedLogger LOGGER = Logger.tag("engine/font/msdf/provider");
   private static final Random RANDOM = new Random();
   private final Map<Integer, MsdfGlyphRenderer> glyphRenderers = new HashMap<>();
   private final MsdfFont font;
   private final String fontName;
   private int[][] obfuscationTable;

   public void init() {
      MsdfFontData data = this.font.getData();
      float maxAdvance = 0.0F;

      for (MsdfGlyph glyph : data.getGlyphs()) {
         MsdfGlyphRenderer renderer = new MsdfGlyphRenderer(glyph, data.getAtlas(), data.getMetrics());
         float advance = renderer.getAdvance();
         if (advance > maxAdvance) {
            maxAdvance = advance;
         }

         this.glyphRenderers.put(glyph.getUnicode(), renderer);
      }

      this.buildObfuscationTable(maxAdvance * 100.0F);
      LOGGER.info("Successfully created character provider cache for " + this.fontName);
   }

   private void buildObfuscationTable(float maxAdvance) {
      int tableSize = MathHelper.ceiling_float_int(maxAdvance);
      this.obfuscationTable = new int[tableSize][];

      for (int i = 0; i < tableSize; i++) {
         ArrayList matchingGlyphs = new ArrayList();

         for (Entry entry : this.glyphRenderers.entrySet()) {
            MsdfGlyphRenderer renderer = (MsdfGlyphRenderer)entry.getValue();
            boolean noPlaneBounds = renderer.getGlyph().getPlaneBounds() == null;
            if (Math.abs(renderer.getAdvance() * 100.0F - i) <= 0.5F && !noPlaneBounds) {
               int unicode = (Integer)entry.getKey();
               if (unicode != 167) {
                  matchingGlyphs.add(unicode);
               }
            }
         }

         int[] glyphIndices = new int[matchingGlyphs.size()];

         for (int j = 0; j < matchingGlyphs.size(); j++) {
            glyphIndices[j] = (Integer)matchingGlyphs.get(j);
         }

         this.obfuscationTable[i] = glyphIndices;
      }
   }

   public MsdfGlyphRenderer pickObfuscatedGlyph(MsdfGlyphRenderer glyphRenderer) {
      int[] candidates = this.obfuscationTable[(int)(glyphRenderer.getAdvance() * 100.0F)];
      return candidates.length == 0 ? glyphRenderer : this.getGlyph((char)candidates[RANDOM.nextInt(candidates.length)]);
   }

   public float getKerning(char first, char second) {
      MsdfFontData data = this.font.getData();
      MsdfGlyphRenderer renderer = this.glyphRenderers.get(Integer.valueOf(second));
      if (renderer.getAdvanceCache().containsKey(first)) {
         return renderer.getCachedAdvance(first);
      }

      float kerning = data.getKerning()
         .stream()
         .filter(kerningPair -> kerningPair.getUnicode1() == first && kerningPair.getUnicode2() == second)
         .findFirst()
         .orElse(new MsdfKerningPair())
         .getAdvance();
      renderer.cacheAdvance(first, kerning);
      return kerning;
   }

   public MsdfGlyphRenderer getGlyph(char character) {
      return this.glyphRenderers.get(Integer.valueOf(character));
   }

   public Map<Integer, MsdfGlyphRenderer> getGlyphRenderers() {
      return this.glyphRenderers;
   }

   public MsdfFont getFont() {
      return this.font;
   }

   public String getFontName() {
      return this.fontName;
   }

   public int[][] getObfuscationTable() {
      return this.obfuscationTable;
   }

   public MsdfCharacterProvider(MsdfFont font, String fontName) {
      this.font = font;
      this.fontName = fontName;
   }
}
