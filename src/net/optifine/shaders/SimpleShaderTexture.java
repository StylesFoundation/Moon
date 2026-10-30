package net.optifine.shaders;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
import org.apache.commons.io.IOUtils;

public class SimpleShaderTexture extends AbstractTexture {
   private final String texturePath;
   private static final IMetadataSerializer METADATA_SERIALIZER = makeMetadataSerializer();

   public SimpleShaderTexture(String string) {
      this.texturePath = string;
   }

   @Override
   public void loadTexture(IResourceManager iResourceManager) throws IOException {
      this.deleteGlTexture();
      InputStream var5 = Shaders.getShaderPackResourceStream(this.texturePath);
      if (var5 == null) {
         throw new FileNotFoundException("Shader texture not found: " + this.texturePath);
      }

      try {
         BufferedImage var6 = TextureUtil.readBufferedImage(var5);
         TextureMetadataSection var7 = loadTextureMetadataSection(this.texturePath, new TextureMetadataSection(false, false, new ArrayList<>()));
         TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), var6, var7.getTextureBlur(), var7.getTextureClamp());
      } finally {
         IOUtils.closeQuietly(var5);
      }
   }

   public static TextureMetadataSection loadTextureMetadataSection(String string, TextureMetadataSection textureMetadataSection) {
      String var5 = string + ".mcmeta";
      String var6 = "texture";
      InputStream var7 = Shaders.getShaderPackResourceStream(var5);
      if (var7 != null) {
         BufferedReader var8 = new BufferedReader(new InputStreamReader(var7));

         TextureMetadataSection var9;
         try {
            JsonObject var10 = JsonParser.parseReader(var8).getAsJsonObject();
            TextureMetadataSection var11 = METADATA_SERIALIZER.parseMetadataSection(var6, var10);
            if (var11 == null) {
               return textureMetadataSection;
            }

            var9 = var11;
         } catch (RuntimeException var16) {
            SMCLog.warning("Error reading metadata: " + var5);
            SMCLog.warning(var16.getClass().getName() + ": " + var16.getMessage());
            return textureMetadataSection;
         } finally {
            IOUtils.closeQuietly(var8);
            IOUtils.closeQuietly(var7);
         }

         return var9;
      } else {
         return textureMetadataSection;
      }
   }

   private static IMetadataSerializer makeMetadataSerializer() {
      IMetadataSerializer var0 = new IMetadataSerializer();
      var0.registerMetadataSectionType(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
      var0.registerMetadataSectionType(new FontMetadataSectionSerializer(), FontMetadataSection.class);
      var0.registerMetadataSectionType(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
      var0.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
      var0.registerMetadataSectionType(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
      return var0;
   }
}
