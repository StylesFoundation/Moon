package com.moon.render.font.msdf;

import com.google.gson.annotations.SerializedName;

public class MsdfKerningPair {
   @SerializedName("unicode1")
   private int unicode1;
   @SerializedName("unicode2")
   private int unicode2;
   @SerializedName("advance")
   private float advance;

   public int getUnicode1() {
      return this.unicode1;
   }

   public int getUnicode2() {
      return this.unicode2;
   }

   public float getAdvance() {
      return this.advance;
   }

   public MsdfKerningPair setUnicode1(int unicode1) {
      this.unicode1 = unicode1;
      return this;
   }

   public MsdfKerningPair setUnicode2(int unicode2) {
      this.unicode2 = unicode2;
      return this;
   }

   public MsdfKerningPair setAdvance(float advance) {
      this.advance = advance;
      return this;
   }
}
