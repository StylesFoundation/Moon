package com.moon.ui.theme.palette;

import com.google.ux.material.libmonet.hct.Hct;

public final class MaterialPalettes {
   public TonalPalette primary;
   public TonalPalette secondary;
   public TonalPalette tertiary;
   public TonalPalette neutral;
   public TonalPalette neutralVariant;
   public TonalPalette error;

   public static MaterialPalettes tonal(int color) {
      return new MaterialPalettes(color, false);
   }

   public static MaterialPalettes content(int color) {
      return new MaterialPalettes(color, true);
   }

   private MaterialPalettes(int color, boolean content) {
      Hct hct = Hct.fromInt(color);
      double hue = hct.getHue();
      double chroma = hct.getChroma();
      if (content) {
         this.primary = TonalPalette.fromHueChroma(hue, chroma);
         this.secondary = TonalPalette.fromHueChroma(hue, chroma / 3.0);
         this.tertiary = TonalPalette.fromHueChroma(hue + 60.0, chroma / 2.0);
         this.neutral = TonalPalette.fromHueChroma(hue, Math.min(chroma / 12.0, 4.0));
         this.neutralVariant = TonalPalette.fromHueChroma(hue, Math.min(chroma / 6.0, 8.0));
      } else {
         this.primary = TonalPalette.fromHueChroma(hue, Math.max(48.0, chroma));
         this.secondary = TonalPalette.fromHueChroma(hue, 16.0);
         this.tertiary = TonalPalette.fromHueChroma(hue + 60.0, 24.0);
         this.neutral = TonalPalette.fromHueChroma(hue, 4.0);
         this.neutralVariant = TonalPalette.fromHueChroma(hue, 8.0);
      }

      this.error = TonalPalette.fromHueChroma(25.0, 84.0);
   }
}
