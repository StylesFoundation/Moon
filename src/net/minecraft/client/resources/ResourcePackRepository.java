package net.minecraft.client.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePackRepository {
   private static final Logger logger = LogManager.getLogger();
   private static final FileFilter resourcePackFilter = file -> {
      boolean var4 = file.isFile() && file.getName().endsWith(".zip");
      boolean var5 = file.isDirectory() && new File(file, "pack.mcmeta").isFile();
      return var4 || var5;
   };
   private final File dirResourcepacks;
   public final IResourcePack rprDefaultResourcePack;
   private final File dirServerResourcepacks;
   public final IMetadataSerializer rprMetadataSerializer;
   private IResourcePack resourcePackInstance;
   private final ReentrantLock lock = new ReentrantLock();
   private ListenableFuture<Object> downloadingPacks;
   private List<ResourcePackRepository.Entry> repositoryEntriesAll = Lists.newArrayList();
   public List<ResourcePackRepository.Entry> repositoryEntries = Lists.newArrayList();

   public ResourcePackRepository(File file1, File file2, IResourcePack iResourcePack, IMetadataSerializer iMetadataSerializer, GameSettings gameSettings) {
      this.dirResourcepacks = file1;
      this.dirServerResourcepacks = file2;
      this.rprDefaultResourcePack = iResourcePack;
      this.rprMetadataSerializer = iMetadataSerializer;
      this.fixDirResourcepacks();
      this.updateRepositoryEntriesAll();
      Iterator var9 = gameSettings.resourcePacks.iterator();

      while (var9.hasNext()) {
         String var10 = (String)var9.next();

         for (ResourcePackRepository.Entry var12 : this.repositoryEntriesAll) {
            if (var12.getResourcePackName().equals(var10)) {
               if (var12.func_183027_f() == 1 || gameSettings.incompatibleResourcePacks.contains(var12.getResourcePackName())) {
                  this.repositoryEntries.add(var12);
                  break;
               }

               var9.remove();
               logger.warn("Removed selected resource pack {} because it's no longer compatible", new Object[]{var12.getResourcePackName()});
            }
         }
      }
   }

   private void fixDirResourcepacks() {
      if (this.dirResourcepacks.exists()) {
         if (!this.dirResourcepacks.isDirectory() && (!this.dirResourcepacks.delete() || !this.dirResourcepacks.mkdirs())) {
            logger.warn("Unable to recreate resourcepack folder, it exists but is not a directory: " + this.dirResourcepacks);
         }
      } else if (!this.dirResourcepacks.mkdirs()) {
         logger.warn("Unable to create resourcepack folder: " + this.dirResourcepacks);
      }
   }

   private List<File> getResourcePackFiles() {
      return this.dirResourcepacks.isDirectory() ? Arrays.asList(this.dirResourcepacks.listFiles(resourcePackFilter)) : Collections.emptyList();
   }

   public void updateRepositoryEntriesAll() {
      ArrayList var4 = Lists.newArrayList();

      for (File var6 : this.getResourcePackFiles()) {
         ResourcePackRepository.Entry var7 = new ResourcePackRepository.Entry(var6);
         if (!this.repositoryEntriesAll.contains(var7)) {
            try {
               var7.updateResourcePack();
               var4.add(var7);
            } catch (Exception var9) {
               var4.remove(var7);
            }
         } else {
            int var8 = this.repositoryEntriesAll.indexOf(var7);
            if (var8 > -1 && var8 < this.repositoryEntriesAll.size()) {
               var4.add(this.repositoryEntriesAll.get(var8));
            }
         }
      }

      this.repositoryEntriesAll.removeAll(var4);

      for (ResourcePackRepository.Entry var11 : this.repositoryEntriesAll) {
         var11.closeResourcePack();
      }

      this.repositoryEntriesAll = var4;
   }

   public List<ResourcePackRepository.Entry> getRepositoryEntriesAll() {
      return ImmutableList.copyOf(this.repositoryEntriesAll);
   }

   public List<ResourcePackRepository.Entry> getRepositoryEntries() {
      return ImmutableList.copyOf(this.repositoryEntries);
   }

   public void setRepositories(List<ResourcePackRepository.Entry> list) {
      this.repositoryEntries.clear();
      this.repositoryEntries.addAll(list);
   }

   public File getDirResourcepacks() {
      return this.dirResourcepacks;
   }

   public ListenableFuture<Object> downloadResourcePack(String string1, String string2) {
      String var6;
      if (string2.matches("^[a-f0-9]{40}$")) {
         var6 = string2;
      } else {
         var6 = "legacy";
      }

      final File var7 = new File(this.dirServerResourcepacks, var6);
      this.lock.lock();

      try {
         this.clearResourcePack();
         if (var7.exists() && string2.length() == 40) {
            try {
               String var8 = Hashing.sha1().hashBytes(Files.toByteArray(var7)).toString();
               if (var8.equals(string2)) {
                  return this.setResourcePackInstance(var7);
               }

               logger.warn("File " + var7 + " had wrong hash (expected " + string2 + ", found " + var8 + "). Deleting it.");
               FileUtils.deleteQuietly(var7);
            } catch (IOException var16) {
               logger.warn("File " + var7 + " couldn't be hashed. Deleting it.", var16);
               FileUtils.deleteQuietly(var7);
            }
         }

         this.deleteOldServerResourcesPacks();
         GuiScreenWorking var18 = new GuiScreenWorking();
         Map var9 = Minecraft.getSessionInfo();
         Minecraft var10 = Minecraft.getMinecraft();
         Futures.getUnchecked(var10.addScheduledTask(() -> var10.displayGuiScreen(var18)));
         final SettableFuture var11 = SettableFuture.create();
         this.downloadingPacks = HttpUtil.downloadResourcePack(var7, string1, var9, 52428800, var18, var10.getProxy());
         Futures.addCallback(this.downloadingPacks, new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object object) {
               ResourcePackRepository.this.setResourcePackInstance(var7);
               var11.set(null);
            }

            @Override
            public void onFailure(Throwable throwable) {
               var11.setException(throwable);
            }
         }, MoreExecutors.directExecutor());
         return this.downloadingPacks;
      } finally {
         this.lock.unlock();
      }
   }

   private void deleteOldServerResourcesPacks() {
      ArrayList<File> var4 = Lists.newArrayList(FileUtils.listFiles(this.dirServerResourcepacks, TrueFileFilter.TRUE, null));
      var4.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
      int var5 = 0;

      for (File var7 : var4) {
         if (var5++ >= 10) {
            logger.info("Deleting old server resource pack " + var7.getName());
            FileUtils.deleteQuietly(var7);
         }
      }
   }

   public ListenableFuture<Object> setResourcePackInstance(File file) {
      this.resourcePackInstance = new FileResourcePack(file);
      return Minecraft.getMinecraft().scheduleResourcesRefresh();
   }

   public IResourcePack getResourcePackInstance() {
      return this.resourcePackInstance;
   }

   public void clearResourcePack() {
      this.lock.lock();

      try {
         if (this.downloadingPacks != null) {
            this.downloadingPacks.cancel(true);
         }

         this.downloadingPacks = null;
         if (this.resourcePackInstance != null) {
            this.resourcePackInstance = null;
            Minecraft.getMinecraft().scheduleResourcesRefresh();
         }
      } finally {
         this.lock.unlock();
      }
   }

   public class Entry {
      private final File resourcePackFile;
      private IResourcePack reResourcePack;
      private PackMetadataSection rePackMetadataSection;
      private BufferedImage texturePackIcon;
      private ResourceLocation locationTexturePackIcon;

      private Entry(File file) {
         this.resourcePackFile = file;
      }

      public void updateResourcePack() throws IOException {
         this.reResourcePack = this.resourcePackFile.isDirectory()
            ? new FolderResourcePack(this.resourcePackFile)
            : new FileResourcePack(this.resourcePackFile);
         this.rePackMetadataSection = this.reResourcePack.getPackMetadata(ResourcePackRepository.this.rprMetadataSerializer, "pack");

         try {
            this.texturePackIcon = this.reResourcePack.getPackImage();
         } catch (IOException var1) {
         }

         if (this.texturePackIcon == null) {
            this.texturePackIcon = ResourcePackRepository.this.rprDefaultResourcePack.getPackImage();
         }

         this.closeResourcePack();
      }

      public void bindTexturePackIcon(TextureManager textureManager) {
         if (this.locationTexturePackIcon == null) {
            try {
               this.locationTexturePackIcon = textureManager.getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.texturePackIcon));
            } catch (Exception var2) {
            }
         } else {
            textureManager.bindTexture(this.locationTexturePackIcon);
         }
      }

      public void closeResourcePack() {
         if (this.reResourcePack instanceof Closeable) {
            IOUtils.closeQuietly((Closeable)this.reResourcePack);
         }
      }

      public IResourcePack getResourcePack() {
         return this.reResourcePack;
      }

      public String getResourcePackName() {
         return this.reResourcePack.getPackName();
      }

      public String getTexturePackDescription() {
         return this.rePackMetadataSection == null
            ? EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing 'pack' section)"
            : this.rePackMetadataSection.getPackDescription().getFormattedText();
      }

      public int func_183027_f() {
         return this.rePackMetadataSection.getPackFormat();
      }

      @Override
      public boolean equals(Object object) {
         return this == object || object instanceof ResourcePackRepository.Entry && this.toString().equals(object.toString());
      }

      @Override
      public int hashCode() {
         return this.toString().hashCode();
      }

      @Override
      public String toString() {
         return String.format(
            "%s:%s:%d", this.resourcePackFile.getName(), this.resourcePackFile.isDirectory() ? "folder" : "zip", this.resourcePackFile.lastModified()
         );
      }
   }
}
