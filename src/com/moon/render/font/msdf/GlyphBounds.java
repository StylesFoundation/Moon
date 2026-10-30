package com.moon.render.font.msdf;

import com.google.gson.annotations.SerializedName;

public class GlyphBounds {
   @SerializedName("left")
   private float left;
   @SerializedName("top")
   private float top;
   @SerializedName("right")
   private float right;
   @SerializedName("bottom")
   private float bottom;

   public float getLeft() {
      return this.left;
   }

   public float getTop() {
      return this.top;
   }

   public float getRight() {
      return this.right;
   }

   public float getBottom() {
      return this.bottom;
   }

   public GlyphBounds setLeft(float left) {
      this.left = left;
      return this;
   }

   public GlyphBounds setTop(float top) {
      this.top = top;
      return this;
   }

   public GlyphBounds setRight(float right) {
      this.right = right;
      return this;
   }

   public GlyphBounds setBottom(float bottom) {
      this.bottom = bottom;
      return this;
   }
}
