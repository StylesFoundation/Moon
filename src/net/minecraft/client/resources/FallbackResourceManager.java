package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FallbackResourceManager implements IResourceManager {
   private static final Logger logger = LogManager.getLogger();
   protected final List<IResourcePack> resourcePacks = Lists.newArrayList();
   private final IMetadataSerializer frmMetadataSerializer;

   public FallbackResourceManager(IMetadataSerializer iMetadataSerializer) {
      this.frmMetadataSerializer = iMetadataSerializer;
   }

   public void addResourcePack(IResourcePack iResourcePack) {
      this.resourcePacks.add(iResourcePack);
   }

   @Override
   public Set<String> getResourceDomains() {
      return null;
   }

   @Override
   public IResource getResource(ResourceLocation resourceLocation) throws IOException {
      IResourcePack var5 = null;
      ResourceLocation var6 = getLocationMcmeta(resourceLocation);

      for (int var7 = this.resourcePacks.size() - 1; var7 >= 0; var7--) {
         IResourcePack var8 = this.resourcePacks.get(var7);
         if (var5 == null && var8.resourceExists(var6)) {
            var5 = var8;
         }

         if (var8.resourceExists(resourceLocation)) {
            InputStream var9 = null;
            if (var5 != null) {
               var9 = this.getInputStream(var6, var5);
            }

            return new SimpleResource(var8.getPackName(), resourceLocation, this.getInputStream(resourceLocation, var8), var9, this.frmMetadataSerializer);
         }
      }

      throw new FileNotFoundException(resourceLocation.toString());
   }

   protected InputStream getInputStream(ResourceLocation resourceLocation, IResourcePack iResourcePack) throws IOException {
      InputStream var5 = iResourcePack.getInputStream(resourceLocation);
      return logger.isDebugEnabled() ? new FallbackResourceManager.InputStreamLeakedResourceLogger(var5, resourceLocation, iResourcePack.getPackName()) : var5;
   }

   @Override
   public List<IResource> getAllResources(ResourceLocation resourceLocation) throws IOException {
      ArrayList var5 = Lists.newArrayList();
      ResourceLocation var6 = getLocationMcmeta(resourceLocation);

      for (IResourcePack var8 : this.resourcePacks) {
         if (var8.resourceExists(resourceLocation)) {
            InputStream var9 = var8.resourceExists(var6) ? this.getInputStream(var6, var8) : null;
            var5.add(new SimpleResource(var8.getPackName(), resourceLocation, this.getInputStream(resourceLocation, var8), var9, this.frmMetadataSerializer));
         }
      }

      if (var5.isEmpty()) {
         throw new FileNotFoundException(resourceLocation.toString());
      } else {
         return var5;
      }
   }

   static ResourceLocation getLocationMcmeta(ResourceLocation resourceLocation) {
      return new ResourceLocation(resourceLocation.getResourceDomain(), resourceLocation.getResourcePath() + ".mcmeta");
   }

   static class InputStreamLeakedResourceLogger extends InputStream {
      private final InputStream inputStream;
      private final String message;
      private boolean isClosed = false;

      public InputStreamLeakedResourceLogger(InputStream inputStream, ResourceLocation resourceLocation, String string) {
         this.inputStream = inputStream;
         ByteArrayOutputStream var4 = new ByteArrayOutputStream();
         new Exception().printStackTrace(new PrintStream(var4));
         this.message = "Leaked resource: '" + resourceLocation + "' loaded from pack: '" + string + "'\n" + var4;
      }

      @Override
      public void close() throws IOException {
         this.inputStream.close();
         this.isClosed = true;
      }

      @Override
      protected void finalize() throws Throwable {
         if (!this.isClosed) {
            FallbackResourceManager.logger.warn(this.message);
         }

         super.finalize();
      }

      @Override
      public int read() throws IOException {
         return this.inputStream.read();
      }
   }
}
