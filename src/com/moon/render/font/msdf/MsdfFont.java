package com.moon.render.font.msdf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class MsdfFont {
   private static final TaggedLogger LOGGER = Logger.tag("engine/font/msdf/provider");
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private MsdfCharacterProvider provider;
   private ResourceLocation textureLocation;
   private MsdfFontData data;

   public void load(String fontName) {
      InputStream inputStream = this.getClass().getResourceAsStream(String.format("/engine/font/msdf/%s", fontName + ".json"));
      if (inputStream == null) {
         LOGGER.error("Could not read msdf font file " + fontName + ".json");
      } else {
         try {
            this.textureLocation = new ResourceLocation(fontName + ".png");
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.textureLocation);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GlStateManager.bindTexture(0);
            this.data = (MsdfFontData)GSON.fromJson(new InputStreamReader(inputStream), MsdfFontData.class);
            if (this.data == null) {
               LOGGER.error("Could not parse font info " + fontName + ".json");
            }
         } catch (Exception e) {
            LOGGER.error("Could not parse font info " + fontName + ".json", new Object[]{e});
         }

         this.provider = new MsdfCharacterProvider(this, fontName);
         this.provider.init();
      }
   }

   public MsdfCharacterProvider getProvider() {
      return this.provider;
   }

   public ResourceLocation getTextureLocation() {
      return this.textureLocation;
   }

   public MsdfFontData getData() {
      return this.data;
   }
}
