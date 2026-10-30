package net.minecraft.client.resources;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleReloadableResourceManager implements IReloadableResourceManager {
   private static final Logger logger = LogManager.getLogger();
   private static final Joiner joinerResourcePacks = Joiner.on(", ");
   private final Map<String, FallbackResourceManager> domainResourceManagers = Maps.newHashMap();
   private final List<IResourceManagerReloadListener> reloadListeners = Lists.newArrayList();
   private final Set<String> setResourceDomains = Sets.newLinkedHashSet();
   private final IMetadataSerializer rmMetadataSerializer;

   public SimpleReloadableResourceManager(IMetadataSerializer iMetadataSerializer) {
      this.rmMetadataSerializer = iMetadataSerializer;
   }

   public void reloadResourcePack(IResourcePack iResourcePack) {
      for (String var6 : iResourcePack.getResourceDomains()) {
         this.setResourceDomains.add(var6);
         FallbackResourceManager var7 = this.domainResourceManagers.get(var6);
         if (var7 == null) {
            var7 = new FallbackResourceManager(this.rmMetadataSerializer);
            this.domainResourceManagers.put(var6, var7);
         }

         var7.addResourcePack(iResourcePack);
      }
   }

   @Override
   public Set<String> getResourceDomains() {
      return this.setResourceDomains;
   }

   @Override
   public IResource getResource(ResourceLocation resourceLocation) throws IOException {
      IResourceManager var5 = this.domainResourceManagers.get(resourceLocation.getResourceDomain());
      if (var5 != null) {
         return var5.getResource(resourceLocation);
      } else {
         throw new FileNotFoundException(resourceLocation.toString());
      }
   }

   @Override
   public List<IResource> getAllResources(ResourceLocation resourceLocation) throws IOException {
      IResourceManager var5 = this.domainResourceManagers.get(resourceLocation.getResourceDomain());
      if (var5 != null) {
         return var5.getAllResources(resourceLocation);
      } else {
         throw new FileNotFoundException(resourceLocation.toString());
      }
   }

   private void clearResources() {
      this.domainResourceManagers.clear();
      this.setResourceDomains.clear();
   }

   @Override
   public void reloadResources(List<IResourcePack> list) {
      this.clearResources();
      logger.info("Reloading ResourceManager: " + joinerResourcePacks.join(Iterables.transform(list, IResourcePack::getPackName)));

      for (IResourcePack var6 : list) {
         this.reloadResourcePack(var6);
      }

      this.notifyReloadListeners();
   }

   @Override
   public void registerReloadListener(IResourceManagerReloadListener iResourceManagerReloadListener) {
      this.reloadListeners.add(iResourceManagerReloadListener);
      iResourceManagerReloadListener.onResourceManagerReload(this);
   }

   private void notifyReloadListeners() {
      Iterator var4 = this.reloadListeners.iterator();

      while (var4.hasNext()) {
         ((IResourceManagerReloadListener)var4.next()).onResourceManagerReload(this);
      }
   }
}
