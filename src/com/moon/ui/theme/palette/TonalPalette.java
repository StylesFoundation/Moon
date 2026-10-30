package com.moon.ui.theme.palette;

import com.google.ux.material.libmonet.hct.Hct;
import java.util.HashMap;
import java.util.Map;

public final class TonalPalette {
   Map<Integer, Integer> toneToColorCache = new HashMap<>();
   double hue;
   double chroma;

   public static final TonalPalette fromColor(int color) {
      Hct hct = Hct.fromInt(color);
      return fromHueChroma(hct.getHue(), hct.getChroma());
   }

   public static final TonalPalette fromHueChroma(double hue, double chroma) {
      return new TonalPalette(hue, chroma);
   }

   private TonalPalette(double hue, double chroma) {
      this.hue = hue;
      this.chroma = chroma;
   }

   public int getColor(int tone) {
      Integer color = this.toneToColorCache.get(tone);
      if (color == null) {
         color = Hct.from(this.hue, this.chroma, tone).toInt();
         this.toneToColorCache.put(tone, color);
      }

      return color;
   }

   public static boolean isValid() {
      return true;
   }

   static {
      if (!isValid()) {
      }
   }
}
