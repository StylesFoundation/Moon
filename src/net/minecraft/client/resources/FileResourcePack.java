package net.minecraft.client.resources;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileResourcePack extends AbstractResourcePack implements Closeable {
   public static final Splitter entryNameSplitter = Splitter.on('/').omitEmptyStrings().limit(3);
   private ZipFile resourcePackZipFile;

   public FileResourcePack(File file) {
      super(file);
   }

   private ZipFile getResourcePackZipFile() throws IOException {
      if (this.resourcePackZipFile == null) {
         this.resourcePackZipFile = new ZipFile(this.resourcePackFile);
      }

      return this.resourcePackZipFile;
   }

   @Override
   protected InputStream getInputStreamByName(String string) throws IOException {
      ZipFile var4 = this.getResourcePackZipFile();
      ZipEntry var5 = var4.getEntry(string);
      if (var5 == null) {
         throw new ResourcePackFileNotFoundException(this.resourcePackFile, string);
      } else {
         return var4.getInputStream(var5);
      }
   }

   @Override
   public boolean hasResourceName(String string) {
      try {
         return this.getResourcePackZipFile().getEntry(string) != null;
      } catch (IOException var2) {
         return false;
      }
   }

   @Override
   public Set<String> getResourceDomains() {
      ZipFile var2;
      try {
         var2 = this.getResourcePackZipFile();
      } catch (IOException var9) {
         return Collections.emptySet();
      }

      Enumeration var3 = var2.entries();
      HashSet var4 = Sets.newHashSet();

      while (var3.hasMoreElements()) {
         String var6 = ((ZipEntry)var3.nextElement()).getName();
         if (var6.startsWith("assets/")) {
            ArrayList var7 = Lists.newArrayList(entryNameSplitter.split(var6));
            if (var7.size() > 1) {
               String var8 = (String)var7.get(1);
               if (!var8.equals(var8.toLowerCase())) {
                  this.logNameNotLowercase(var8);
               } else {
                  var4.add(var8);
               }
            }
         }
      }

      return var4;
   }

   @Override
   protected void finalize() throws Throwable {
      this.close();
      super.finalize();
   }

   @Override
   public void close() throws IOException {
      if (this.resourcePackZipFile != null) {
         this.resourcePackZipFile.close();
         this.resourcePackZipFile = null;
      }
   }
}
