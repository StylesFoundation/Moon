package com.moon.render.font.msdf;

import com.google.gson.annotations.SerializedName;

public class MsdfFontMetrics {
   @SerializedName("emSize")
   private float emSize;
   @SerializedName("lineHeight")
   private float lineHeight;
   @SerializedName("ascender")
   private float ascender;
   @SerializedName("descender")
   private float descender;
   @SerializedName("underlineY")
   private float underlineY;
   @SerializedName("underlineThickness")
   private float underlineThickness;

   public float getUnderlinePosition() {
      return this.lineHeight + this.descender + this.underlineY / 2.0F;
   }

   public float getEmSize() {
      return this.emSize;
   }

   public float getLineHeight() {
      return this.lineHeight;
   }

   public float getAscender() {
      return this.ascender;
   }

   public float getDescender() {
      return this.descender;
   }

   public float getUnderlineY() {
      return this.underlineY;
   }

   public float getUnderlineThickness() {
      return this.underlineThickness;
   }

   public MsdfFontMetrics setEmSize(float emSize) {
      this.emSize = emSize;
      return this;
   }

   public MsdfFontMetrics setLineHeight(float lineHeight) {
      this.lineHeight = lineHeight;
      return this;
   }

   public MsdfFontMetrics setAscender(float ascender) {
      this.ascender = ascender;
      return this;
   }

   public MsdfFontMetrics setDescender(float descender) {
      this.descender = descender;
      return this;
   }

   public MsdfFontMetrics setUnderlineY(float underlineY) {
      this.underlineY = underlineY;
      return this;
   }

   public MsdfFontMetrics setUnderlineThickness(float underlineThickness) {
      this.underlineThickness = underlineThickness;
      return this;
   }
}
