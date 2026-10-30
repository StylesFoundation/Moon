package com.moon.render.font.manager;

import com.moon.render.font.FontRenderer;
import com.moon.render.font.msdf.MsdfFontRenderer;
import java.util.HashMap;
import java.util.Map;

public class FontManager {
   private final Map<FontType, FontRenderer> fonts = new HashMap<>();

   public FontRenderer getFont(FontType type) {
      this.initializeFont(type);
      return this.fonts.get(type);
   }

   public void initializeFont(FontType type) {
      this.fonts.computeIfAbsent(type, fontType -> {
         return switch (type.getRenderMode()) {
            case MSDF -> new MsdfFontRenderer(type.getResourceName(), type.getWeight(), type.getOutlineOffset(), type.getSdfOffset());
            case BITMAP -> null;
         };
      });
   }

   public void refreshTextureStates() {
      this.fonts
         .values()
         .stream()
         .filter(fontRenderer -> fontRenderer instanceof MsdfFontRenderer)
         .forEach(fontRenderer -> ((MsdfFontRenderer)fontRenderer).needsTextureFilterRefresh = true);
   }
}
