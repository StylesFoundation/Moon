package com.moon.render.font.msdf;

import com.google.gson.annotations.SerializedName;
import javax.annotation.Nullable;

public class MsdfGlyph {
   @SerializedName("unicode")
   private int unicode;
   @SerializedName("advance")
   private float advance;
   @SerializedName("planeBounds")
   @Nullable
   private GlyphBounds planeBounds;
   @SerializedName("atlasBounds")
   @Nullable
   private GlyphBounds atlasBounds;

   public int getUnicode() {
      return this.unicode;
   }

   public float getAdvance() {
      return this.advance;
   }

   @Nullable
   public GlyphBounds getPlaneBounds() {
      return this.planeBounds;
   }

   @Nullable
   public GlyphBounds getAtlasBounds() {
      return this.atlasBounds;
   }

   public MsdfGlyph setUnicode(int unicode) {
      this.unicode = unicode;
      return this;
   }

   public MsdfGlyph setAdvance(float advance) {
      this.advance = advance;
      return this;
   }

   public MsdfGlyph setPlaneBounds(@Nullable GlyphBounds planeBounds) {
      this.planeBounds = planeBounds;
      return this;
   }

   public MsdfGlyph setAtlasBounds(@Nullable GlyphBounds atlasBounds) {
      this.atlasBounds = atlasBounds;
      return this;
   }
}
