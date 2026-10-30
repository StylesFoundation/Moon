package com.moon.render.font.bitmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

public class BitmapFontLoader {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

   public static BitmapFontData load(String fontName) {
      InputStream inputStream = BitmapFontLoader.class.getResourceAsStream("/assets/minecraft/bit/" + fontName + ".json");
      if (inputStream == null) {
         throw new RuntimeException("Failed to load font file " + fontName);
      }

      try {
         return (BitmapFontData)GSON.fromJson(IOUtils.toString(inputStream, StandardCharsets.UTF_8), BitmapFontData.class);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
