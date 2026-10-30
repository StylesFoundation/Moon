package net.minecraft.client.resources;

import com.google.common.collect.ImmutableSet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class DefaultResourcePack implements IResourcePack {
   public static final Set<String> defaultResourceDomains = ImmutableSet.of("minecraft", "realms");
   private final Map<String, File> mapAssets;

   public DefaultResourcePack(Map<String, File> map) {
      this.mapAssets = map;
   }

   @Override
   public InputStream getInputStream(ResourceLocation resourceLocation) throws IOException {
      InputStream var5 = this.Method346(resourceLocation);
      if (var5 != null) {
         return var5;
      } else {
         InputStream var6 = this.getInputStreamAssets(resourceLocation);
         if (var6 != null) {
            return var6;
         } else {
            throw new FileNotFoundException(resourceLocation.getResourcePath());
         }
      }
   }

   public InputStream getInputStreamAssets(ResourceLocation resourceLocation) throws IOException {
      File var5 = this.mapAssets.get(resourceLocation.toString());
      return var5 != null && var5.isFile() ? new FileInputStream(var5) : null;
   }

   private InputStream Method346(ResourceLocation resourceLocation) {
      String var2 = "/assets/" + resourceLocation.getResourceDomain() + "/" + resourceLocation.getResourcePath();
      return DefaultResourcePack.class.getResourceAsStream(var2);
   }

   @Override
   public boolean resourceExists(ResourceLocation resourceLocation) {
      return this.Method346(resourceLocation) != null || this.mapAssets.containsKey(resourceLocation.toString());
   }

   @Override
   public Set<String> getResourceDomains() {
      return defaultResourceDomains;
   }

   @Override
   public <T extends IMetadataSection> T getPackMetadata(IMetadataSerializer iMetadataSerializer, String string) throws IOException {
      try {
         FileInputStream var3 = new FileInputStream(this.mapAssets.get("pack.mcmeta"));
         return AbstractResourcePack.readMetadata(iMetadataSerializer, var3, string);
      } catch (RuntimeException | FileNotFoundException var4) {
         return null;
      }
   }

   @Override
   public BufferedImage getPackImage() throws IOException {
      return TextureUtil.readBufferedImage(DefaultResourcePack.class.getResourceAsStream("/" + new ResourceLocation("pack.png").getResourcePath()));
   }

   @Override
   public String getPackName() {
      return "Default";
   }
}
