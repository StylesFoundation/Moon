package com.moon.render.font.msdf;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MsdfFontData {
   @SerializedName("atlas")
   private MsdfAtlasMetadata atlas;
   @SerializedName("metrics")
   private MsdfFontMetrics metrics;
   @SerializedName("glyphs")
   private List<MsdfGlyph> glyphs;
   @SerializedName("kerning")
   private List<MsdfKerningPair> kerning;

   public MsdfAtlasMetadata getAtlas() {
      return this.atlas;
   }

   public MsdfFontMetrics getMetrics() {
      return this.metrics;
   }

   public List<MsdfGlyph> getGlyphs() {
      return this.glyphs;
   }

   public List<MsdfKerningPair> getKerning() {
      return this.kerning;
   }

   public MsdfFontData withAtlas(MsdfAtlasMetadata atlas) {
      this.atlas = atlas;
      return this;
   }

   public MsdfFontData withMetrics(MsdfFontMetrics metrics) {
      this.metrics = metrics;
      return this;
   }

   public MsdfFontData withGlyphs(List<MsdfGlyph> glyphs) {
      this.glyphs = glyphs;
      return this;
   }

   public MsdfFontData withKerning(List<MsdfKerningPair> kerning) {
      this.kerning = kerning;
      return this;
   }
}
