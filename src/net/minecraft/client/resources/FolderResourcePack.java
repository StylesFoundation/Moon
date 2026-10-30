package net.minecraft.client.resources;

import com.google.common.collect.Sets;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.filefilter.DirectoryFileFilter;

public class FolderResourcePack extends AbstractResourcePack {
   public FolderResourcePack(File file) {
      super(file);
   }

   @Override
   protected InputStream getInputStreamByName(String string) throws IOException {
      return new BufferedInputStream(new FileInputStream(new File(this.resourcePackFile, string)));
   }

   @Override
   protected boolean hasResourceName(String string) {
      return new File(this.resourcePackFile, string).isFile();
   }

   @Override
   public Set<String> getResourceDomains() {
      HashSet var4 = Sets.newHashSet();
      File var5 = new File(this.resourcePackFile, "assets/");
      if (var5.isDirectory()) {
         for (File var9 : var5.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY)) {
            String var10 = getRelativeName(var5, var9);
            if (!var10.equals(var10.toLowerCase())) {
               this.logNameNotLowercase(var10);
            } else {
               var4.add(var10.substring(0, var10.length() - 1));
            }
         }
      }

      return var4;
   }
}
