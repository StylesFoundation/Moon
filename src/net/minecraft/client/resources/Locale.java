package net.minecraft.client.resources;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class Locale {
   private static final Splitter splitter = Splitter.on('=').limit(2);
   private static final Pattern b = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
   Map<String, String> properties = Maps.newHashMap();
   private boolean unicode;

   public synchronized void loadLocaleDataFiles(IResourceManager iResourceManager, List<String> list) {
      this.properties.clear();

      for (String var7 : list) {
         String var8 = String.format("lang/%s.lang", var7);

         for (String var10 : iResourceManager.getResourceDomains()) {
            try {
               this.loadLocaleData(iResourceManager.getAllResources(new ResourceLocation(var10, var8)));
            } catch (IOException var11) {
            }
         }
      }

      this.checkUnicode();
   }

   public boolean isUnicode() {
      return this.unicode;
   }

   private void checkUnicode() {
      this.unicode = false;
      int var4 = 0;
      int var5 = 0;

      for (String var7 : this.properties.values()) {
         int var8 = var7.length();
         var5 += var8;

         for (int var9 = 0; var9 < var8; var9++) {
            if (var7.charAt(var9) >= 256) {
               var4++;
            }
         }
      }

      float var10 = (float)var4 / var5;
      this.unicode = var10 > 0.1;
   }

   private void loadLocaleData(List<IResource> list) throws IOException {
      Iterator var5 = list.iterator();

      while (var5.hasNext()) {
         InputStream var7 = ((IResource)var5.next()).getInputStream();

         try {
            this.loadLocaleData(var7);
         } finally {
            IOUtils.closeQuietly(var7);
         }
      }
   }

   private void loadLocaleData(InputStream inputStream) throws IOException {
      for (String var6 : IOUtils.readLines(inputStream, Charsets.UTF_8)) {
         if (!var6.isEmpty() && var6.charAt(0) != '#') {
            String[] var7 = (String[])Iterables.toArray(splitter.split(var6), String.class);
            if (var7.length == 2) {
               String var8 = var7[0];
               String var9 = b.matcher(var7[1]).replaceAll("%$1s");
               this.properties.put(var8, var9);
            }
         }
      }
   }

   private String translateKeyPrivate(String string) {
      String var4 = this.properties.get(string);
      return var4 == null ? string : var4;
   }

   public String Method980(String string, Object[] arr) {
      String var3 = this.translateKeyPrivate(string);

      try {
         return String.format(var3, arr);
      } catch (IllegalFormatException var4) {
         return "Format error: " + var3;
      }
   }
}
