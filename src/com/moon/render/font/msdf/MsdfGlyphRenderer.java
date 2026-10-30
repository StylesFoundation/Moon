package com.moon.render.font.msdf;

import com.moon.render.command.impl.text.MsdfGlyphCommand;
import com.moon.render.font.context.FontRenderContext;
import java.util.HashMap;
import java.util.Map;

public class MsdfGlyphRenderer {
   private final Map<Character, Float> advanceCache = new HashMap<>();
   private MsdfGlyph glyph;
   private MsdfFontMetrics metrics;
   private final float topV;
   private final float leftU;
   private final float bottomV;
   private final float rightU;
   private final float planeWidth;
   private final float planeHeight;
   private float advance;
   private float scale;
   private float baseline;
   private float slant;

   public MsdfGlyphRenderer(MsdfGlyph glyph, MsdfAtlasMetadata atlas, MsdfFontMetrics metrics) {
      this.advance = glyph.getAdvance();
      GlyphBounds atlasBounds = glyph.getAtlasBounds();
      float left;
      float top;
      float right;
      float bottom;
      if (atlasBounds == null) {
         left = 0.0F;
         top = 0.0F;
         right = 0.0F;
         bottom = 0.0F;
         this.planeWidth = 0.0F;
         this.planeHeight = 0.0F;
         this.baseline = 0.0F;
      } else {
         left = atlasBounds.getLeft();
         top = atlasBounds.getTop();
         right = atlasBounds.getRight();
         bottom = atlasBounds.getBottom();
         GlyphBounds planeBounds = glyph.getPlaneBounds();
         if (planeBounds != null) {
            this.planeWidth = planeBounds.getRight() - planeBounds.getLeft();
            this.planeHeight = planeBounds.getTop() - planeBounds.getBottom();
            this.baseline = planeBounds.getTop();
         } else {
            this.planeWidth = 0.0F;
            this.planeHeight = 0.0F;
            this.baseline = 0.0F;
         }
      }

      float atlasWidth = atlas.getWidth();
      float atlasHeight = atlas.getHeight();
      this.topV = 1.0F - top / atlasHeight;
      this.leftU = left / atlasWidth;
      this.bottomV = 1.0F - bottom / atlasHeight;
      this.rightU = right / atlasWidth;
      this.glyph = glyph;
      this.metrics = metrics;
   }

   public MsdfGlyphCommand createCommand(
      float x, float y, float weight, float outlineOffset, float sdfOffset, float fontSdfOffset, boolean shadow, FontRenderContext context
   ) {
      y = y + this.metrics.getUnderlinePosition() * this.scale - this.baseline * this.scale;
      GlyphBounds planeBounds = this.glyph.getPlaneBounds();
      float effectiveSdfOffset = context.getColorState().isBold() ? -1.0F + outlineOffset : fontSdfOffset + sdfOffset;
      float italicSlant = context.getColorState().isItalic() ? 1.0F : 0.0F;
      return planeBounds == null
         ? MsdfGlyphCommand.of(
            x,
            y,
            x + this.planeWidth * this.scale,
            y + this.planeHeight * this.scale,
            this.leftU,
            this.topV,
            this.rightU,
            this.bottomV,
            weight,
            effectiveSdfOffset,
            italicSlant,
            shadow ? Integer.MIN_VALUE : (context.isApplyColorCodes() ? context.getRenderedColor() : context.getInitialColor())
         )
         : MsdfGlyphCommand.of(
            x + planeBounds.getLeft() * this.scale,
            y,
            x + planeBounds.getRight() * this.scale,
            y + this.planeHeight * this.scale,
            this.leftU,
            this.topV,
            this.rightU,
            this.bottomV,
            weight,
            effectiveSdfOffset,
            italicSlant,
            shadow ? Integer.MIN_VALUE : (context.isApplyColorCodes() ? context.getRenderedColor() : context.getInitialColor())
         );
   }

   public void cacheAdvance(char character, float advance) {
      this.advanceCache.put(character, advance);
   }

   public float getCachedAdvance(char character) {
      return this.advanceCache.getOrDefault(character, 0.0F);
   }

   public Map<Character, Float> getAdvanceCache() {
      return this.advanceCache;
   }

   public MsdfGlyph getGlyph() {
      return this.glyph;
   }

   public MsdfFontMetrics getMetrics() {
      return this.metrics;
   }

   public float getTopV() {
      return this.topV;
   }

   public float getLeftU() {
      return this.leftU;
   }

   public float getBottomV() {
      return this.bottomV;
   }

   public float getRightU() {
      return this.rightU;
   }

   public float getPlaneWidth() {
      return this.planeWidth;
   }

   public float getPlaneHeight() {
      return this.planeHeight;
   }

   public float getAdvance() {
      return this.advance;
   }

   public float getScale() {
      return this.scale;
   }

   public float getBaseline() {
      return this.baseline;
   }

   public float getSlant() {
      return this.slant;
   }

   public MsdfGlyphRenderer setGlyph(MsdfGlyph glyph) {
      this.glyph = glyph;
      return this;
   }

   public MsdfGlyphRenderer setMetrics(MsdfFontMetrics metrics) {
      this.metrics = metrics;
      return this;
   }

   public MsdfGlyphRenderer setAdvance(float advance) {
      this.advance = advance;
      return this;
   }

   public MsdfGlyphRenderer setScale(float scale) {
      this.scale = scale;
      return this;
   }

   public MsdfGlyphRenderer setBaseline(float baseline) {
      this.baseline = baseline;
      return this;
   }

   public MsdfGlyphRenderer setSlant(float slant) {
      this.slant = slant;
      return this;
   }
}
