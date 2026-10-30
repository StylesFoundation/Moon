package net.minecraft.client.resources;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractResourcePack implements IResourcePack {
   private static final Logger resourceLog = LogManager.getLogger();
   public final File resourcePackFile;

   public AbstractResourcePack(File file) {
      this.resourcePackFile = file;
   }

   private static String locationToName(ResourceLocation resourceLocation) {
      return String.format("%s/%s/%s", "assets", resourceLocation.getResourceDomain(), resourceLocation.getResourcePath());
   }

   protected static String getRelativeName(File file1, File file2) {
      return file1.toURI().relativize(file2.toURI()).getPath();
   }

   @Override
   public InputStream getInputStream(ResourceLocation resourceLocation) throws IOException {
      return this.getInputStreamByName(locationToName(resourceLocation));
   }

   @Override
   public boolean resourceExists(ResourceLocation resourceLocation) {
      return this.hasResourceName(locationToName(resourceLocation));
   }

   protected abstract InputStream getInputStreamByName(String string) throws IOException;

   protected abstract boolean hasResourceName(String string);

   protected void logNameNotLowercase(String string) {
      resourceLog.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", string, this.resourcePackFile);
   }

   @Override
   public <T extends IMetadataSection> T getPackMetadata(IMetadataSerializer iMetadataSerializer, String string) throws IOException {
      return readMetadata(iMetadataSerializer, this.getInputStreamByName("pack.mcmeta"), string);
   }

   static <T extends IMetadataSection> T readMetadata(IMetadataSerializer iMetadataSerializer, InputStream inputStream, String string) {
      BufferedReader var4 = null;

      JsonObject var3;
      try {
         var4 = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8));
         var3 = JsonParser.parseReader(var4).getAsJsonObject();
      } catch (RuntimeException var9) {
         throw new JsonParseException(var9);
      } finally {
         IOUtils.closeQuietly(var4);
      }

      return iMetadataSerializer.parseMetadataSection(string, var3);
   }

   @Override
   public BufferedImage getPackImage() throws IOException {
      return TextureUtil.readBufferedImage(this.getInputStreamByName("pack.png"));
   }

   @Override
   public String getPackName() {
      return this.resourcePackFile.getName();
   }
}
