package com.moon.render.font.bitmap;

import com.moon.render.font.impl.MinecraftFontRenderer;
import com.moon.render.font.manager.FontImplementation;
import java.util.HashMap;
import java.util.Map;

public class BitFontManager {
   private final Map<Integer, MinecraftFontRenderer> renderers = new HashMap<>();

   public MinecraftFontRenderer getDefaultRenderer() {
      this.ensureRenderer(this.renderers, "skeet", 5, FontImplementation.BITMAP);
      return this.renderers.get(5);
   }

   private void ensureRenderer(Map<Integer, MinecraftFontRenderer> renderers, String fontName, int size, FontImplementation implementation) {
      if (!renderers.containsKey(size)) {
         renderers.put(size, new MinecraftFontRenderer(fontName, size, implementation));
      }
   }
}
